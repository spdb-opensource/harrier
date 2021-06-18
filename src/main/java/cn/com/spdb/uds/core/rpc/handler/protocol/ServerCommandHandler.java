package cn.com.spdb.uds.core.rpc.handler.protocol;

import java.io.File;

import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.EtlConsoleHanlder;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventCallBack;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.dynamicjava.DynamicClassEngine;

@RpcEventProtocol(value = RpcCommand.SERVER_COMMAND, concurrent = 1)
public class ServerCommandHandler implements ServerRpcEventCallBack, ServerRpcEventHandler {

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {
		event.addAttribute(RpcAttrKey.COMMAND, String.valueOf(paramters));
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE_COMMAND, "SEND", paramters);
	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		String command = event.getAttribute(RpcAttrKey.COMMAND);
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE_COMMAND, "RECEIVE", command);
		UdsRpcEvent callbackEvent = event.callBackEvent();
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
				callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
				return callbackEvent;
			}
			hanlderCommand = (InterfaceConsoleCommand) calzz.newInstance();
			String tmp = null;
			if (hanlderCommand != null) {
				tmp = hanlderCommand.hanlder(command.replaceFirst(split[0], "").trim(), null);
			}
			tmp = tmp == null ? "" : tmp;
			if (tmp.startsWith(HttpResultCode.SUCCESS)) {
				callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.SUCCESS);
				UdsLogger.logEvent(LogEvent.HTTP_CONSOLE_COMMAND, command, "call", RpcResultCode.SUCCESS, tmp);
			} else {
				callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
				UdsLogger.logEvent(LogEvent.HTTP_CONSOLE_COMMAND, command, "call", RpcResultCode.ERROR, tmp);
			}
			callbackEvent.addAttribute(RpcAttrKey.CMD_MSG, tmp);
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
			callbackEvent.addAttribute(RpcAttrKey.CMD_MSG, e.getMessage());
			UdsLogger.logEvent(LogEvent.HTTP_ERROR, e.getMessage());
		}
		return callbackEvent;

	}

	@Override
	public void callback(UdsRpcEvent callBackEvent) {
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE_COMMAND, "callback", callBackEvent.getAttributes());
	}
}
