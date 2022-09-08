package cn.spdb.harrier.alarm.send;

import cn.spdb.harrier.alarm.send.modle.AlarmInfo;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;

public interface AlarmProcess {

	  AlarmResult process(AlarmInfo info);
	  
	  
}
