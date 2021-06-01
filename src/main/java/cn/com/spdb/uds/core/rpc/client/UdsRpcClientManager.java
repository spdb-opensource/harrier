package cn.com.spdb.uds.core.rpc.client;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.SchedulerManager;
import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.UdsHandlerManger;
import cn.com.spdb.uds.core.rpc.handler.UdsRpcHandler;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;
import cn.com.spdb.uds.utils.UdsUtils;

public class UdsRpcClientManager {

	private final static Logger LOGGER = LoggerFactory.getLogger(UdsRpcClientManager.class);
	private final static Object KEY = new Object();

	public static UdsRpcClientManager instance;

	public static UdsRpcClientManager getInstance() {
		synchronized (KEY) {
			if (instance == null) {
				instance = new UdsRpcClientManager();
			}
		}
		return instance;
	}

	/**
	 * 全部服务
	 */
	private ConcurrentHashMap<String, UdsRpcClient> RPC_CLIENT_MAP = new ConcurrentHashMap<String, UdsRpcClient>();

	/**
	 * 心跳服务
	 */
	private ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1,
			new NameThreadFactory(this.getClass().getSimpleName()));

	/**
	 * 启动连接客户端的心跳
	 */
	public void startHeartbeat() {
		/***
		 * 启动心跳
		 */
		scheduledService.scheduleAtFixedRate(new TimerTask() {

			/**
			 *
			 */
			@Override
			public void run() {
				try {
					for (UdsRpcClient rpcClient : RPC_CLIENT_MAP.values()) {

						// 发送心跳
						if (rpcClient.getHeartbeat().checkOverMinTime()) {
							UdsRpcEvent event = UdsRpcEvent.buildBroadcastEvent(RpcCommand.SERVER_HEARTBEAT);
							event.setTargetId(rpcClient.getServerName());
							UdsRpcClientManager.getInstance().sendMessage(rpcClient, event, null);
						}
						// 心跳超时检测
						rpcClient.getHeartbeat().checkOverMaxTime();
						// 主节点检测竞争和机器死亡检测
						UdsRpcClientManager.getInstance().checkUdsRpcClient(rpcClient);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, 15 * DateUtils.TIME_MILLSECOND_OF_SECOND, 10 * DateUtils.TIME_MILLSECOND_OF_SECOND, TimeUnit.MILLISECONDS);
	}

	/**
	 * 本地获取连接,获取不到新建
	 * 
	 * @param serverName
	 * @return
	 */
	public UdsRpcClient getUdsRpcClient(String serverName) {
		UdsRpcClient client = RPC_CLIENT_MAP.get(serverName);
		return client;
	}

	public UdsRpcClient getUdsRpcClient(short order) {
		for (UdsRpcClient udsRpcClient : RPC_CLIENT_MAP.values()) {
			if (udsRpcClient.getOrder() == order) {
				return udsRpcClient;
			}
		}
		return null;
	}

	/**
	 * 广播给其他服务器
	 * 
	 * @param udsRpcEvent
	 * @param object
	 */
	public void sendBroadcastMessage(int command, Object object) {
		for (UdsRpcClient udsRpcClient : RPC_CLIENT_MAP.values()) {
			if (udsRpcClient.getServerName().equals(UdsConstant.SERVER_NAME)
					&& UdsConstant.SEND_LOCATE == UdsConstant.FALSE_NUM) {
				continue;
			}
			UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildBroadcastEvent(command);
			udsRpcEvent.setTargetId(udsRpcClient.getServerName());
			sendMessage(udsRpcClient, udsRpcEvent, object);
		}
	}

	/**
	 * 
	 * @param udsRpcEvent
	 * @param object
	 * @param isMaster    true 通知主节点，false 通知子节点
	 */
	public void sendBroadcastMessage(UdsRpcEvent udsRpcEvent, Object object, boolean isMaster) {
		for (UdsRpcClient udsRpcClient : RPC_CLIENT_MAP.values()) {
			if (udsRpcClient.getServerName().equals(UdsConstant.SERVER_NAME)
					&& UdsConstant.SEND_LOCATE == UdsConstant.FALSE_NUM) {
				continue;
			}
			UdsRpcEvent tmpRpcEvent = udsRpcEvent.clone();
			if (isMaster) {
				if (udsRpcClient.isMaster()) {
					tmpRpcEvent.setTargetId(udsRpcClient.getServerName());
					sendMessage(udsRpcClient, tmpRpcEvent, object);
				}
			} else {
				if (!udsRpcClient.isMaster()) {
					tmpRpcEvent.setTargetId(udsRpcClient.getServerName());
					sendMessage(udsRpcClient, tmpRpcEvent, object);
				}
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param udsRpcCliento
	 * @param udsRpcEvent
	 * @param object
	 */
	public boolean sendMessage(UdsRpcClient udsRpcClient, UdsRpcEvent udsRpcEvent, Object object) {
		UdsRpcEvent callBackEvent = sendMessageForUdsRpcEvent(udsRpcClient, udsRpcEvent, object);
		Integer code = callBackEvent.getAttribute(RpcAttrKey.CODE);
		if (code == null || code == RpcResultCode.ERROR) {
			return false;
		}
		return true;
	}

	public UdsRpcEvent sendMessageForUdsRpcEvent(UdsRpcClient udsRpcClient, UdsRpcEvent udsRpcEvent, Object object) {
		UdsRpcEvent event = UdsRpcEvent.buildBroadcastEvent(0);
		event.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
		if (udsRpcClient == null || udsRpcEvent == null) {
			LOGGER.error("udsRpcEvent or  udsRpcEvent  is null");
			UdsLogger.logEvent(LogEvent.ERROR, "udsRpcEvent or  udsRpcEvent  is null");
			return event;
		}
		if (!udsRpcEvent.getSourceId().equals(UdsConstant.SERVER_NAME)
				|| !udsRpcEvent.getTargetId().equals(udsRpcClient.getServerName())) {
			LOGGER.error("SourceId or TargetId  is error | udsRpcEvent:[{}] | udsRpcClient:[{}]",
					udsRpcEvent.toString(), udsRpcClient.toString());
			UdsLogger.logEvent(LogEvent.ERROR, "SourceId or TargetId  is error | udsRpcEvent:[{}] | udsRpcClient:[{}]",
					udsRpcEvent.toString(), udsRpcClient.toString());
			return event;
		}
		UdsRpcHandler handler = UdsHandlerManger.getInstance().getServerRpcEventHandler(udsRpcEvent.getCommand());
		if (handler == null) {
			LOGGER.error("command handler is not exit | command: [{}]", udsRpcEvent.getCommand());
			UdsLogger.logEvent(LogEvent.ERROR, "command handler is not exit | command: [{}]", udsRpcEvent.getCommand());
			return event;
		}
		RpcEventProtocol rpcEventProtocol = handler.getClass().getAnnotation(RpcEventProtocol.class);
		int concurrent = rpcEventProtocol.concurrent();
		if (concurrent != 1) {
			UdsHandlerManger.getInstance().addSendProducerTask(udsRpcClient, udsRpcEvent, object);
			event.addAttribute(RpcAttrKey.CODE, RpcResultCode.SUCCESS);
			return event;
		} else {
			UdsRpcEvent callBackEvent = UdsHandlerManger.getInstance().sendServerConcurrentEvent(udsRpcEvent,
					udsRpcClient, object);
			Integer code = callBackEvent.getAttribute(RpcAttrKey.CODE);
			if (code == null || code == RpcResultCode.ERROR) {
				callBackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
				return callBackEvent;
			}
			callBackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.SUCCESS);
			return callBackEvent;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param udsRpcClient
	 * @param udsRpcEvent
	 * @param object
	 */
	public void sendMessage(String serverName, UdsRpcEvent udsRpcEvent, Object object) {
		if (udsRpcEvent == null) {
			return;
		}
		UdsRpcClient udsRpcClient = RPC_CLIENT_MAP.get(serverName);
		sendMessage(udsRpcClient, udsRpcEvent, object);
	}

	/**
	 * 注销客服端
	 * 
	 * @param serverName
	 */
	public void removeUdsRpcClient(String serverName) {
		UdsRpcClient rpcClient = RPC_CLIENT_MAP.remove(serverName);
		if (rpcClient != null) {
			rpcClient.shutDown();
		}
	}

	/**
	 * 加载客服端
	 */
	public UdsRpcClient addUdsRpcClient(String serverName) {
		UdsServerDao dao = DBManager.getInstance().getDao(UdsServerDao.class);
		UdsServerBean serverBean = dao.getUdsServerByName(serverName);
		if (serverBean == null) {
			LOGGER.error("serverName: " + serverName + "|serverBean is null");
			return null;
		}
		return addUdsRpcClient(serverBean);
	}

	public UdsRpcClient addUdsRpcClient(UdsServerBean serverBean) {
		if (serverBean == null) {
			LOGGER.error("serverBean is null");
			return null;
		}
		String serverName = serverBean.getServer_name();
		UdsRpcClient rpcClient = RPC_CLIENT_MAP.remove(serverName);
		if (rpcClient != null) {
			LOGGER.warn("remove old UdsRpcClient add |new client:" + rpcClient.toString() + "|old client "
					+ rpcClient.toString());
			rpcClient.shutDown();
		}
		if ((UdsConstant.LOCATION & serverBean.getLocation()) <= 0) {
			LOGGER.error("serverName: " + serverName + "|location:" + UdsConstant.LOCATION + " "
					+ serverBean.getLocation() + "|not my location");
			return null;
		}
		if (!UdsConstant.IS_PRIMARY_SERVER && serverBean.getServer_type() == UdsConstant.SERVER_TYPE_CHILD) {
			LOGGER.error("serverName: " + serverName + "local IS_PRIMARY_SERVER:" + UdsConstant.IS_PRIMARY_SERVER
					+ "|Server_type:" + serverBean.getServer_type() + " not master");
			return null;
		}
		UdsRpcClient udsRpcClient = null;
		try {
			udsRpcClient = new UdsRpcClient(serverBean);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("udsRpcClient is not connect", serverBean.toString());
			return null;
		}
		RPC_CLIENT_MAP.put(serverName, udsRpcClient);
		LOGGER.info("addUdsRpcClient new client:" + udsRpcClient.toString());
		return udsRpcClient;
	}

	/**
	 * 远程主节点检测竞选和机器死亡检测
	 * 
	 * @param rpcClient 远程主节点
	 */
	public void checkUdsRpcClient(UdsRpcClient rpcClient) {
		if (UdsConstant.IS_PRIMARY_SERVER) {
			// 检测远程主节点
			if (rpcClient.isMaster()) {
				// 远程主节点是否活跃
				if (rpcClient.isActive()) {
					// 活跃，检测我的序列，如果比我小，我在测试，我停止
					if (rpcClient.getOrder() < UdsConstant.ORDER && MasterManager.getInstance().isCheckRecive()) {
						// 停止检测信号文件
						MasterManager.getInstance().setCheckRecive(false);
						LOGGER.error("local check stop , other " + rpcClient.getServerName() + "start");
					}
				} else {
					// 不活跃，检测我的序列，如果比我小，我停止，我启动
					if (rpcClient.getOrder() < UdsConstant.ORDER && !MasterManager.getInstance().isCheckRecive()) {
						MasterManager.getInstance().setCheckRecive(true);
						UdsConstant.loadUdsSystemMap();
						LOGGER.error("local check start, other " + rpcClient.getServerName() + "end");
					}
					UdsLogger.logErrorInstertDbError(UdsErrorCode.SERVER_DEATH, UdsErrorLevel.H,
							UdsConstant.SERVER_NAME, rpcClient.getServerName(), rpcClient.getIp());
				}
			} else {
				if (!rpcClient.isActive()) {
					ChildServerInfo info = MasterManager.getInstance()
							.removeChildServerJobMap(rpcClient.getServerName());
					if (info != null) {
						// 重新分发和执行作业报错
						UdsUtils.childShutDownJobShutDown(rpcClient.getServerName());
					}
					LOGGER.error("child check death：" + rpcClient.getServerName());
					UdsLogger.logErrorInstertDbError(UdsErrorCode.SERVER_DEATH, UdsErrorLevel.H,
							UdsConstant.SERVER_NAME, rpcClient.getServerName(), rpcClient.getIp());
				}
			}
		}
	}

	/** 全部关闭 */
	public void shutDownRpcClient() {
		sendBroadcastMessage(RpcCommand.SERVER_SHOUTDOWN, null);
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (UdsRpcClient client : RPC_CLIENT_MAP.values()) {
			try {
				client.shutDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RPC_CLIENT_MAP.clear();
	}

	public ConcurrentHashMap<String, UdsRpcClient> getRPC_CLIENT_MAP() {
		return RPC_CLIENT_MAP;
	}

	public void setRPC_CLIENT_MAP(ConcurrentHashMap<String, UdsRpcClient> rPC_CLIENT_MAP) {
		RPC_CLIENT_MAP = rPC_CLIENT_MAP;
	}

}
