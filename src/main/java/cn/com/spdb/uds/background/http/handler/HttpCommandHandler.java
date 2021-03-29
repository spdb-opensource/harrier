package cn.com.spdb.uds.background.http.handler;

import io.netty.util.internal.StringUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.EtlConsoleHanlder;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.dynamicjava.DynamicClassEngine;

@HttpMapProtocol(value = "/command")
public class HttpCommandHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		String serverName = (String) objectMap.get("server_name");
		String command = (String) objectMap.get("command");
		HashMap<String, String> map = new HashMap<String, String>();
		if (serverName.equals(UdsConstant.SERVER_NAME)) {
			String[] split = command.split(" ");
			String scriptName = split[0];
			InterfaceConsoleCommand hanlderCommand = null;
			try {
				Class<?> calzz = null;
				File file = new File("./script/" + scriptName + ".class");
				if (file.exists()) {
					calzz = DynamicClassEngine.getInstance().loadClass(file);
				} else {
					String packagePath = EtlConsoleHanlder.class.getPackage().getName();
					String classPath = packagePath + ".command." + scriptName;
					calzz = Class.forName(classPath, false, Thread.currentThread().getContextClassLoader());
				}
				if (calzz == null) {
					UdsLogger.logEvent(LogEvent.HTTP_ERROR, "class is null script name:" + scriptName);
					map.put("code", HttpResultCode.ERROR);
					map.put("msg", "class is null script name:" + scriptName);
					return map.toString();
				}
				hanlderCommand = (InterfaceConsoleCommand) calzz.newInstance();
				String tmp = null;
				if (hanlderCommand != null) {
					tmp = hanlderCommand.hanlder(command.replaceFirst(split[0], "").trim(), null);
				}
				if (tmp != null) {
					map.put("code", HttpResultCode.SUCCESS);
					map.put("msg", tmp);
				} else {
					map.put("code", HttpResultCode.ERROR);
					map.put("msg", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				UdsLogger.logEvent(LogEvent.HTTP_ERROR, e.getMessage());
			}
		} else {
			UdsRpcClient udsRpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
			if (udsRpcClient == null || !udsRpcClient.isActive()) {
				return "服务器名字错误，或失活";
			}
			if (StringUtil.isNullOrEmpty(command)) {
				return "命令错误";
			}
			UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(serverName, RpcCommand.SERVER_COMMAND);
			UdsRpcEvent callBackEvent = UdsRpcClientManager.getInstance().sendMessageForUdsRpcEvent(udsRpcClient,
					udsRpcEvent, command);
			Integer code = callBackEvent.getAttribute(RpcAttrKey.CODE);
			String msg = callBackEvent.getAttribute(RpcAttrKey.CMD_MSG);
			String codeStr = (code == null || code.intValue() != RpcResultCode.SUCCESS) ? HttpResultCode.ERROR
					: HttpResultCode.SUCCESS;
			msg = msg == null ? "" : msg;
			map.put("code", codeStr);
			map.put("msg", msg);
		}
		return JSON.toJSONString(map);
	}
}
