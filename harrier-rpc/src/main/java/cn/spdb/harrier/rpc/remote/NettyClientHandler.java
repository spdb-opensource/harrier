package cn.spdb.harrier.rpc.remote;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.rpc.client.ConsumerConfig;
import cn.spdb.harrier.rpc.client.ConsumerConfigCache;
import cn.spdb.harrier.rpc.client.RpcFuture;
import cn.spdb.harrier.rpc.client.RpcRequestCache;
import cn.spdb.harrier.rpc.client.RpcRequestTable;
import cn.spdb.harrier.rpc.common.RpcResponse;
import cn.spdb.harrier.rpc.common.ThreadPoolManager;
import cn.spdb.harrier.rpc.protocol.ProtocolEventType;
import cn.spdb.harrier.rpc.protocol.ProtocolHeader;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class NettyClientHandler extends ChannelDuplexHandler {
	private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ctx.channel().close();
		logger.debug("channel close :" + ctx.channel().remoteAddress().toString());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof RpcProtocol<?>) {			
			RpcProtocol<Object> rpcProtocol = (RpcProtocol<Object>) msg;
			if (rpcProtocol.getProtocolHeader().getEventType() == ProtocolEventType.COMPERESS_ERROR.getType()) {
				logger.debug(ProtocolEventType.COMPERESS_ERROR.getDescription() + ":"
						+ ctx.channel().remoteAddress().toString());
				return;
			}
			RpcResponse response = (RpcResponse) rpcProtocol.getBody();
			long reqId = rpcProtocol.getProtocolHeader().getRequestId();
			RpcRequestCache requestCache = RpcRequestTable.get(reqId);
			logger.warn("client:"+reqId);
			ThreadPoolManager.getInstance().addExecute(() -> readHandler(response, requestCache, reqId));
		}
	}

	private void readHandler(RpcResponse response, RpcRequestCache requestCache, Long reqId) {
		String serviceName = requestCache.getHandlerMethName();
		ConsumerConfig consumerConfig = ConsumerConfigCache.getConfigByServersName(serviceName);
		if (Boolean.FALSE.equals(consumerConfig.getAsync())) {
			RpcFuture future = requestCache.getFuture();
			RpcRequestTable.remove(reqId);
			future.done(response);
			return;
		}
		if (Boolean.FALSE.equals(consumerConfig.getCallBack())) {
			return;
		}
		if (response.getStatus() == 0) {
			try {
				consumerConfig.getSeriveCallBackClass().getDeclaredConstructor().newInstance()
						.run(response.getResult());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		if (evt instanceof IdleStateEvent) {
			logger.debug("IdleStateEvent triggered send channel " + ctx.channel());
			RpcProtocol<?> protocol = new RpcProtocol<Object>();
			ProtocolHeader header = new ProtocolHeader();
			header.setEventType(ProtocolEventType.HEARTBEAT.getType());
			protocol.setProtocolHeader(header);
			ctx.channel().writeAndFlush(protocol);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("exceptionCaught :{}", cause.getMessage(), cause);
		super.exceptionCaught(ctx, cause);
		ctx.channel().close();
	}
}
