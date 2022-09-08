package cn.spdb.harrier.server.worker.exec;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.PropertyUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.utils.ResourceDownUtils;
import cn.spdb.harrier.server.worker.task.TaskManager;

public class ComplementExecuteRunnable extends ExecuteRunable {

	private long ComplementId;

	private ComplementRunable complementRunable;

	public ComplementExecuteRunnable(long complementId, ComplementRunable complementRunable) {
		super();
		this.ComplementId = complementId;
		this.complementRunable = complementRunable;
	}

	@Override
	public void run() {
		try {
			workerManagerThread.getWorkingInfo().getJobNum().incrementAndGet();
			workerManagerThread.insertUdsJobRecord(jobExecutionContext);
			jobExecutionContext.initGlobal();
			jobExecutionContext.setStartTime(new Date());
			logger.info("the task begins to execute. task instance id: {}", jobExecutionContext.getTaskInstanceId());
			if (jobExecutionContext.getExecutionStatus() != ExecutionStatus.STEP_RUNNING) {
				jobExecutionContext.setExecutionStatus(ExecutionStatus.STEP_RUNNING);
			}
			for (JobStepBean stepBean : jobExecutionContext.getJobStepListSort()) {
				stepBean.resetEnvs(jobExecutionContext.getGloEnvs());
				stepBean.resetPararmeter(jobExecutionContext.getGloParams());
				stepBean.setStartTime(LocalDateTime.now());
				UdsJobStepRecord record = buildUdsJobStepRecord(jobExecutionContext.getTaskInstanceId(),
						jobExecutionContext.getUdsJob(), stepBean);
				record.setId(workerManagerThread.getSingleId());
				if(StringUtils.isBlank(stepBean.getLogPath()) ) {
					String log =workerManagerThread.getWorkerConfig().getLogStepPrex()+DateUtils.format(new Date(), DateUtils.PATTERN_YYYYMMDD_CONS) + Symbol.XIE_XIAN
							+ record.getPlatform() + Symbol.XIE_XIAN + record.getSystems() + Symbol.XIE_XIAN
							+ record.getJob() + Symbol.XIE_XIAN + record.getNumTimes() + Symbol.XIA_HUA_XIAN
							+ record.getStepNum() + Symbol.XIA_HUA_XIAN + record.getId() + ".log";
					stepBean.setLogPath(log);
				}
				record.setLogPath(stepBean.getLogUri().toString());
				workerManagerThread.insertUdsJobStepRecord(record);
				try {
					nowTask = TaskManager.newTask(stepBean);
					if (cancel.get()) {
						stepBean.setExitId(Constants.EXIT_CODE_KILL);
						nowTask.getLogger().error("job step is kill,end");
						break;
					}
					if (!ResourceDownUtils.downloadResource(stepBean)) {
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
					break;
				} finally {
					record.setEndTime(LocalDateTime.now());
					record.setReturnCode(stepBean.getExitId());
					workerManagerThread.updateJobStepRecord(record);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			jobExecutionContext.setExecutionStatus(
					ObjectUtils.isEmpty(nowTask) ? ExecutionStatus.FAILURE : nowTask.getExitStatus());
			jobExecutionContext.setEndTime(new Date());
			workerManagerThread.getWorkingInfo().getJobNum().decrementAndGet();
			workerManagerThread.updatejobDone(jobExecutionContext);
			complementRunable.offer();
		}
	}

	public long getComplementId() {
		return ComplementId;
	}

	public void setComplementId(long complementId) {
		ComplementId = complementId;
	}

}
