package cn.com.spdb.uds.background.socket;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.UdsConstant;

public class BackgroundServer {

	private static Logger logger = LoggerFactory.getLogger(BackgroundServer.class);

	private static final Object KEY = new Object();
	private static int BACKEND_PORT = UdsConstant.BACKEND_PORT;
	private static int BACKEND_TIMEOUT = UdsConstant.BACKEND_TIMEOUT_SECOND;
	private static List<String> IP_LIST = Arrays.asList(new String[] { "127.0.0.1" });

	private static BackgroundServer backgroundServer = null;
	private ServerSocket serverSock = null;

	public static BackgroundServer getInstance() {
		synchronized (KEY) {
			if (backgroundServer == null) {
				backgroundServer = new BackgroundServer();
			}
		}
		return backgroundServer;
	}

	private ExecutorService backendExecutorService;

	public BackgroundServer() {

	}

	/**
	 * 启动后台监控接口
	 */
	public void startBackend() {
		backendExecutorService = Executors.newFixedThreadPool(2);
		backendExecutorService.execute(new Runnable() {
			public void run() {
				try {
					if (serverSock != null) {
						serverSock.close();
					}
					serverSock = new ServerSocket(BACKEND_PORT);
					while (true) {
						try {
							Socket socket = serverSock.accept();
							socket.setSoTimeout(BACKEND_TIMEOUT);
							AbstracIpFilter ipFilter = new AbstracIpFilter() {
								@Override
								public boolean handlerFilter(Socket socket) {
									InetAddress inetAddress = socket.getInetAddress();
									String ip = inetAddress.getHostAddress();
									if (IP_LIST.contains(ip)) {
										return true;
									} else {
										return false;
									}
								}
							};
							if (ipFilter.handlerFilter(socket)) {
								backendExecutorService.execute(new Terminal(socket, new EtlConsoleHanlder()));
								logger.info("backend start port:" + BACKEND_PORT);
							} else {
								logger.info("backend ip filter intercept socket :" + socket.toString());
								socket.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		logger.info("start Backend ok");
	}

	public void shutDown() {
		backendExecutorService.shutdownNow();
	}

	public static List<String> getIP_LIST() {
		return IP_LIST;
	}

	public static void setIP_LIST(List<String> iP_LIST) {
		IP_LIST = iP_LIST;
	}

	/**
	 * IP拦截
	 * @author T-luzl
	 *
	 */
	public abstract class AbstracIpFilter {
		public abstract boolean handlerFilter(Socket socket);
	}
}
