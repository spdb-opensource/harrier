package cn.spdb.harrier.alarm.enums;

import org.apache.commons.lang3.ObjectUtils;

import cn.spdb.harrier.alarm.send.AlarmProcess;
import cn.spdb.harrier.alarm.send.email.EmailAlarmProcess;
import cn.spdb.harrier.alarm.send.http.HttpAlarmProcess;
import cn.spdb.harrier.alarm.send.modle.AlarmInfo;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;
import cn.spdb.harrier.alarm.send.wechat.WechatAlarmProcess;

public enum SendType {
	EMAIL(new EmailAlarmProcess()), HTTP(new HttpAlarmProcess()), WECHAT(new WechatAlarmProcess());

	private AlarmProcess process;

	SendType(AlarmProcess process) {
		this.process = process;
	}

	public static AlarmResult send(AlarmInfo info) {
		SendType sendType = SendType.valueOf(info.getSendType());
		if (ObjectUtils.isEmpty(sendType)) {
			return null;
		}
		return sendType.getProcess().process(info);
	}

	public static void sendAlarm(AlarmInfo info, String... types) {
		for (String type : types) {
			SendType sendType = SendType.valueOf(type);
			if (ObjectUtils.isEmpty(sendType)) {
				continue;
			}
			sendType.getProcess().process(info);
		}
	}

	public AlarmProcess getProcess() {
		return process;
	}

	public void setProcess(AlarmProcess process) {
		this.process = process;
	}

}
