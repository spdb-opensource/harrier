
package cn.spdb.harrier.rpc.config;

/**
 * netty client config
 */
public class NettyClientConfig {

	/**
	 * worker threads，default get machine cpus
	 */
	private int workerThreads = Runtime.getRuntime().availableProcessors();

	/**
	 * whether tpc delay
	 */
	private boolean tcpNoDelay = true;

	/**
	 * whether keep alive
	 */
	private boolean soKeepalive = true;

	/**
	 * send buffer size
	 */
	private int sendBufferSize = 65535;

	/**
	 * receive buffer size
	 */
	private int receiveBufferSize = 65535;

	/**
	 * connect timeout millis
	 */
	private int connectTimeoutMillis = 3000;

	private boolean needReconnect = true;

	public int getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	public boolean isSoKeepalive() {
		return soKeepalive;
	}

	public void setSoKeepalive(boolean soKeepalive) {
		this.soKeepalive = soKeepalive;
	}

	public int getSendBufferSize() {
		return sendBufferSize;
	}

	public void setSendBufferSize(int sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}

	public int getReceiveBufferSize() {
		return receiveBufferSize;
	}

	public void setReceiveBufferSize(int receiveBufferSize) {
		this.receiveBufferSize = receiveBufferSize;
	}

	public int getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(int connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public boolean isNeedReconnect() {
		return needReconnect;
	}

	public void setNeedReconnect(boolean needReconnect) {
		this.needReconnect = needReconnect;
	}
	
}
