package cn.com.spdb.uds.background.socket.command;

import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.master.MasterFactory;
import cn.com.spdb.uds.core.master.MasterManager;

public class SeeSignalJobQueue extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		MasterFactory masterFactory = MasterManager.getInstance().getMasterFactory();
		
	}

	public StringBuffer writeLine(String msg, StringBuffer buffer) {
		buffer.append(msg).append("\r\n");
		return buffer;
	}
}
