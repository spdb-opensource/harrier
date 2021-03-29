package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;

public class LoadNoticeFinshJob implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		UdsConstant.loadNoticeFinshJob();
		return HttpResultCode.SUCCESS;
	}

}
