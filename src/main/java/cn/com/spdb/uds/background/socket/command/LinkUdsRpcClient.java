package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.utils.UdsUtils;

public class LinkUdsRpcClient implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		String str = UdsUtils.linkServerMachine();
		if (pw != null) {
			pw.println(str);
		}
		return HttpResultCode.SUCCESS;
	}

}
