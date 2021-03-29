package cn.com.spdb.uds.db.bean;

public class UdsSystemBean {
	// 平台
	private String platform;
	// 系统
	private String system;

	// private short data_keep_day = 0;
	// private short log_keep_day = 0;
	// private short record_keep_day = 0;
	private int max_run_job = 0;
	private short strategy = 0;
	private String strategy_pro = "";
	private byte use_platform = 1;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platfrom) {
		this.platform = platfrom;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public int getMax_run_job() {
		return max_run_job;
	}

	public void setMax_run_job(int max_run_job) {
		this.max_run_job = max_run_job;
	}

	public short getStrategy() {
		return strategy;
	}

	public void setStrategy(short strategy) {
		this.strategy = strategy;
	}

	public String getStrategy_pro() {
		return strategy_pro;
	}

	public void setStrategy_pro(String strategy_pro) {
		this.strategy_pro = strategy_pro;
	}

	public byte getUse_platform() {
		return use_platform;
	}

	public void setUse_platform(byte use_platform) {
		this.use_platform = use_platform;
	}

	@Override
	public String toString() {
		return "UdsSystemBean [platform=" + platform + ", system=" + system + ", max_run_job=" + max_run_job + ", strategy=" + strategy + ", strategy_pro=" + strategy_pro
				+ ", use_platform=" + use_platform + "]";
	}

}
