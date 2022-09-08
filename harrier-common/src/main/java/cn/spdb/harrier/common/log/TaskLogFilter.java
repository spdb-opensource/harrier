
package cn.spdb.harrier.common.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.spdb.harrier.common.utils.LoggerUtils;

/**
 * task log filter
 */
public class TaskLogFilter extends Filter<ILoggingEvent> {


    private Level level=Level.INFO;

    public void setLevel(String level) {
        this.level = Level.toLevel(level);
    }
	
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getLoggerName().startsWith(LoggerUtils.TASK_STEP_LOGGER_PREFIX)
				&&event.getLevel().isGreaterOrEqual(level)
				) {
			return FilterReply.ACCEPT;
		}
		return FilterReply.DENY;
	}
	
}