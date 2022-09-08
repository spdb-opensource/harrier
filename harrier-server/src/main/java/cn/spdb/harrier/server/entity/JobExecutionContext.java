
package cn.spdb.harrier.server.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import cn.spdb.harrier.common.enmus.ExecutionStatus;
import cn.spdb.harrier.common.enmus.UdsJobType;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.Host;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobConfig;

/**
 * master/worker task transport
 */
public class JobExecutionContext implements Serializable {

	private static final long serialVersionUID = 2L;

	private Long taskInstanceId;

	private List<JobStepBean> stepList;

	private Host host;

	private ExecutionStatus executionStatus;

	private HashMap<Integer, Integer> weightMap = new HashMap<Integer, Integer>();

	private UdsJobConfig udsJobConfig;

	private UdsJob udsJob;

	private HashMap<String, String> gloParams;

	private HashMap<String, String> gloEnvs;

	private List<String> cornList;

	private List<String> timeCornList;

	private Long complementId = 0L;

	private Boolean usePlatform = false;

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getPlatform() {
		return udsJob.getPlatform();
	}

	public String getSystem() {
		return udsJob.getSystems();
	}

	public String getJob() {
		return udsJob.getJob();
	}

	public List<JobStepBean> getStepList() {
		return stepList;
	}

	public void setStepList(List<JobStepBean> stepList) {
		this.stepList = stepList;
	}

	public ExecutionStatus getExecutionStatus() {
		if (ObjectUtils.isEmpty(executionStatus)) {
			executionStatus = ExecutionStatus.valueOf(udsJob.getLastStatus());
		}
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus currentExecutionStatus) {
		this.executionStatus = currentExecutionStatus;
		this.udsJob.setLastStatus(currentExecutionStatus.name());
	}

	public List<JobStepBean> getJobStepListSort() {
		Collections.sort(stepList, new Comparator<JobStepBean>() {
			@Override
			public int compare(JobStepBean o1, JobStepBean o2) {
				return o1.getStepNum() - o2.getStepNum();
			}
		});
		return stepList;
	}

	public HashMap<Integer, Integer> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(HashMap<Integer, Integer> weightMap) {
		this.weightMap = weightMap;
	}

	public UdsJobConfig getUdsJobConfig() {
		return udsJobConfig;
	}

	public void setUdsJobConfig(UdsJobConfig udsJobConfig) {
		this.udsJobConfig = udsJobConfig;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public UdsJob getUdsJob() {
		return udsJob;
	}

	public void setUdsJob(UdsJob udsJob) {
		this.udsJob = udsJob;
	}

	public void setStartTime(Date date) {
		udsJob.setStartTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	public void setEndTime(Date date) {
		udsJob.setEndTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	public HashMap<String, String> getGloParams() {
		return gloParams;
	}

	public void setGloParams(HashMap<String, String> gloParams) {
		this.gloParams = gloParams;
	}

	public HashMap<String, String> getGloEnvs() {
		return gloEnvs;
	}

	public void setGloEnvs(HashMap<String, String> gloenv) {
		this.gloEnvs = gloenv;
	}

	public void setBatch(Integer batch) {
		this.udsJob.setMultiBatch(batch);
	}

	public void setJobDate(String jobDate) {
		this.udsJob.setJobDate(LocalDate.parse(jobDate, DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYYMMDD_CONS)));
	}

	public String getJobDate() {
		return udsJob.getJobDate().format(DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYYMMDD_CONS));
	}

	public void setNextJobDate(String jobDate) {
		this.udsJob
				.setNextJobDate(LocalDate.parse(jobDate, DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYYMMDD_CONS)));
	}

	public String getNextJobDate() {
		return udsJob.getNextJobDate().format(DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYYMMDD_CONS));
	}

	public List<String> getCornList() {
		return cornList;
	}

	public void setCornList(List<String> cornList) {
		this.cornList = cornList;
	}

	public List<String> getTimeCornList() {
		return timeCornList;
	}

	public void setTimeCornList(List<String> timeCornList) {
		this.timeCornList = timeCornList;
	}

	public UdsJobType getJobType() {
		return UdsJobType.valueOf(udsJobConfig.getJobType());
	}

	public int getPriority() {
		return udsJobConfig.getPriority();
	}

	public LocalDateTime getPendingTime() {
		return udsJob.getPendingTime();
	}

	public LocalDateTime getDispatcherTime() {
		return udsJob.getDispatcherTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
		result = prime * result + ((getPlatform() == null) ? 0 : getPlatform().hashCode());
		result = prime * result + ((getSystem() == null) ? 0 : getSystem().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobExecutionContext other = (JobExecutionContext) obj;
		if (getJob() == null) {
			if (other.getJob() != null)
				return false;
		} else if (!getJob().equals(other.getJob()))
			return false;
		if (getPlatform() == null) {
			if (other.getPlatform() != null)
				return false;
		} else if (!getPlatform().equals(other.getPlatform()))
			return false;
		if (getSystem() == null) {
			if (other.getSystem() != null)
				return false;
		} else if (!getSystem().equals(other.getSystem()))
			return false;
		return true;
	}

	public void initGlobal() {
		putGlobalParam("${job}", getJob());
		putGlobalParam("${platform}", getPlatform());
		putGlobalParam("${systems}", getSystem());
		putGlobalParam("${date}", getJobDate());
		putGlobalParam("${batche}", udsJob.getMultiBatch().toString());
	}

	public void putGlobalParam(String key, String value) {
		if (CollectionUtils.isEmpty(gloParams)) {
			gloParams = new HashMap<String, String>();
		}
		gloParams.put(key, value);
	}

	public void putGlobalEnv(String key, String value) {
		if (CollectionUtils.isEmpty(gloEnvs)) {
			gloEnvs = new HashMap<String, String>();
		}
		gloEnvs.put(key, value);
	}

	@Override
	public String toString() {
		return "JobExecutionContext [udsJob=" + udsJob + "]";
	}

	public String getJobNameOrJob() {
		String name = udsJobConfig.getJobName();
		name = StringUtils.isBlank(name) ? getJobId() : name;
		return name;
	}

	public String getJobId() {
		return udsJobConfig.getPlatform() + Symbol.XIA_HUA_XIAN + udsJobConfig.getSystems() + Symbol.XIA_HUA_XIAN
				+ udsJobConfig.getJob();
	}

	public Boolean getComplement() {
		return complementId > 0;
	}

	public Long getComplementId() {
		return complementId;
	}

	public void setComplementId(Long complementId) {
		this.complementId = complementId;
	}

	public Boolean getUsePlatform() {
		return usePlatform;
	}

	public void setUsePlatform(Boolean usePlatform) {
		this.usePlatform = usePlatform;
	}

}
