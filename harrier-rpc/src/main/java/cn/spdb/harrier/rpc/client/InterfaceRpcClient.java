package cn.spdb.harrier.rpc.client;

import java.lang.reflect.InvocationTargetException;

import cn.spdb.harrier.common.utils.URI;

public interface InterfaceRpcClient {

	<T> T create(Class<T> calzz, URI uri) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
