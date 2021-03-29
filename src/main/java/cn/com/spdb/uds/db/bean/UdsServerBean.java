package cn.com.spdb.uds.db.bean;

import java.sql.Timestamp;

public class UdsServerBean {

	private String server_name;

	private short order = 10;

	private String ip = "127.0.0.1";

	private int port = 9696;

	private int agent_port = 6969;

	private int http_port = 7878;

	private short max_job_num = 30;

	private byte server_type = 0;

	private short performance_ratio = 1000;

	private Timestamp last_start;

	private Timestamp last_end;

	private int location;

	private byte is_enable;

	private String tags;

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getAgent_port() {
		return agent_port;
	}

	public void setAgent_port(int agent_port) {
		this.agent_port = agent_port;
	}

	public short getMax_job_num() {
		return max_job_num;
	}

	public void setMax_job_num(short max_job_num) {
		this.max_job_num = max_job_num;
	}

	public byte getServer_type() {
		return server_type;
	}

	public void setServer_type(byte server_type) {
		this.server_type = server_type;
	}

	public short getPerformance_ratio() {
		return performance_ratio;
	}

	public void setPerformance_ratio(short performance_ratio) {
		this.performance_ratio = performance_ratio;
	}

	public Timestamp getLast_star() {
		return last_start;
	}

	public void setLast_star(Timestamp last_star) {
		this.last_start = last_star;
	}

	public Timestamp getLast_end() {
		return last_end;
	}

	public void setLast_end(Timestamp last_end) {
		this.last_end = last_end;
	}

	public short getOrder() {
		return order;
	}

	public void setOrder(short order) {
		this.order = order;
	}

	public Timestamp getLast_start() {
		return last_start;
	}

	public void setLast_start(Timestamp last_start) {
		this.last_start = last_start;
	}

	public byte getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(byte is_enable) {
		this.is_enable = is_enable;
	}

	public int getHttp_port() {
		return http_port;
	}

	public void setHttp_port(int http_port) {
		this.http_port = http_port;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
