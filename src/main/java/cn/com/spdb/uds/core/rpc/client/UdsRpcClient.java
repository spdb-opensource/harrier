package cn.com.spdb.uds.core.rpc.client;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.NettyRuntime;

import java.util.concurrent.atomic.AtomicBoolean;

import org.aspectj.apache.bcel.classfile.ExceptionTable;

import com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy;
import com.baidu.jprotobuf.pbrpc.transport.RpcClient;
import com.baidu.jprotobuf.pbrpc.transport.RpcClientOptions;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.transfer.TransferRpc;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

public class UdsRpcClient implements Comparable<UdsRpcClient> {

	private RpcClient rpcClient;

	private String ip;

	private int port;
	/** 远程方法 */
	private TransferRpc transerRpc;
	/** 远程代理 */
	private ProtobufRpcProxy<TransferRpc> transferRpcProxy;
	/** 创建时间 */
	private long createMillisTime = 0;
	/** 心跳 */
	private Heartbeat heartbeat = new Heartbeat();

	/** 服务器名字 */
	private String serverName;
	/** 服务器顺序 */
	private short order;
	/** 是否是主节点 */
	private boolean isMaster;
	/** 地域 */
	private int location;

	public UdsRpcClient(String ip, int port, String serverName) {
		super();
		this.ip = ip;
		this.port = port;
		this.serverName = serverName;
		this.doConnect();
	}

	public UdsRpcClient(UdsServerBean bean) {
		this.ip = bean.getIp();
		this.port = bean.getPort();
		this.serverName = bean.getServer_name();
		this.createMillisTime = System.currentTimeMillis();
		this.location = bean.getLocation();
		setMaster(bean.getServer_type() == UdsConstant.SERVER_TYPE_MASTER ? true : false);
		setOrder(bean.getOrder());
		this.doConnect();
	}

	/** 发送 */
	public UdsRpcEvent writeConcurrent(UdsRpcEvent udsRpcEvent) {
		UdsRpcEvent call = null;
		try {
			call = transerRpc.transferEventConcurrent(udsRpcEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return call;
	}

	/** 发送 */
	public void write(UdsRpcEvent udsRpcEvent) {
		try {
			transerRpc.transferEvent(udsRpcEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doConnect() {
		shutDown();

		RpcClientOptions options = new RpcClientOptions();
		/***
		 * 代理线程数目
		 */
		int size = UdsConstant.AVAILABLE_PROCESSORS_SIZE > 16 ? 16 : UdsConstant.AVAILABLE_PROCESSORS_SIZE;
		options.setThreadPoolSize(size);
		options.setWorkGroupThreadSize(size);

		rpcClient = new RpcClient(NioSocketChannel.class, options);
		transferRpcProxy = new ProtobufRpcProxy<TransferRpc>(rpcClient, TransferRpc.class);
		transferRpcProxy.setHost(ip);
		transferRpcProxy.setPort(port);
		transferRpcProxy.setLookupStubOnStartup(true);
		try {
			transerRpc = transferRpcProxy.proxy();
		} catch (Exception e) {
			shutDown();
			throw e;
		}
	}

	public void shutDown() {
		try {
			if (transferRpcProxy != null) {
				transferRpcProxy.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (rpcClient != null) {
				rpcClient.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "UdsRpcClient [ip=" + ip + ", port=" + port + ", serverName=" + serverName + ", isMaster=" + isMaster
				+ ", location=" + location + "]";
	}

	public RpcClient getRpcClient() {
		return rpcClient;
	}

	public void setRpcClient(RpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int prot) {
		this.port = prot;
	}

	public TransferRpc getTranserRpc() {
		return transerRpc;
	}

	public void setTranserRpc(TransferRpc transerRpc) {
		this.transerRpc = transerRpc;
	}

	public ProtobufRpcProxy<TransferRpc> getTransferRpcProxy() {
		return transferRpcProxy;
	}

	public void setTransferRpcProxy(ProtobufRpcProxy<TransferRpc> transferRpcProxy) {
		this.transferRpcProxy = transferRpcProxy;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public long getCreateMillisTime() {
		return createMillisTime;
	}

	public void setCreateMillisTime(long createMillisTime) {
		this.createMillisTime = createMillisTime;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short order) {
		this.order = order;
	}

	@Override
	public int compareTo(UdsRpcClient o) {
		if (o.isMaster) {
			if (getOrder() > o.getOrder()) {
				return -4;
			} else {
				return -3;
			}
		} else {
			if (getOrder() > o.getOrder()) {
				return -2;
			} else {
				return -1;
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UdsRpcClient other = (UdsRpcClient) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		return true;
	}

	/** 是否存活 */
	public boolean isActive() {
		return heartbeat.isStatus();
	}

	public Heartbeat getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Heartbeat heartbeat) {
		this.heartbeat = heartbeat;
	}

	/** 更新心跳 */
	public void updateMillisTime() {
		if (!heartbeat.getAndSetStatus(true)) {
			UdsLogger.error(serverName + " active now");
			/** 主节点恢复心跳提示 */
			if (UdsConstant.IS_PRIMARY_SERVER) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.SERVER_ACTIVE, UdsErrorLevel.M, UdsConstant.SERVER_NAME,
						serverName, ip);
			}
		}
		heartbeat.setUpdateMillisTime(System.currentTimeMillis());
	}

	/**
	 * 心跳
	 * 
	 * @author T-luzl
	 *
	 */
	public static class Heartbeat {
		/** 更新时间 */
		private long updateMillisTime;
		/** 是否存活 */
		private AtomicBoolean status = new AtomicBoolean(false);

		private final static int MAX_TIME_OUT = 30 * DateUtils.TIME_MILLSECOND_OF_SECOND;

		private final static int MIN_TIME_OUT = 10 * DateUtils.TIME_MILLSECOND_OF_SECOND;

		/** 超时判断 */
		public boolean checkOverMaxTime() {
			if (this.status.get() == false) {
				return true;
			}
			long time = System.currentTimeMillis() - updateMillisTime;
			if (time > MAX_TIME_OUT) {
				this.status.set(false);
				return true;
			}
			return false;
		}

		/** 发送 */
		public boolean checkOverMinTime() {
			long time = System.currentTimeMillis() - updateMillisTime;
			return time > MIN_TIME_OUT;
		}

		/**
		 * 心跳状态
		 * 
		 * @return
		 */
		public boolean isStatus() {
			return status.get();
		}

		public long getUpdateMillisTime() {
			return updateMillisTime;
		}

		public void setUpdateMillisTime(long updateMillisTime) {
			this.updateMillisTime = updateMillisTime;
		}

		/**
		 * 设置新值，返回旧值
		 * 
		 * @param newValue
		 * @return
		 */
		public boolean getAndSetStatus(boolean newValue) {
			return status.getAndSet(newValue);
		}

		public static int getMaxTimeOut() {
			return MAX_TIME_OUT;
		}

		public static int getMinTimeOut() {
			return MIN_TIME_OUT;
		}

	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

}
