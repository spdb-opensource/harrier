
package cn.spdb.harrier.server.worker.exec;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.uitls.DateUtils;
import cn.spdb.harrier.common.uitls.FileUtils;
import cn.spdb.harrier.common.uitls.HttpClientUtils;
import cn.spdb.harrier.common.uitls.IPUtils;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.rpc.transport.File.FileManager;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.worker.WorkerManagerThread;
import cn.spdb.harrier.server.worker.task.AbstractTask;
import cn.spdb.harrier.server.worker.task.TaskManager;
import cn.spdb.harrier.service.aws.AwsUtils;

public class TaskExecuteThread implements Runnable, Delayed {
	private final Logger logger = LoggerFactory.getLogger(TaskExecuteThread.class);

	private JobExecutionContext jobExecutionContext;

	private volatile AbstractTask nowTask;

	private WorkerManagerThread workerManagerThread;

	@Override
	public void run() {
		jobExecutionContext.initGlobal();
		jobExecutionContext.setStartTime(new Date());
		if (jobExecutionContext.getExecutionStatus() != ExecutionStatus.STEP_RUNNING) {
			jobExecutionContext.setExecutionStatus(ExecutionStatus.STEP_RUNNING);
		}
		logger.info("the task begins to execute. task instance id: {}", jobExecutionContext.getTaskInstanceId());
		try {
			for (JobStepBean stepBean : jobExecutionContext.getJobStepListSort()) {
				stepBean.resetEnvs(jobExecutionContext.getGloEnvs());
				stepBean.resetPararmeter(jobExecutionContext.getGloParams());
				stepBean.setStartTime(LocalDateTime.now());
				UdsJobStepRecord record = buildUdsJobStepRecord(jobExecutionContext.getTaskInstanceId(),
						jobExecutionContext.getUdsJob(), stepBean);
				record.setId(workerManagerThread.getSingleId());
				String log = DateUtils.format(new Date(), DateUtils.PATTERN_YYYYMMDD_CONS) + Symbol.XIE_XIAN
						+ record.getPlatform() + Symbol.XIE_XIAN + record.getSystems() + Symbol.XIE_XIAN
						+ record.getJob() + Symbol.XIE_XIAN + record.getNumTimes() + Symbol.XIA_HUA_XIAN
						+ record.getStepNum() + Symbol.XIA_HUA_XIAN + record.getId() + ".log";
				stepBean.setLogPath(log);
				record.setLogPath(log);
				workerManagerThread.insertUdsJobStepRecord(record);
				try {
					nowTask = TaskManager.newTask(stepBean);
					if (!downloadResource(stepBean)) {
						nowTask.getLogger().error("download resource is error", stepBean.getStepUri().toString());
					}
					nowTask.init();
					nowTask.handle();
					nowTask.after();
					if (nowTask.getExitStatus() != ExecutionStatus.SUCCESS) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					record.setEndTime(LocalDateTime.now());
					record.setReturnCode(stepBean.getExitId());
					workerManagerThread.updateJobStepRecord(record);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			kill();
		} finally {
			jobExecutionContext.setExecutionStatus(nowTask.getExitStatus());
			jobExecutionContext.setEndTime(new Date());
			workerManagerThread.updatejobDone(jobExecutionContext);
		}
	}

	private UdsJobStepRecord buildUdsJobStepRecord(Long jobId, UdsJob udsjob, JobStepBean stepBean) {
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
		if (nowTask != null) {
			try {
				nowTask.cancelApplication();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private Boolean downloadResource(JobStepBean step) {
		try {
			if (step.getStepUri().getProtocol().equals("local") || ObjectUtils.isEmpty(step.getStepUri())
					|| StringUtils.isBlank(step.getStepPath())) {
				return true;
			}
			File file = new File(step.getStepPath());
			if (file.exists()) {
				long ms = file.lastModified();
				LocalDateTime fixLd = DateUtils.date2LocalDateTime(new Date(ms));
				LocalDateTime updateTime = step.getUpdateTime();
				if (updateTime.compareTo(fixLd) < 0) {
					return true;
				} else {
					File destFile = new File(file.getPath() + "_old");
					if (destFile.exists()) {
						destFile.delete();
					}
					FileUtils.copyFile(file, destFile);
					file.delete();
				}
			}
			return downloadResource(step.getStepUri());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * download resource file
	 *
	 * @param execLocalPath execLocalPath
	 * @param projectRes    projectRes
	 * @param logger        logger
	 */
	private Boolean downloadResource(URI stepURI) {
		try {
			if (ObjectUtils.isEmpty(stepURI)) {
				return false;
			}
			switch (stepURI.getProtocol()) {
			case "spdb":
				if (StringUtils.isEmpty(stepURI.getAddress())) {
					UdsServer server = workerManagerThread.getLeaderMaster();
					if (ObjectUtils.isEmpty(server)) {
						return false;
					}
					stepURI = stepURI.setHost(server.getIp());
					stepURI = stepURI.setPort(server.getPort());
				}
				if (IPUtils.getHostIp().equals(stepURI.getHost())) {
					return true;
				}
				return FileManager.getInstance().loadFile(stepURI, stepURI.getPath(), stepURI.getPath(), true);
			case "scp":
				break;
			case "http":
			case "https":
				return HttpClientUtils.downLoadFormUrl(stepURI.toString(), stepURI.getPath());
			case "aws":
				return AwsUtils.load(stepURI, stepURI.getPath());
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * get current TaskExecutionContext
	 * 
	 * @return TaskExecutionContext
	 */
	public JobExecutionContext getTaskExecutionContext() {
		return this.jobExecutionContext;
	}

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

	public WorkerManagerThread getWorkerManagerThread() {
		return workerManagerThread;
	}

	public void setWorkerManagerThread(WorkerManagerThread workerManagerThread) {
		this.workerManagerThread = workerManagerThread;
	}

}
