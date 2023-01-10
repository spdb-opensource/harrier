package cn.spdb.harrier.alarm.rpc.transport;

import cn.spdb.harrier.alarm.AlarmManger;
import cn.spdb.harrier.alarm.AlarmSendManger;
import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;

@RpcServiceHandler("AlarmTransportServerInterfasce")
public class AlarmTransportService implements AlarmTransportServerInterfasce {

	private static AlarmManger alarmManger = BeanContext.getBean(AlarmManger.class);
	private static AlarmSendManger alarmSendManger = BeanContext.getBean(AlarmSendManger.class);

	@Override
	public Boolean createAlarm(MAlarm alarm) {
		return alarmManger.addAlarm(alarm);
	}

	@Override
	public Boolean create(MAlarmSendQueue alarmSendQueue) {
		alarmSendManger.inserMalarmSend(alarmSendQueue);
		return true;
	}
}
