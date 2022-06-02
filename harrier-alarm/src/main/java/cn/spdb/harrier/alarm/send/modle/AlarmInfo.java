
package cn.spdb.harrier.alarm.send.modle;

import java.util.Map;

/**
 * AlertInfo
 */
public class AlarmInfo {

	private long sendId;
	
	private String sendType;
	/**
	 * all params this plugin need is in alertProps
	 */
	private Map<String, String> alertParams;

	/**
	 * the alert content
	 */
	private AlarmData alarmData;

	public Map<String, String> getAlertParams() {
		return alertParams;
	}

	public void setAlertParams(Map<String, String> alertParams) {
		this.alertParams = alertParams;
	}

	public AlarmData getAlarmData() {
		return alarmData;
	}

	public void setAlarmData(AlarmData alertData) {
		this.alarmData = alertData;
	}

	public long getSendId() {
		return sendId;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	@Override
	public String toString() {
		return "AlarmInfo [sendId=" + sendId + ", sendType=" + sendType + ", alertParams=" + alertParams
				+ ", alarmData=" + alarmData + "]";
	}

	
}
