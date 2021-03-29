package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.utils.IceKey;

public class EncodeProperties implements InterfaceConsoleCommand {

	/**
	 * 输入的参数是 PropertiesKEY  PropertiesValue
	 */
	@Override
	public String hanlder(String param, PrintWriter pw) {
		StringBuffer stringBuffer = new StringBuffer();
		String[] tmp = param.split(" ");
		if (tmp.length > 1) {
			IceKey ikey = new IceKey(0);
			String value = ikey.encode(tmp[1], tmp[0]);
			stringBuffer.append("succ");
			stringBuffer.append("\n").append(value);
			System.out.println(tmp[0] + ":" + value);
		} else {
			stringBuffer.append("error");
		}
		if (pw != null) {
			pw.println(stringBuffer.toString());
		}
		return stringBuffer.toString();
	}

}
