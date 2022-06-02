package cn.spdb.harrier.api.model;

public class JobDepRelations {
	/**
	 * 依赖的平台名
	 */
	private String depPlatform;
	
	/**
	 * 依赖的应用名
	 */
	private String depSystem;
	
	/**
	 * 依赖的作业名
	 */
	private String depJob;
	
	/**
	 * 依赖作业的批次
	 */
	private Integer depBatch;

	public String getDepPlatform() {
		return depPlatform;
	}

	public void setDepPlatform(String depPlatform) {
		this.depPlatform = depPlatform;
	}

	public String getDepSystem() {
		return depSystem;
	}

	public void setDepSystem(String depSystem) {
		this.depSystem = depSystem;
	}

	public String getDepJob() {
		return depJob;
	}

	public void setDepJob(String depJob) {
		this.depJob = depJob;
	}

	public Integer getDepBatch() {
		return depBatch;
	}

	public void setDepBatch(Integer depBatch) {
		this.depBatch = depBatch;
	}

	@Override
	public String toString() {
		return depPlatform + "@" + depSystem + "@" + depJob + "@" + depBatch;
	}
	
	
}
