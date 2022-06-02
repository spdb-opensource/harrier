package cn.spdb.harrier.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.spdb.harrier.common.uitls.LoggerUtils;

public class OtherLogFilter extends Filter<ILoggingEvent> {
	private Level level = Level.INFO;
	public void setLevel(String level) {
		this.level = Level.toLevel(level);
	}
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if(event.getThreadName().startsWith("Master")
				||event.getThreadName().startsWith("Worker")
				||event.getLoggerName().startsWith(LoggerUtils.TASK_STEP_LOGGER_PREFIX)
				||!event.getLevel().isGreaterOrEqual(level)) {
			return FilterReply.DENY;
		}
		return FilterReply.ACCEPT;
	}

}
