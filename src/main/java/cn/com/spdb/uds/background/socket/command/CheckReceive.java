package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.master.MasterManager;

public class CheckReceive implements InterfaceConsoleCommand {
	
	@Override
	public String hanlder(String param, PrintWriter pw) {
		StringBuffer stringBuffer = new StringBuffer();
		if (param.equals("START")) {
			if (UdsConstant.IS_PRIMARY_SERVER) {
				MasterManager.getInstance().setCheckRecive(true);
				stringBuffer.append("start check rceive dir server name :" + UdsConstant.SERVER_NAME);
			} else {
				stringBuffer.append("not PRIMARY_SERVER");
			}
		}
		else if (param.equals("STOP")) {
			if (UdsConstant.IS_PRIMARY_SERVER) {
				MasterManager.getInstance().setCheckRecive(false);
				stringBuffer.append("stop check rceive dir server name :" + UdsConstant.SERVER_NAME);
			} else {
				stringBuffer.append("not PRIMARY_SERVER");
			}
		} else {
			stringBuffer.append("param is error " + param);
		}
		if (pw != null) {
			pw.println(stringBuffer.toString());
		}
		return stringBuffer.toString();
	}

}
