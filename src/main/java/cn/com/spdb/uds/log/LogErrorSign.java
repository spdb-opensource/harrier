package cn.com.spdb.uds.log;

// #{code},#{msg_level},now(),#{msg_par},#{msg} 
public class LogErrorSign {
	private int code;
	private byte msg_level;
	private String msg_par;
	private String msg;
	private long updateTime;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public LogErrorSign(int code, byte msg_level, String msg_par, String msg) {
		super();
		this.code = code;
		this.msg_level = msg_level;
		this.msg_par = msg_par;
		this.msg = msg;
		this.updateTime=System.currentTimeMillis();
	}

	public long getNow() {
		return updateTime;
	}

	public void setUpdateTime(long now) {
		this.updateTime = now;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((msg_par == null) ? 0 : msg_par.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (null==obj)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogErrorSign other = (LogErrorSign) obj;
		if (code != other.code)
			return false;
		if (msg_par == null) {
			if (other.msg_par != null)
				return false;
		} else if (!msg_par.equals(other.msg_par))
			return false;
		return true;
	}

	
	
}
