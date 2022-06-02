package cn.spdb.harrier.common.model;

import java.util.HashMap;

public class JobSignal {

	private String signal;
	
	private Integer batch;
	
	private String jobDate;
	
	private HashMap<String, String> params;
	
	private HashMap<String, String> envs;

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public HashMap<String, String> getEnvs() {
		return envs;
	}

	public void setEnvs(HashMap<String, String> env) {
		this.envs = env;
	}

	@Override
	public String toString() {
		return "JobSignal [signal=" + signal + ", batch=" + batch + ", jobDate=" + jobDate + ", params=" + params
				+ ", envs=" + envs + "]";
	}
}
