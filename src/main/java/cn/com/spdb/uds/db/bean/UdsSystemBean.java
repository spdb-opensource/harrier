package cn.com.spdb.uds.db.bean;

import cn.com.spdb.uds.utils.Symbol;

public class UdsSystemBean {
	// 平台
	private String platform;
	// 系统
	private String system;
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

	public String getPlatformAndSystemKey() {
		return platform + Symbol.XIA_HUA_XIAN + system;
	}

	@Override
	public String toString() {
		return "UdsSystemBean [platform=" + platform + ", system=" + system + ", max_run_job=" + max_run_job
				+ ", strategy=" + strategy + ", strategy_pro=" + strategy_pro + ", use_platform=" + use_platform + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
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
		UdsSystemBean other = (UdsSystemBean) obj;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		return true;
	}

}
