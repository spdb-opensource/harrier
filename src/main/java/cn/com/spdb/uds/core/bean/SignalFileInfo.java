package cn.com.spdb.uds.core.bean;

import java.io.File;

import cn.com.spdb.uds.utils.Symbol;

public class SignalFileInfo {

	// 信号文件
	private File file;
	// 作业名
	private String jobSource;
	// 信号文件日期
	private String jobDate;
	// 批次号
	private int batch;

	/***
	 * 符合后创建
	 * 
	 * @param file
	 * @return
	 */
	public static SignalFileInfo bulidSignalFileBean(File file) {
		String fileName = file.getName();
		String[] tmp = fileName.split(Symbol.AT);
		String jobSource = tmp[1].substring(0, tmp[1].length() - 8);
		String job_date = tmp[1].substring(tmp[1].length() - 8);
		String[] batchStr = tmp[0].split(Symbol.DIAN);
		int batch = Integer.valueOf(batchStr[1]);
		SignalFileInfo bean = new SignalFileInfo();
		bean.setFile(file);
		bean.setJobDate(job_date);
		bean.setJobSource(jobSource);
		bean.setBatch(batch);
		return bean;
	}

	public SignalFileInfo() {
	}

	public SignalFileInfo(String jobSource, String jobDate, int batch) {
		this.jobSource = jobSource;
		this.jobDate = jobDate;
		this.batch = batch;
	}

	public String getJobSource() {
		return jobSource;
	}

	public void setJobSource(String jobSource) {
		this.jobSource = jobSource;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String job_date_str) {
		this.jobDate = job_date_str;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
