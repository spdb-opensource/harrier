package cn.com.spdb.uds.background.http;

import java.util.Map;
/*
Rename Variable
 */
public interface InterfaceHttpWorkHandler {

	public Object handler(Map<String, String> parMap, Map<String, Object> objectMap);

}
