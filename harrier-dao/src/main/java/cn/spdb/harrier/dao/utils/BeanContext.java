
package cn.spdb.harrier.dao.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * bean context
 */
@Component
public class BeanContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * get bean
	 * 
	 * @param name class name
	 * @param <T>  generic
	 * @return target object
	 * @throws BeansException if error throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * get bean
	 * 
	 * @param clazz clazz
	 * @param <T>   generic
	 * @return target object
	 * @throws BeansException if error throws BeansException
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return applicationContext.getBean(clazz);
	}

	/**
	 * set applicationcontext
	 * 
	 * @param applicationContext applicationContext
	 * @throws BeansException if error throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanContext.applicationContext = applicationContext;
	}
	
	
	public static <T> Map<String, T> getBeanOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
		
	}
}
