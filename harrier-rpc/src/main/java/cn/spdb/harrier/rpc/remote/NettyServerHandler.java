package cn.spdb.harrier.rpc.remote;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.common.RpcResponse;
import cn.spdb.harrier.rpc.common.ThreadPoolManager;
import cn.spdb.harrier.rpc.protocol.ProtocolEventType;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.transport.RpcServiceTransportBean;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
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
		RpcProtocol<Object> rpcProtocol = (RpcProtocol<Object>) msg;
		if (rpcProtocol.getProtocolHeader().getEventType() == ProtocolEventType.HEARTBEAT.getType()) {
			logger.debug(ProtocolEventType.HEARTBEAT.getDescription() + ":" + ctx.channel().remoteAddress().toString());
			return;
		}
		if (rpcProtocol.getProtocolHeader().getEventType() == ProtocolEventType.COMPERESS_ERROR.getType()) {
			logger.debug(ProtocolEventType.COMPERESS_ERROR.getDescription() + ":"
					+ ctx.channel().remoteAddress().toString());
			return;
		}

		ThreadPoolManager.getInstance().addExecute(() -> readHandler(ctx, rpcProtocol));
	}

	private void readHandler(ChannelHandlerContext ctx, RpcProtocol<Object> rpcProtocol) {
		RpcRequest request = (RpcRequest) rpcProtocol.getBody();
		RpcResponse response = new RpcResponse();
		response.setStatus((byte) 0);
		String className = request.getClassName();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] arguments = request.getParameters();
		Object result = null;
		try {
			Class<?> serviceClass = RpcServiceTransportBean.getServiceClass(className);
			Method method = serviceClass.getDeclaredMethod(methodName, parameterTypes);
			Object object = RpcServiceTransportBean.getServiceClassObject(className);
			result = method.invoke(object, arguments);
			response.setResult(result);
		} catch (Exception e) {
			response.setStatus((byte) -1);
			e.printStackTrace();
		}
		rpcProtocol.setBody(response);
		rpcProtocol.getProtocolHeader().setEventType(ProtocolEventType.RESPONSE.getType());
		ctx.writeAndFlush(rpcProtocol);
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
