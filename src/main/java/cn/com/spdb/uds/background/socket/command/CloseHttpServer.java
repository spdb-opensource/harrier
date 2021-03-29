package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.http.HttpServer;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;

public class CloseHttpServer implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		HttpServer.getInstance().shutDown();
		return HttpResultCode.SUCCESS;
	}

}
