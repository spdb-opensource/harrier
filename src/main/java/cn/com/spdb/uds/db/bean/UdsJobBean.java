package cn.com.spdb.uds.db.bean;

import java.sql.Timestamp;
import java.util.HashMap;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;

public class UdsJobBean {

	private String platform;
	private String system;
	private String job;
	private String server_name;
	private String job_type;
	private String last_status;
	private String job_date;

	private String last_script_name;
	private String timewindow;

	private byte virtual_enable;
	private byte check_frequency;
	private byte check_time_trigger;
	private int batch;

	private short priority;

	private long num_times;

	private Timestamp pending_time;
	private Timestamp dispatcher_time;
	private Timestamp start_time;
	private Timestamp end_time;

	private byte call_again_max_num;
	private byte call_again_num;
	private int location;

	private byte check_file_stream;
	private byte ignore_error;

	private byte check_weight;

	private HashMap<Integer, Integer> weightConfMap;

	private String platfromSytemKey;

	public String getPlatfromSytemKey() {
		if (platfromSytemKey != null) {
			return platfromSytemKey;
		} else {
			UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(platform, system);
			if (udsSystemBean == null) {
				return null;
			}
			String key = UdsConstant.getUdsSystemBeanKey(udsSystemBean.getPlatform(), udsSystemBean.getSystem());
			if (StringUtils.isBlank(key)) {
				return null;
			}
			return platfromSytemKey = key;
		}
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

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

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public String getLast_status() {
		return last_status;
	}

	public void setLast_status(String last_status) {
		this.last_status = last_status;
	}

	public String getJob_date() {
		return job_date;
	}

	public void setJob_date(String job_date) {
		this.job_date = job_date;
	}

	public String getLast_script_name() {
		return last_script_name;
	}

	public void setLast_script_name(String last_script_name) {
		this.last_script_name = last_script_name;
	}

	public String getTimewindow() {
		return timewindow;
	}

	public void setTimewindow(String timewindow) {
		this.timewindow = timewindow;
	}

	public byte getCheck_frequency() {
		return check_frequency;
	}

	public void setCheck_frequency(byte check_frequency) {
		this.check_frequency = check_frequency;
	}

	public byte getCheck_time_trigger() {
		return check_time_trigger;
	}

	public void setCheck_time_trigger(byte check_time_trigger) {
		this.check_time_trigger = check_time_trigger;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public long getNum_times() {
		return num_times;
	}

	public void setNum_times(long num_times) {
		this.num_times = num_times;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getPending_time() {
		return pending_time;
	}

	public void setPending_time(Timestamp pending_time) {
		this.pending_time = pending_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public byte getVirtual_enable() {
		return virtual_enable;
	}

	public void setVirtual_enable(byte virtual_enable) {
		this.virtual_enable = virtual_enable;
	}

	@Override
	public String toString() {
		return "UdsJobBean [" + job + "," + job_date + "," + batch + "]";
	}

	public byte getCall_again_max_num() {
		return call_again_max_num;
	}

	public void setCall_again_max_num(byte call_again_max_num) {
		this.call_again_max_num = call_again_max_num;
	}

	public byte getCall_again_num() {
		return call_again_num;
	}

	public void setCall_again_num(byte call_again_num) {
		this.call_again_num = call_again_num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
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
		UdsJobBean other = (UdsJobBean) obj;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		return true;
	}

	public byte getCheck_file_stream() {
		return check_file_stream;
	}

	public void setCheck_file_stream(byte check_file_stream) {
		this.check_file_stream = check_file_stream;
	}

	public byte getIgnore_error() {
		return ignore_error;
	}

	public void setIgnore_error(byte ignore_error) {
		this.ignore_error = ignore_error;
	}

	public Timestamp getDispatcher_time() {
		return dispatcher_time;
	}

	public void setDispatcher_time(Timestamp dispatcher_time) {
		this.dispatcher_time = dispatcher_time;
	}

	public byte getCheck_weight() {
		return check_weight;
	}

	public void setCheck_weight(byte check_weight) {
		this.check_weight = check_weight;
	}

	public HashMap<Integer, Integer> getWeightConfMap() {
		return weightConfMap;
	}

	public void setWeightConfMap(HashMap<Integer, Integer> weightConfMap) {
		this.weightConfMap = weightConfMap;
	}

}
