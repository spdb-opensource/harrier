package cn.com.spdb.uds.db.bean;

public class UdsJobTagBean {

	private long id;
	// 平台
	private String platform;
	// 系统
	private String system;
	// 作业
	private String job;
	// 作业
	private String tag;
	// 作业
	private int tag_type;
	// 排序
	private int sort;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getTag_type() {
		return tag_type;
	}

	public void setTag_type(int tag_type) {
		this.tag_type = tag_type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
