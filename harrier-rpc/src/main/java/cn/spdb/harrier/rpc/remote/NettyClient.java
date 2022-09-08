package cn.spdb.harrier.rpc.remote;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.rpc.client.ConsumerConfig;
import cn.spdb.harrier.rpc.client.RpcFuture;
import cn.spdb.harrier.rpc.client.RpcRequestCache;
import cn.spdb.harrier.rpc.client.RpcRequestTable;
import cn.spdb.harrier.rpc.code.NettyDecoder;
import cn.spdb.harrier.rpc.code.NettyEncoder;
import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.common.RpcResponse;
import cn.spdb.harrier.rpc.config.NettyClientConfig;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {

	private static class NettyClientInner {
		private static final NettyClient instance = new NettyClient(new NettyClientConfig());
	}

	public static NettyClient getInstance() {
		return NettyClient.NettyClientInner.instance;
	}

	private final Logger logger = LoggerFactory.getLogger(NettyClient.class);

	private final EventLoopGroup workGroup;
	private final NettyClientConfig nettyClientConfig;
	private final Class<? extends SocketChannel> socketChannelClass;
	private final Bootstrap bootstrap = new Bootstrap();

	private final AtomicBoolean isStarted = new AtomicBoolean(false);

	private final ConcurrentHashMap<URI, Channel> channelMap = new ConcurrentHashMap<URI, Channel>();


	public Channel getChannel(URI uri) {
		Channel channel = channelMap.get(uri);
		if (channel != null && channel.isActive()) {
			return channel;
		}
		return createChannel(uri, true);
	}

	private Channel createChannel(URI uri, boolean isSync) {
		ChannelFuture channelFuture;
		try {
			synchronized (bootstrap) {
				channelFuture = bootstrap.connect(new InetSocketAddress(uri.getIp(), uri.getPort()));
				if (isSync) {
					channelFuture.sync();
				}
				if (channelFuture.isSuccess()) {
					Channel channel = channelFuture.channel();
					channelMap.put(uri, channel);
					return channel;
				}
			}
		} catch (Exception e) {
			logger.warn(String.format("connect to %s error", uri), e);
		}
		return null;
	}

	private NettyClient(final NettyClientConfig clientConfig) {
		this.nettyClientConfig = clientConfig;
		this.workGroup = NettyEventLoopFactory.eventLoopGroup(nettyClientConfig.getWorkerThreads(),
				"NettyClientWorkThread");
		this.socketChannelClass=NettyEventLoopFactory.socketChannelClass();
		this.start();
	}

	private void start() {
		this.bootstrap.group(workGroup).channel(this.socketChannelClass)
				.option(ChannelOption.SO_KEEPALIVE, nettyClientConfig.isSoKeepalive())
				.option(ChannelOption.TCP_NODELAY, nettyClientConfig.isTcpNoDelay())
				.option(ChannelOption.SO_SNDBUF, nettyClientConfig.getSendBufferSize())
				.option(ChannelOption.SO_RCVBUF, nettyClientConfig.getReceiveBufferSize())
				.handler(new LoggingHandler(LogLevel.DEBUG)).handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("encoder", new NettyEncoder())
								.addLast("decoder", new NettyDecoder(RpcResponse.class))
								.addLast("client-idle-handler",
										new IdleStateHandler(6 * 1000, 0, 0, TimeUnit.MILLISECONDS))
								.addLast("handler", new NettyClientHandler());

					}
				});
		isStarted.compareAndSet(false, true);
	}

	public RpcResponse sendMsg(final URI uri, RpcProtocol<RpcRequest> protocol,ConsumerConfig consumerConfig) {
		Channel channel = getChannel(uri);
		assert channel != null;
		RpcRequest request = protocol.getBody();
		RpcRequestCache rpcRequestCache = new RpcRequestCache();
		String handlerMethName = request.getHandlerMethName();
		rpcRequestCache.setHandlerMethName(handlerMethName);
		Long reqId = protocol.getProtocolHeader().getRequestId();
		RpcFuture future = null;
		if (consumerConfig.getAsync() == false) {
			future = new RpcFuture(request, reqId);
			rpcRequestCache.setFuture(future);
		}
		RpcRequestTable.put(protocol.getProtocolHeader().getRequestId(), rpcRequestCache);
		channel.writeAndFlush(protocol);
		RpcResponse rpcResponse = null;
		if (consumerConfig.getAsync() == true) {
			rpcResponse = new RpcResponse();
			rpcResponse.setStatus((byte) 0);
			rpcResponse.setResult(null);
			return rpcResponse;
		}
		try {
			assert future != null;
			rpcResponse = future.get(consumerConfig.getTimeOut(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("send msg error service name is {}", handlerMethName);
			Thread.currentThread().interrupt();
		}

		return rpcResponse;
	}

	public void close() {
		if (isStarted.compareAndSet(true, false)) {
			try {
				closeChannels();
				if (workGroup != null) {
					this.workGroup.shutdownGracefully();
				}
			} catch (Exception e) {
				logger.error("netty client close exception", e);
			}
			logger.info("netty client closed");
		}
	}

	private void closeChannels() {
		for (Channel channel : this.channelMap.values()) {
			channel.close();
		}
		this.channelMap.clear();
	}

}
