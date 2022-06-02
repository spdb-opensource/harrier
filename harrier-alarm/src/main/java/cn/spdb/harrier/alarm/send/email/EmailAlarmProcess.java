package cn.spdb.harrier.alarm.send.email;

import java.util.Map;

import cn.spdb.harrier.alarm.enums.SendStatus;
import cn.spdb.harrier.alarm.send.AlarmProcess;
import cn.spdb.harrier.alarm.send.modle.AlarmData;
import cn.spdb.harrier.alarm.send.modle.AlarmInfo;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;

public class EmailAlarmProcess implements AlarmProcess {

	@Override
	public AlarmResult process(AlarmInfo info) {
		AlarmData alert = info.getAlarmData();
		Map<String, String> paramsMap = info.getAlertParams();
		if (null == paramsMap) {
			return new AlarmResult(SendStatus.FAIL.name(), "mail params is null");
		}
		return new EmailSender(paramsMap).sendMails(alert.getTitle(), alert.getContent());
	}

}
