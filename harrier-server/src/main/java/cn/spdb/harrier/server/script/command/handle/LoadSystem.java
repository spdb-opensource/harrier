package cn.spdb.harrier.server.script.command.handle;

import org.apache.commons.lang3.ObjectUtils;

import cn.spdb.harrier.common.CommandConstant;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.cache.SystemCache;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.server.script.command.InterfaceCommand;

public class LoadSystem implements InterfaceCommand {

	@Override
	public String handle(String... args) {
		SystemCache systemCache = BeanContext.getBean(SystemCache.class);
		if (ObjectUtils.isNotEmpty(systemCache)) {
			if (args.length > 1) {
					systemCache.delete(args[0], Symbol.XING_HAO);
					systemCache.delete(args[0], args[1]);
			} else {
				systemCache.load();
			}
		}
		return CommandConstant.SUCCESS;
	}

	@Override
	public String getType() {
		return CommandConstant.MASTER;
	}
}
