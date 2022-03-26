package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;

public class Exit implements InterfaceConsoleCommand {

	public String hanlder(String param, PrintWriter pw) {
		if (pw != null) {
			pw.println("-OK\nAutomation Shutdown.");
		}
		System.exit(0);
		return HttpResultCode.SUCCESS;
	}

}
