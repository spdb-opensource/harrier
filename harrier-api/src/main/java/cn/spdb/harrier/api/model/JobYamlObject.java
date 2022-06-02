package cn.spdb.harrier.api.model;

import java.util.List;

/**
 * yaml对象
 * @author guon2
 *
 */
public class JobYamlObject {
	
	
	/**
	 * 作业版本信息
	 */
	private Integer version;
	
	private String platform;
	
	private String systems;
	
	private String job;
	
	private String jobName;
	
	private String jobType;
	
	private String jobDate;
	
	private String lastStatus;
	
	private int taskStatus;
	
	private int multiBatch;
	
	private String timeWindow;
	
	private Boolean virtualEnable;
	
	private int priority;
	
	private Byte callAgainMaxNum;
	
	private Byte callAgainWaitSec;
	
	private boolean checkFrequency;
	
	private boolean checkTimeTrigger;
	
	private boolean checkStreamSelf;
	
	private Byte streamType;
	
	/**
	 * 列表形式存储作业间的依赖
	 */
	private List<JobDepRelations> depJob;
	
	/**
	 * 列表形式存储作业脚本信息
	 */
	private List<JobStepConfig> jobStep;
	
	private List<String> jobFrequency;
	
	private Byte offsetDay;
	
	private boolean ignoreError;
	
	private String des;
	
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSystems() {
		return systems;
	}
	public void setSystems(String systems) {
		this.systems = systems;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobDate() {
		return jobDate;
	}
	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public int getMultiBatch() {
		return multiBatch;
	}
	public void setMultiBatch(int multiBatch) {
		this.multiBatch = multiBatch;
	}
	public String getTimeWindow() {
		return timeWindow;
	}
	public void setTimeWindow(String timeWindow) {
		this.timeWindow = timeWindow;
	}
	public Boolean getVirtualEnable() {
		return virtualEnable;
	}
	public void setVirtualEnable(Boolean virtualEnable) {
		this.virtualEnable = virtualEnable;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Byte getCallAgainMaxNum() {
		return callAgainMaxNum;
	}
	public void setCallAgainMaxNum(Byte callAgainMaxNum) {
		this.callAgainMaxNum = callAgainMaxNum;
	}
	public Byte getCallAgainWaitSec() {
		return callAgainWaitSec;
	}
	public void setCallAgainWaitSec(Byte callAgainWaitSec) {
		this.callAgainWaitSec = callAgainWaitSec;
	}
	public boolean getCheckFrequency() {
		return checkFrequency;
	}
	public void setCheckFrequency(boolean checkFrequency) {
		this.checkFrequency = checkFrequency;
	}
	public boolean getCheckTimeTrigger() {
		return checkTimeTrigger;
	}
	public void setCheckTimeTrigger(boolean checkTimeTrigger) {
		this.checkTimeTrigger = checkTimeTrigger;
	}
	public boolean getCheckStreamSelf() {
		return checkStreamSelf;
	}
	public void setCheckStreamSelf(boolean checkStreamSelf) {
		this.checkStreamSelf = checkStreamSelf;
	}
	public Byte getStreamType() {
		return streamType;
	}
	public void setStreamType(Byte streamType) {
		this.streamType = streamType;
	}
	public List<JobDepRelations> getDepJob() {
		return depJob;
	}
	public void setDepJob(List<JobDepRelations> list) {
		this.depJob = list;
	}
	public List<JobStepConfig> getJobStep() {
		return jobStep;
	}
	public void setJobStep(List<JobStepConfig> jobStep) {
		this.jobStep = jobStep;
	}
	public List<String> getJobFrequency() {
		return jobFrequency;
	}
	public void setJobFrequency(List<String> jobFrequency) {
		this.jobFrequency = jobFrequency;
	}
	public Byte getOffsetDay() {
		return offsetDay;
	}
	public void setOffsetDay(Byte offsetDay) {
		this.offsetDay = offsetDay;
	}
	public boolean isIgnoreError() {
		return ignoreError;
	}
	public void setIgnoreError(boolean ignoreError) {
		this.ignoreError = ignoreError;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

	
}
