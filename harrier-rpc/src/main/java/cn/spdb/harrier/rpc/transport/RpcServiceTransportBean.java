package cn.spdb.harrier.rpc.transport;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.uitls.ClassUtils;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;


public class RpcServiceTransportBean {

	private static final Logger logger = LoggerFactory.getLogger(RpcServiceTransportBean.class);

	private static HashMap<String, RpcServiceHandlerBeanIner> serviceHandlerMap = new HashMap<String, RpcServiceTransportBean.RpcServiceHandlerBeanIner>();

	static {
		Package packagea = RpcServiceTransportBean.class.getPackage();
		scanServiceClassScan(packagea);
	}

	public static Object getServiceClassObject(String handlerName) {
		return serviceHandlerMap.get(handlerName).getClassObject();
	}

	public static Class<?> getServiceClass(String handlerName) {
		return serviceHandlerMap.get(handlerName).getClazz();
	}

	public static void addServiceClass(String handlerName, Class<?> handlerClass) {
		serviceHandlerMap.putIfAbsent(handlerName, new RpcServiceHandlerBeanIner(handlerClass));
	}

	public static void scanServiceClassScan(Package packagea) {
		ClassUtils.scanPackage(packagea, clazz->{return clazz.isAnnotationPresent(RpcServiceHandler.class);})
		.forEach(handlerClass -> {
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
					return null;
				}
			}
			return object;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public RpcServiceHandlerBeanIner(Class<?> clazz) {
			this.clazz = clazz;
		}
	}
}
