package cn.spdb.harrier.alarm.send.wechat;

import java.util.Map;

import cn.spdb.harrier.alarm.send.AlarmProcess;
import cn.spdb.harrier.alarm.send.modle.AlarmData;
import cn.spdb.harrier.alarm.send.modle.AlarmInfo;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;
import cn.spdb.harrier.common.enmus.alarm.SendStatus;

public class WechatAlarmProcess implements AlarmProcess {

	@Override
	public AlarmResult process(AlarmInfo info) {
		AlarmData alertData = info.getAlarmData();
		Map<String, String> paramsMap = info.getAlertParams();
		if (null == paramsMap) {
			return new AlarmResult(SendStatus.FAIL.name(), "we chat params is null");
		}
		return new WeChatSender(paramsMap).sendEnterpriseWeChat(alertData.getTitle(), alertData.getContent());

	}

}
