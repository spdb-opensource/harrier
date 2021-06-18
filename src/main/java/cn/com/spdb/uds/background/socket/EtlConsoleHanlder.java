package cn.com.spdb.uds.background.socket;

import java.io.File;
import java.io.PrintWriter;

import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.dynamicjava.DynamicClassEngine;

public class EtlConsoleHanlder implements InterfaseTerminalHanlder {

	public void handler(String msg, PrintWriter pw) {
		char[] chars = msg.toCharArray();
		StringBuffer buffer = new StringBuffer(msg.length());
		int arrLen = chars.length;
		char c;
		for (int i = 0; i < arrLen; i++) {
			c = chars[i];
			if (c == '') {// 去除光标
				i++;
				i++;
				continue;
			}
			if (c != 8) {// backspace键 则删除前1个字符串
				buffer.append(c);
			} else {
				int idx = buffer.length() - 1;
				if (idx >= 0) {
					buffer.deleteCharAt(idx);
				}
			}
		}
		msg = buffer.toString();
		UdsLogger.logEvent(LogEvent.BACK_CONSOLE, "Console input :" + msg);
		String[] split = msg.split(" ");
		String scriptName = split[0];
		InterfaceConsoleCommand hanlderCommand = null;
		try {
			Class<?> calzz = null;
			File file = new File("./script/" + scriptName + ".java");
			if (file.exists()) {
				calzz = DynamicClassEngine.getInstance().loadFromJavaFile(file);
			} else {
				if (!file.exists()) {
					file = new File("./script/" + scriptName + ".class");
				}
				if (file.exists()) {
					calzz = DynamicClassEngine.getInstance().loadClass(file);
				} else {
					String packagePath = EtlConsoleHanlder.class.getPackage().getName();
					String classPath = packagePath + ".command." + scriptName;
					calzz = Class.forName(classPath, false, Thread.currentThread().getContextClassLoader());
				}
			}
			if (calzz == null) {
				pw.println("class is null script name:" + scriptName);
				UdsLogger.logEvent(LogEvent.BACK_CONSOLE, "class is null script name:" + scriptName);
				return;
			}
			hanlderCommand = (InterfaceConsoleCommand) calzz.newInstance();
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.BACK_ERROR, e.getMessage());
		}
		if (hanlderCommand != null) {
			try {
				hanlderCommand.hanlder(msg.replaceFirst(split[0], "").trim(), pw);
			} catch (Exception e) {
				UdsLogger.logEvent(LogEvent.BACK_ERROR, e.getMessage());
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
			pw.println();
			pw.println("end");
			pw.flush();
		}
	}

}
