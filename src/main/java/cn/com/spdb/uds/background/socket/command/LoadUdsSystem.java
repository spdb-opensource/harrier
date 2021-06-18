package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;

public class LoadUdsSystem implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		UdsConstant.loadUdsSystemMap();
		return "ok";
	}

}
