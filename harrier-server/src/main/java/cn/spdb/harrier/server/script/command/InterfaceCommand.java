package cn.spdb.harrier.server.script.command;

import cn.spdb.harrier.common.CommandConstant;

public interface InterfaceCommand {

	String handle(String... args);

	default String getType() {
		return CommandConstant.ALL;
	}
}
