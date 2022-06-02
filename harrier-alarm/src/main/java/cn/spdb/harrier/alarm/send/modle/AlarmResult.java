
package cn.spdb.harrier.alarm.send.modle;

public class AlarmResult {

	private String status;

	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message == null ? "" : message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlarmResult(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public AlarmResult() {
	}
}
