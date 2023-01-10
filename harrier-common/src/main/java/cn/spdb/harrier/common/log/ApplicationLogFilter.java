package cn.spdb.harrier.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.spdb.harrier.common.utils.LoggerUtils;

public class ApplicationLogFilter extends Filter<ILoggingEvent> {
	
	private static LogbackPrintStream logbackPrintStream=new LogbackPrintStream(System.err);
	
	private Level level = Level.INFO;

	public void setLevel(String level) {
		this.level = Level.toLevel(level);
	}

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
			return FilterReply.ACCEPT;
		} else if (event.getThreadName().startsWith("Master") 
				|| event.getLoggerName().startsWith("Master")
				|| event.getThreadName().startsWith("Worker") 
				|| event.getLoggerName().startsWith("Worker")
				|| event.getThreadName().startsWith("Monitor") 
				|| event.getLoggerName().startsWith("Monitor")
				|| event.getThreadName().startsWith("Alarm") 
				|| event.getLoggerName().startsWith("Alarm")
				|| event.getLoggerName().startsWith(LoggerUtils.TASK_STEP_LOGGER_PREFIX)
				|| !event.getLevel().isGreaterOrEqual(level)) {
			return FilterReply.DENY;
		}
		return FilterReply.ACCEPT;
	}

}
