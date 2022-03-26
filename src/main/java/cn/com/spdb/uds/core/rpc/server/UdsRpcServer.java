package cn.com.spdb.uds.core.rpc.server;

import java.util.List;

import com.baidu.jprotobuf.pbrpc.management.ServerStatus;
import com.baidu.jprotobuf.pbrpc.transport.RpcServer;
import com.baidu.jprotobuf.pbrpc.transport.RpcServerOptions;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.UdsHandlerManger;
import cn.com.spdb.uds.core.rpc.transfer.UdsTransferRpcImpl;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.NameThreadFactory;

public class UdsRpcServer {

	private RpcServer rpcServer;

	public void shutDown() {
		if (rpcServer != null) {
			try {
				rpcServer.shutdown();
			} catch (Exception e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
	}

	public void start() {
		// 初始化RPC服务
		this.init();
		// 消息处理器
		UdsHandlerManger.getInstance();
		// 开始连接尝试连接
		this.linkDistanceUdsServer();
	}

	/**
	 * 加载按照优先级有高到低注册 正常情况下连接 由子节点连接主节点；备节点连接主节点
	 */
	public void linkDistanceUdsServer() {
		UdsServerDao dao = DBManager.getInstance().getDao(UdsServerDao.class);
		List<UdsServerBean> list = dao.getUdsServerList();
		for (UdsServerBean serverBean : list) {
			if ((serverBean.getLocation() & UdsConstant.LOCATION) < 1) {
				UdsLogger.error("error location|my:" + UdsConstant.LOCATION + " |client:" + serverBean.getLocation());
				continue;
			}
			// 机器是否是主节点机器
			if (serverBean.getServer_type() == UdsConstant.SERVER_TYPE_MASTER) {
				if (UdsConstant.IS_PRIMARY_SERVER && UdsConstant.ORDER <= serverBean.getOrder()) {
					continue;
				}
				UdsRpcClient udsRpcClient = UdsRpcClientManager.getInstance().addUdsRpcClient(serverBean);
				if (udsRpcClient == null) {
					continue;
				}
				UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(udsRpcClient.getServerName(),
						RpcCommand.SERVER_REGISTER);
				UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent, null);
			}
		}
	}

	public void init() {
		RpcServerOptions options = new RpcServerOptions();
		/** 单位秒 */
		int size = UdsConstant.AVAILABLE_PROCESSORS_SIZE > 32 ? 32 : UdsConstant.AVAILABLE_PROCESSORS_SIZE;
		options.setTaskTheads(size);
		options.setWorkThreads(size);
		options.setAcceptorThreads(size);

		rpcServer = new RpcServer(options);
		rpcServer.registerService(new UdsTransferRpcImpl());
		rpcServer.start(UdsConstant.RPC_SERVER_PORT);

		NameThreadFactory factory = new NameThreadFactory(this.getClass().getSimpleName() + "FIEXD_BUG");
		factory.newDeamonThread(new FixedBug()).start();
	}

	/**
	 * 防止{@link ServerStatus} 当不用｛@link
	 * {@link RpcServerOptions#setHttpServerPort(int)}｝方式启动 时造成 {@link ServerStatus}
	 * 无法通过构造函数实例化导致，消费线程无法正常启动造成 {@link ServerStatus#ASYNC_REQUEST} 队列无法消费，导致内存溢出
	 * 
	 * @author T-luzl
	 *
	 */
	/*
	Rename variable
	 */
	private class FixedBug implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					ServerStatus.ASYNC_REQUEST.clear();
					ServerStatus.REQUEST_COUNTS.clear();
					Thread.sleep(1000 * 10);
				} catch (InterruptedException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}

		}

	}

}
