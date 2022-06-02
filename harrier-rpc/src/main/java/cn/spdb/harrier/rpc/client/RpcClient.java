package cn.spdb.harrier.rpc.client;

import java.lang.reflect.InvocationTargetException;

import cn.spdb.harrier.common.uitls.URI;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class RpcClient implements InterfaceRpcClient {

	private static class RpcClientIner {
		private static RpcClient instance = new RpcClient();
	}

	public static RpcClient getInstance() {
		return RpcClientIner.instance;
	}

	@Override
	public <T> T create(Class<T> clazz, URI uri) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return new ByteBuddy().subclass(clazz).method(ElementMatchers.isDeclaredBy(clazz))
				.intercept(MethodDelegation.to(new ConsumerInterceptor(uri))).make().load(getClass().getClassLoader())
				.getLoaded().getDeclaredConstructor().newInstance();
	}
	

}
