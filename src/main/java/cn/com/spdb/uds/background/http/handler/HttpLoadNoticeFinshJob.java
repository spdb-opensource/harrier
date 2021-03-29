package cn.com.spdb.uds.background.http.handler;

import java.util.Map;

import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.LoadNoticeFinshJob;
import cn.com.spdb.uds.background.socket.command.LoadUdsConstantServer;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;

@HttpMapProtocol(value = "/load/noticeFinshJob")
public class HttpLoadNoticeFinshJob extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(null, RpcCommand.SERVER_COMMAND);
		UdsRpcClientManager.getInstance().sendBroadcastMessage(udsRpcEvent, LoadNoticeFinshJob.class.getSimpleName(),
				false);
		return HttpResultCode.SUCCESS;
	}

}
