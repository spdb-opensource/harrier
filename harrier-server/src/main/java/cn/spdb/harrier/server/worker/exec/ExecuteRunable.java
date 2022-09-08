package cn.spdb.harrier.server.worker.exec;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.worker.WorkerManagerThread;
import cn.spdb.harrier.server.worker.task.AbstractTask;

public abstract class ExecuteRunable implements Runnable, Delayed {

	protected static WorkerManagerThread workerManagerThread = BeanContext.getBean(WorkerManagerThread.class);
	protected AtomicBoolean cancel = new AtomicBoolean(false);
	protected final Logger logger = LoggerFactory.getLogger(TaskExecuteRunnable.class);

	protected JobExecutionContext jobExecutionContext;

	protected volatile AbstractTask nowTask;

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return 0;
	}

	public JobExecutionContext getJobExecutionContext() {
		return jobExecutionContext;
	}

	public void setJobExecutionContext(JobExecutionContext jobExecutionContext) {
		this.jobExecutionContext = jobExecutionContext;
	}

	public UdsJobStepRecord buildUdsJobStepRecord(Long jobId, UdsJob udsjob, JobStepBean stepBean) {
		UdsJobStepRecord jobStepRecord = new UdsJobStepRecord();
		jobStepRecord.setJobRecordId(jobId);
		jobStepRecord.setPlatform(udsjob.getPlatform());
		jobStepRecord.setSystems(udsjob.getSystems());
		jobStepRecord.setJob(udsjob.getJob());
		jobStepRecord.setNumTimes(udsjob.getNumTimes());
		jobStepRecord.setStepNum(stepBean.getStepNum());
		jobStepRecord.setStartTime(stepBean.getStartTime());
		jobStepRecord.setEnvironments(stepBean.getEnvs());
		jobStepRecord.setCmd(stepBean.getCmd());
		jobStepRecord.setScriptPath(stepBean.getStepPath());
		jobStepRecord.setParameter(stepBean.getPararmeter());
		jobStepRecord.setLogPath(stepBean.getLogPath());
		return jobStepRecord;
	}

	/**
	 * kill task
	 */
	public void kill() {
		cancel.set(true);
		if (nowTask != null) {
			try {
				nowTask.kill();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	

}
