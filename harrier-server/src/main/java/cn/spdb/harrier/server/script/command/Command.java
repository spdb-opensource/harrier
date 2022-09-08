package cn.spdb.harrier.server.script.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import cn.spdb.harrier.common.CommandConstant;

import cn.spdb.harrier.server.script.command.handle.LoadSystem;

public enum Command {
	LOADSYSTEM(CommandConstant.LOAD_SYSTEM ,new LoadSystem());

	private String name;
	private String type;
	private InterfaceCommand command;

	
	private Command(String name, InterfaceCommand command) {
		this.command = command;
		this.name = name;
		this.type = command.getType();
	}

	private Command(InterfaceCommand command) {
		this.command = command;
		this.type = command.getType();
		this.name = command.getClass().getSimpleName();
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InterfaceCommand getCommand() {
		return command;
	}

	public void setCommand(InterfaceCommand command) {
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String cmd(String name, String type, String... args) {
		for (Command cmd : values()) {
			if (cmd.getType().equals(type) && cmd.getName().equals(name)) {
				return cmd.getCommand().handle(args);
			}
		}
		return null;
	}

	public static List<String> getCmdName() {
		return Arrays.stream(values()).map(mapper -> mapper.getName()).collect(Collectors.toList());
	}
}
