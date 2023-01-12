package cn.spdb.harrier.rpc.transport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.utils.ClassUtils;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;

public class RpcServiceTransportBean {

	private static final Logger logger = LoggerFactory.getLogger(RpcServiceTransportBean.class);

	private static HashMap<String, RpcServiceHandlerBeanIner> serviceHandlerMap = new HashMap<String, RpcServiceTransportBean.RpcServiceHandlerBeanIner>();

	static {
		Package packagea = RpcServiceTransportBean.class.getPackage();
		scanServiceClassScan(packagea);
	}

	public static Object getServiceClassObject(String handlerName) {
		RpcServiceHandlerBeanIner beanIner = serviceHandlerMap.get(handlerName);
		return beanIner == null ? null : beanIner.getClassObject();
	}

	public static Method getMethod(String handlerName, String name, Class<?>[] parameterTypes) {
		RpcServiceHandlerBeanIner beanIner = serviceHandlerMap.get(handlerName);
		return beanIner == null ? null : beanIner.getMethod(name, parameterTypes);
	}

	public static Class<?> getServiceClass(String handlerName) {
		RpcServiceHandlerBeanIner beanIner = serviceHandlerMap.get(handlerName);
		return beanIner == null ? null : beanIner.getClazz();
	}

	public static Object invoke(String handlerName, String name, Class<?>[] parameterTypes, Object[] arguments) {
		RpcServiceHandlerBeanIner beanIner = serviceHandlerMap.get(handlerName);
		return beanIner == null ? null : beanIner.invoke(name, parameterTypes, arguments);
	}

	public static void addServiceClass(String handlerName, Class<?> handlerClass) {
		serviceHandlerMap.putIfAbsent(handlerName, new RpcServiceHandlerBeanIner(handlerClass));
	}

	public static void scanServiceClassScan(Package packagea) {
		ClassUtils.scanPackage(packagea, clazz -> {
			return clazz.isAnnotationPresent(RpcServiceHandler.class);
		}).forEach(handlerClass -> {
			RpcServiceHandler handler = handlerClass.getAnnotation(RpcServiceHandler.class);
			String handlerName = handler.value();
			if (serviceHandlerMap.containsKey(handlerName)) {
				logger.error("load rpc service class name is exist {} -> {} ", handlerName, handlerClass);
			}
			serviceHandlerMap.put(handlerName, new RpcServiceHandlerBeanIner(handlerClass));
			logger.info("load rpc service class name {} -> {} ", handlerName, handlerClass);
		});
	}

	private static class RpcServiceHandlerBeanIner {
		private Class<?> clazz;
		private Object object;

		public Object getClassObject() {
			if (object == null) {
				try {
					object = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return object;
		}

		public Object invoke(String name, Class<?>[] parameterTypes, Object[] arguments) {
			Method method = getMethod(name, parameterTypes);
			Object object = getClassObject();
			if (method == null || object == null) {
				return null;
			}
			try {
				return method.invoke(object, arguments);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public Method getMethod(String name, Class<?>... parameterTypes) {
			Method method = null;
			try {
				method = clazz.getMethod(name, parameterTypes);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			return method;
		}

		public RpcServiceHandlerBeanIner(Class<?> clazz) {
			this.clazz = clazz;
		}
	}
}
