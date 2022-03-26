package cn.com.spdb.uds.background.socket.command;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;

public class SeeRpcClientMap extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		ConcurrentHashMap<String, UdsRpcClient> map = UdsRpcClientManager.getInstance().getRPC_CLIENT_MAP();
		for (Entry<String, UdsRpcClient> en : map.entrySet()) {
			buffer.append(en.toString()).append("\r\n");
		}
	}

	public StringBuffer writeLine(String msg, StringBuffer buffer) {
		buffer.append(msg).append("\r\n");
		return buffer;
	}


}
