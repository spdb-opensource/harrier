package cn.com.spdb.uds.db.bean;

import java.sql.Timestamp;

public class UdsJobDateTriggerBean {
	private long id;
	private String platform;
	private String system;
	private String job;
	private Timestamp start_time;
	private Timestamp end_time;

	private String second;
	private String minute;
	private String hour;
	private String day;
	private String month;
	private String week;
	private String year;

	private String job_date;
	private String last_status;
	private String job_type;
	private int batch;

	private byte offset_day=0;
	private byte check_file_stream;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getJob_date() {
		return job_date;
	}

	public void setJob_date(String job_date) {
		this.job_date = job_date;
	}

	public String getLast_status() {
		return last_status;
	}

	public void setLast_status(String last_status) {
		this.last_status = last_status;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public byte getOffset_day() {
		return offset_day;
	}

	public void setOffset_day(byte offset_day) {
		this.offset_day = offset_day;
	}

	public byte getCheck_file_stream() {
		return check_file_stream;
	}

	public void setCheck_file_stream(byte check_file_stream) {
		this.check_file_stream = check_file_stream;
	}
	
}
