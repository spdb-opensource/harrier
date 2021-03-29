package cn.com.spdb.uds.db.bean;

import java.sql.Timestamp;

public class UdsErrorBean {
	private long id;
	private int code;
	private Timestamp msg_time;
	private byte msg_level;
	private String msg_par;
	private String msg;
	private byte dispose;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public byte getDispose() {
		return dispose;
	}
	public void setDispose(byte dispose) {
		this.dispose = dispose;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Timestamp getMsg_time() {
		return msg_time;
	}
	public void setMsg_time(Timestamp msg_time) {
		this.msg_time = msg_time;
	}
	public byte getMsg_level() {
		return msg_level;
	}
	public void setMsg_level(byte msg_level) {
		this.msg_level = msg_level;
	}
	public String getMsg_par() {
		return msg_par;
	}
	public void setMsg_par(String msg_par) {
		this.msg_par = msg_par;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
