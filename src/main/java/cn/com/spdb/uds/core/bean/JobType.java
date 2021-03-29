package cn.com.spdb.uds.core.bean;

public enum JobType {
	D("D", "日作业"), W("W", "周作业"), M("M", "月作业"), Y("Y", "年作业"), C("C", "定时作业"), S("S", "特殊作业"), ;

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private JobType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String type() {
		return id;
	}

	public static JobType getJoyType(String type){
		for(JobType jobType:JobType.values()){
			if(jobType.getId().equals(type)){
				return jobType;
			}
		}
		return null;
	}
	
}
