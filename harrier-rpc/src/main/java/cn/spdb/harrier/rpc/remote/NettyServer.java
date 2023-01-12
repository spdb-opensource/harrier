package cn.spdb.harrier.rpc.remote;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.rpc.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

	private final EventLoopGroup bossGroup;
	private final EventLoopGroup workGroup;
	private final NettyServerConfig serverConfig;

	private final ServerBootstrap serverBootstrap = new ServerBootstrap();

	private final AtomicBoolean isStarted = new AtomicBoolean(false);
	private final Logger logger = LoggerFactory.getLogger(NettyClient.class);
	private Class<? extends ServerChannel> serverChannelClass;

	public NettyServer(final NettyServerConfig serverConfig) {
		this.serverConfig = serverConfig;
		this.bossGroup = NettyEventLoopFactory.eventLoopGroup(1, "NettyServerBossThread");
		this.workGroup = NettyEventLoopFactory.eventLoopGroup(serverConfig.getWorkerThread(), "NettyServerWorkThread");
		this.serverChannelClass=NettyEventLoopFactory.serverSocketChannelClass();
	}

	public void start() {
		if (isStarted.compareAndSet(false, true)) {
			this.serverBootstrap.group(this.bossGroup, this.workGroup).channel(this.serverChannelClass)
					.option(ChannelOption.SO_REUSEADDR, true)
					.option(ChannelOption.SO_BACKLOG, serverConfig.getSoBacklog())
					.childOption(ChannelOption.SO_KEEPALIVE, serverConfig.isSoKeepalive())
					.childOption(ChannelOption.TCP_NODELAY, serverConfig.isTcpNoDelay())
					.childOption(ChannelOption.SO_SNDBUF, serverConfig.getSendBufferSize())
					.childOption(ChannelOption.SO_RCVBUF, serverConfig.getReceiveBufferSize())
					.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("choose", new NettyChooseSelectHandler());
						}
					});
			ChannelFuture channelFuture = null;
			try {
				channelFuture = serverBootstrap.bind(serverConfig.getListenPort()).sync();
			} catch (Exception e) {
				logger.warn(String.format("bind to %d port", serverConfig.getListenPort()), e);
			}
			if (channelFuture.isSuccess()) {
				logger.debug("success ", channelFuture.channel().toString());
			} else if (channelFuture.cause() != null) {
				logger.error(channelFuture.cause().getMessage());
			} else {
				
			}
		}
	}

	public void shutdown() {
		if (isStarted.compareAndSet(true, false)) {
			try {
				if (bossGroup != null) {
					bossGroup.shutdownGracefully();
				}
				if (workGroup != null) {
					workGroup.shutdownGracefully();
				}
			} catch (Exception e) {
				logger.warn("shutdown error ", e);
			}
		}

	}

}
