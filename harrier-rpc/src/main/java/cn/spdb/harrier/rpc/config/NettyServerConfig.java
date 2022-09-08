
package cn.spdb.harrier.rpc.config;

/**
 * netty server config
 */
public class NettyServerConfig {

	/**
	 * init the server connectable queue
	 */
	private int soBacklog = 1024;

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
	 * worker threads，default get machine cpus
	 */
	private int workerThread = Runtime.getRuntime().availableProcessors();

	/**
	 * listen port
	 */
	private int listenPort = 10286;

	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public int getSoBacklog() {
		return soBacklog;
	}

	public void setSoBacklog(int soBacklog) {
		this.soBacklog = soBacklog;
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

	public int getWorkerThread() {
		return workerThread;
	}

	public void setWorkerThread(int workerThread) {
		this.workerThread = workerThread;
	}
}
