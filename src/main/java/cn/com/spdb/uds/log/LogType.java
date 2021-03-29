package cn.com.spdb.uds.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LogType {
	MASTER_LOG(LoggerFactory.getLogger("masterLog")),
	CHILD_LOG(LoggerFactory.getLogger("childLog")),
	ERROR_LOG(LoggerFactory.getLogger("errorLog")),
	BACK_GROUND_LOG(LoggerFactory.getLogger("backGroundLog")),
	;
	private Logger logger;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	private LogType(Logger logger) {
		this.logger = logger;
	}
	
	
}
