
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
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.utils.ResourceDownUtils;
import cn.spdb.harrier.server.worker.task.TaskManager;

public class TaskExecuteRunnable extends ExecuteRunable {

	@Override
	public void run() {
		try {
			jobExecutionContext.initGlobal();
			jobExecutionContext.setStartTime(new Date());
			if (jobExecutionContext.getExecutionStatus() != ExecutionStatus.STEP_RUNNING) {
				jobExecutionContext.setExecutionStatus(ExecutionStatus.STEP_RUNNING);
			}
			logger.info("the task begins to execute. task instance id: {}", jobExecutionContext.getTaskInstanceId());
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
			workerManagerThread.updatejobDone(jobExecutionContext);
			workerManagerThread.decrementJob(jobExecutionContext);
		}
	}

}
