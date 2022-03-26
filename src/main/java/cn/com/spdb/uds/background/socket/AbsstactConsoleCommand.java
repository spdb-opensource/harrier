package cn.com.spdb.uds.background.socket;

import java.io.PrintWriter;

public abstract class AbsstactConsoleCommand implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		StringBuffer buffer = new StringBuffer(128);
		hanlder(param, buffer);
		if (pw != null) {
			pw.append(buffer);
		}
		return buffer.toString();
	}

	public abstract void hanlder(String param, StringBuffer buffer);

    public StringBuffer writeLine(String msg, StringBuffer buffer) {
        buffer.append(msg).append("\r\n");
        return buffer;
    }
}
