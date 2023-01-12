package cn.spdb.harrier.rpc.transport.script;

import java.io.File;

import cn.spdb.harrier.common.dynamicjava.DynamicClassManager;
import cn.spdb.harrier.common.utils.PropertyUtils;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;

@RpcServiceHandler("IRpcScriptService")
public class RpcScriptService {

	private static boolean enable = PropertyUtils.getBoolean("harrier.rpc.script.enable", false);
	private static String clazzDir = PropertyUtils.getString("harrier.rpc.script.clazz.path", "");
	private static String dirPath = PropertyUtils.getString("harrier.rpc.script.dir.path", "./script/");


	public String script(String scriptName, String... args) {
		if (!enable) {
			return "system env 'rpc.script.enable:false'";
		}
		try {
			Class<?> calzz = null;
			File file = new File(dirPath + scriptName + ".java");
			if (file.exists()) {
				calzz = DynamicClassManager.getInstance().loadFromJavaFile(file);
			} else {
				if (!file.exists()) {
					file = new File(dirPath + scriptName + ".class");
				}
				if (file.exists()) {
					calzz = DynamicClassManager.getInstance().loadClass(file);
				} else {
					calzz = Class.forName(clazzDir + scriptName, false, Thread.currentThread().getContextClassLoader());
				}
			}
			if (calzz != null) {
				InterfaceCommand hanlderCommand = (InterfaceCommand) calzz.newInstance();
				return hanlderCommand.handle(args);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "";
	}
}
