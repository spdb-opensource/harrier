package cn.com.spdb.uds.background.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.ClassUtils;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.ClassUtils.ClassFilter;

public class HttpServer {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

	private static HashMap<String, InterfaceHttpWorkHandler> HTTP_HANDER = new HashMap<String, InterfaceHttpWorkHandler>();

	private static final Object KEY = new Object();

	private static int HTTP_PORT = UdsConstant.HTTP_PORT;

	private ChannelFuture channelFuture = null;

	private ExecutorService httpExecutorService;

	private static HttpServer HttpServer = null;

	public static HttpServer getInstance() {
		synchronized (KEY) {
			if (HttpServer == null) {
				HttpServer = new HttpServer();
			}
		}
		return HttpServer;
	}

	private boolean exit = false;

	private void init() {
		/**
		 * 扫包加载 SERVER_HANDLERS
		 */
		String packPath = this.getClass().getPackage().getName();
		LOGGER.info("scanPackage : " + packPath);
		ClassUtils.scanPackage(packPath, new ClassFilter() {
			@Override
			public boolean actionFilter(Class<?> clazz) {
				int modifers = clazz.getModifiers();
				if (Modifier.isAbstract(modifers) || Modifier.isInterface(modifers)) {
					return false;
				}
				if (InterfaceHttpWorkHandler.class.isAssignableFrom(clazz)) {
					HttpMapProtocol rpcEventProtocol = clazz.getAnnotation(HttpMapProtocol.class);
					if (rpcEventProtocol == null) {
						return false;
					}
					String path = rpcEventProtocol.value();

					InterfaceHttpWorkHandler handler = HTTP_HANDER.get(path);

					if (handler != null) {
						LOGGER.error("handler is exist please check http path: " + path + " class: " + clazz);
						return false;
					}
					try {
						handler = (InterfaceHttpWorkHandler) clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
					LOGGER.info("handler  load handler http path: " + path + " class: " + clazz);
					HTTP_HANDER.put(path, handler);
				}
				return true;
			}
		});
	}

	public void start() {
		httpExecutorService = Executors.newSingleThreadExecutor();
		httpExecutorService.submit(new Runnable() {
			@Override
			public void run() {
				int size = UdsConstant.AVAILABLE_PROCESSORS_SIZE > 32 ? 32 : UdsConstant.AVAILABLE_PROCESSORS_SIZE;
				EventLoopGroup bossGroup = new NioEventLoopGroup(size);
				EventLoopGroup workGroup = new NioEventLoopGroup(size);
				ServerBootstrap bootstrap = new ServerBootstrap();
				bootstrap.group(bossGroup, workGroup);
				bootstrap.channel(NioServerSocketChannel.class);
				bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// HTTP 的请求和相应
						ch.pipeline().addLast("codec", new HttpServerCodec());
						// HTTP 消息内容的大写
						ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 5));
						// 压缩数据
						ch.pipeline().addLast("compressor", new HttpContentCompressor());
						ch.pipeline().addLast("handler", new HttpServerHandler());

					}
				});

				bootstrap.option(ChannelOption.SO_BACKLOG, 128);
				bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
				bootstrap.option(ChannelOption.SO_REUSEADDR, true);

				try {
					channelFuture = bootstrap.bind(HTTP_PORT).sync();
					LOGGER.info("start http server prot " + HTTP_PORT);
					while (!exit) {
						Thread.sleep(5 * DateUtils.TIME_MILLSECOND_OF_SECOND);
					}
					channelFuture.channel().close().sync();
					LOGGER.info("close http server prot " + HTTP_PORT);
				} catch (InterruptedException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				} finally {
					bossGroup.shutdownGracefully();
					workGroup.shutdownGracefully();
				}

			}
		});

	}

	public void shutDown() {
		try {
			if (channelFuture != null) {
				channelFuture.channel().close().sync();
			}
			httpExecutorService.shutdownNow();
			LOGGER.info("shutDown http server prot " + HTTP_PORT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		}
	}

	public InterfaceHttpWorkHandler getHttpMapHandler(String path) {
		InterfaceHttpWorkHandler handler = HTTP_HANDER.get(path);
		return handler;
	}

	public HttpServer() {
		init();
	}

	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer();
		httpServer.start();
		System.out.println("http server start");
	}
}
