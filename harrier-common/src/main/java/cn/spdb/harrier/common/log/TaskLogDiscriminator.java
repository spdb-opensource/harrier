package cn.spdb.harrier.common.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.AbstractDiscriminator;
import cn.spdb.harrier.common.uitls.LoggerUtils;

public class TaskLogDiscriminator extends AbstractDiscriminator<ILoggingEvent> {

	private String key;

	@Override
	public String getDiscriminatingValue(ILoggingEvent event) {
		String loggerName = event.getLoggerName();
		String prefix = LoggerUtils.TASK_STEP_LOGGER_PREFIX;
		if (loggerName.startsWith(prefix)) {
			return loggerName.substring(prefix.length(), loggerName.length() - 1);
		} else {
			return "unknown_task";
		}
	}

	@Override
	public void start() {
		started = true;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
