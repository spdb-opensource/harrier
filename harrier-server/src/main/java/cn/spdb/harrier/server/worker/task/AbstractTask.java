package cn.spdb.harrier.server.worker.task;

import static ch.qos.logback.classic.ClassicConstants.FINALIZE_SESSION_MARKER;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.StringJoiner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.uitls.LoggerUtils;
import cn.spdb.harrier.server.entity.JobStepBean;

public abstract class AbstractTask implements Comparable<JobStepBean> {

	private AtomicBoolean cancel = new AtomicBoolean(false);

	protected JobStepBean jobStepBean;

	protected Logger logger;

	protected AbstractTask(JobStepBean jobStepBean) {
		this.jobStepBean = jobStepBean;
		this.logger = LoggerFactory.getLogger(LoggerUtils.TASK_STEP_LOGGER_PREFIX + jobStepBean.getLogPath() + "]");
	}

	public void init() {
		logger.info("start jobstepbean:{}", jobStepBean);
	}

	public abstract void handle();

	public void after() {
		logger.info("end");
	}

	public void cancelApplication() {
		cancel.set(true);
	}

	protected void logHandle(LinkedBlockingQueue<String> logs) {
		if (logs.contains(FINALIZE_SESSION_MARKER.toString())) {
			logger.info(FINALIZE_SESSION_MARKER, FINALIZE_SESSION_MARKER.toString());
		} else {
			StringJoiner joiner = new StringJoiner("\n\t");
			while (!logs.isEmpty()) {
				joiner.add(logs.poll());
			}
			logger.info(" -> {}", joiner);
		}
	}

	public ExecutionStatus getExitStatus() {
		ExecutionStatus status;
		switch (getExitStatusCode()) {
		case Constants.EXIT_CODE_SUCCESS:
			status = ExecutionStatus.SUCCESS;
			break;
		case Constants.EXIT_CODE_KILL:
			status = ExecutionStatus.KILL;
			break;
		default:
			status = ExecutionStatus.FAILURE;
			break;
		}
		return status;
	}

	public int getExitStatusCode() {
		return jobStepBean.getExitId();
	}

	protected void setExitStatusCode(int exitStatusCode) {
		jobStepBean.setExitId(exitStatusCode);
	}

	@Override
	public int compareTo(JobStepBean other) {
		return other.getStepNum() - jobStepBean.getStepNum();
	}

	protected long getRemaintime() {
		Duration duration = Duration.between(jobStepBean.getStartTime(), LocalDateTime.now());
		long usedTime = duration.getSeconds();
		long remainTime = jobStepBean.getTimeOut() - usedTime;
		if (remainTime < 0) {
			throw new RuntimeException("task execution time out");
		}
		return remainTime;
	}

	public Logger getLogger() {
		return logger;
	}

}
