package cn.com.spdb.uds.background.http;

import java.util.Map;

/**
 * Http处理接口 
 * @author T-luzl
 *
 */
public abstract class AbstractHttpPostBodyWorkHandler implements InterfaceHttpWorkeHandler {

	public abstract String handler(Map<String, Object> objectMap);

	@Override
	public Object handler(Map<String, String> parMap, Map<String, Object> objectMap) {
		return handler(objectMap);
	}

}
