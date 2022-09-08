package cn.spdb.harrier.server.master.rpc.transport;

import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;

@RpcHandler("AlarmTransportServerInterfasce")
public interface AlarmTransportServerInterfasce {

	@RpcMethod
	Boolean createAlarm(MAlarm alarm);

	@RpcMethod
	Boolean create(MAlarmSendQueue alarmSendQueue);

}