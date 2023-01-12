package cn.spdb.harrier.rpc.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.common.RpcResponse;
import cn.spdb.harrier.rpc.common.ThreadPoolManager;
import cn.spdb.harrier.rpc.protocol.ProtocolEventType;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.transport.RpcServiceTransportBean;
import cn.spdb.harrier.rpc.transport.http.HttpServiceTransportBean;
import cn.spdb.harrier.rpc.transport.http.InterfaceHttpHandler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyServerHandler extends ChannelDuplexHandler {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("channel close :" + ctx.channel().remoteAddress().toString());
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.debug("client connect success  :" + ctx.channel().remoteAddress().toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest httpRequest = (FullHttpRequest) msg;
			ThreadPoolManager.getInstance().addExecute(() -> readHttpHandler(ctx, httpRequest));
		} else if (msg instanceof WebSocketFrame) {
			WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
			ThreadPoolManager.getInstance().addExecute(() -> readWebSocketHandler(ctx, webSocketFrame));
		} else if (msg instanceof RpcProtocol<?>) {
			RpcProtocol<Object> rpcProtocol = (RpcProtocol<Object>) msg;
			if (rpcProtocol.getProtocolHeader().getEventType() == ProtocolEventType.HEARTBEAT.getType()) {
				logger.debug(
						ProtocolEventType.HEARTBEAT.getDescription() + ":" + ctx.channel().remoteAddress().toString());
				return;
			}
			if (rpcProtocol.getProtocolHeader().getEventType() == ProtocolEventType.COMPERESS_ERROR.getType()) {
				logger.debug(ProtocolEventType.COMPERESS_ERROR.getDescription() + ":"
						+ ctx.channel().remoteAddress().toString());
				return;
			}
			ThreadPoolManager.getInstance().addExecute(() -> readRpcHandler(ctx, rpcProtocol));
		} else {
			ctx.writeAndFlush(msg);
			return;
		}

	}

	private void readWebSocketHandler(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) {

	}

	private void readRpcHandler(ChannelHandlerContext ctx, RpcProtocol<Object> rpcProtocol) {
		RpcResponse response = new RpcResponse();
		RpcRequest request = (RpcRequest) rpcProtocol.getBody();
		response.setStatus((byte) 0);
		String className = request.getClassName();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] arguments = request.getParameters();
		Object result = null;
		try {
			result = RpcServiceTransportBean.invoke(className, methodName, parameterTypes, arguments);
		} catch (Exception e) {
			response.setStatus((byte) -1);
			logger.error("netty servre execute error,service name:{} method name:{}", className + methodName, e);
		}
		response.setResult(result);
		rpcProtocol.setBody(response);
		rpcProtocol.getProtocolHeader().setEventType(ProtocolEventType.RESPONSE.getType());
		logger.warn("server:" + rpcProtocol.getProtocolHeader().getRequestId());
		ctx.writeAndFlush(rpcProtocol);
	}

	private void readHttpHandler(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
		String uri = httpRequest.uri();// 路径
		QueryStringDecoder decoder = new QueryStringDecoder(uri);
		String path = decoder.path();
		InterfaceHttpHandler handler = HttpServiceTransportBean.getHttpHandler(path);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
		if (handler != null) {
			response = handler.handler(httpRequest);
		}
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			logger.debug("IdleStateEvent triggered send channel " + ctx.channel());
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("exceptionCaught :{}", cause.getMessage(), cause);
		ctx.channel().close();
	}
}
