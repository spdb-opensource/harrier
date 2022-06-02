package cn.spdb.harrier.rpc.client;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.rpc.common.InterfaceRpcCallBack;
import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;
import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.common.RpcResponse;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.remote.NettyClient;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class ConsumerInterceptor {

	private URI uri;
	private NettyClient nettClient = NettyClient.getInstance();

	public ConsumerInterceptor(URI uri) {
		this.uri = uri;
	}

	@RuntimeType
	public Object intercept(@AllArguments Object[] args, @Origin Method method) {
		RpcRequest request = buildReq(args, method);
		String handlerMethName = request.getHandlerMethName();
		ConsumerConfig consumerConfig = ConsumerConfigCache.getConfigByServersName(handlerMethName);
		if (null == consumerConfig) {
			consumerConfig = cacheServiceConfig(method, handlerMethName);
		}
		int retries = consumerConfig.getRetries();
		Class<?> returnType = method.getReturnType();
		RpcProtocol<RpcRequest> protocol = RpcRequest.buildProtocol(request, consumerConfig.getCompressType());
		while (retries-- > 0) {
			RpcResponse rsp;
			rsp = nettClient.sendMsg(uri, protocol, consumerConfig);
			if (null != rsp && rsp.getStatus() == 0) {
				Object object = rsp.getResult();
				if (JSON.class.isInstance(object)) {
					object = JSON.toJavaObject((JSON) object, returnType);
				}
				return object;
			}
		}

		return new Exception("send msg error");
	}

	private RpcRequest buildReq(Object[] args, Method method) {
		RpcRequest request = new RpcRequest();
		RpcHandler rpcHandler = method.getDeclaringClass().getAnnotation(RpcHandler.class);
		String className = (ObjectUtils.isEmpty(rpcHandler) || ObjectUtils.isEmpty(rpcHandler.value()))
				? method.getDeclaringClass().getSimpleName()
				: rpcHandler.value();
		request.setClassName(className);
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParameters(args);
		return request;
	}

	private ConsumerConfig cacheServiceConfig(Method method, String handlerMethName) {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setHandlerMethName(handlerMethName);
		boolean annotationPresent = method.isAnnotationPresent(RpcMethod.class);
		if (annotationPresent) {
			RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
			consumerConfig.setAsync(rpcMethod.async());
			consumerConfig.setTimeOut(rpcMethod.timeOut());
			consumerConfig.setSeriveCallBackClass(rpcMethod.serviceCallBack());
			if (InterfaceRpcCallBack.class.isAssignableFrom(rpcMethod.serviceCallBack())) {
				consumerConfig.setCallBack(true);
			}
			consumerConfig.setRetries(rpcMethod.retries());
			consumerConfig.setCompressType(rpcMethod.compressType().getType());
		}
		ConsumerConfigCache.putConfig(handlerMethName, consumerConfig);
		return consumerConfig;
	}

}
