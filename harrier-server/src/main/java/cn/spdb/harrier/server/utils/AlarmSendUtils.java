package cn.spdb.harrier.server.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.enmus.Message;
import cn.spdb.harrier.common.enmus.alarm.AlarmCode;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.master.rpc.transport.AlarmTransportServerInterfasce;
import cn.spdb.harrier.service.db.DbRegistryService;

public class AlarmSendUtils {

	public static void sendAlarm(AlarmCode code, String platfrom, String systems, String job, Object... Objects) {
		sendAlarm(build(code.name(), platfrom, systems, null, null, null, null, job, Objects));
	}

	public static void sendAlarm(AlarmCode code, String platfrom, String systems, String job, String title, String content,
			String alarmLevel, String alarmType, Object... objects) {
		sendAlarm(build(code.name(), platfrom, systems, job, title, content, alarmLevel, alarmType, objects));
	}

	public static void sendAlarm(MAlarm alarm) {
		DbRegistryService dbRegistryService = BeanContext.getBean(DbRegistryService.class);
		if (ObjectUtils.isEmpty(dbRegistryService)) {
			return;
		}
		UdsServer udsServer = dbRegistryService.getAlarm();
		if (ObjectUtils.isEmpty(udsServer)) {
			return;
		}
		try {
			AlarmTransportServerInterfasce serverInterfasce = RpcClient.getInstance().create(
					AlarmTransportServerInterfasce.class, new URI("spdb", udsServer.getIp(), udsServer.getPort()));
			serverInterfasce.createAlarm(alarm);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static MAlarm buildWarn(String code, String platfrom, String systems, String job, String content,
			Object... Objects) {
		return build(AlarmCode.WARN.name(), Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO, null, content, null,
				null, Objects);
	}

	public static MAlarm buildInfo(String platfrom, String systems, String job, String content,
			Object... Objects) {
		return build(AlarmCode.INFO.name(), Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO, null, content, null,
				null, Objects);
	}
	public static MAlarm buildDbError(JobExecutionContext jobContext, Message message,
			Object... Objects) {
		return build(AlarmCode.DB_ERROR.name(), jobContext.getPlatform(), jobContext.getSystem(), jobContext.getJob(), null, message.getMsg(), null,
				null, Objects);
	}
	
	public static MAlarm buildJobError(JobExecutionContext jobContext, Message message,
			Object... Objects) {
		return build(AlarmCode.JOB_ERROR.name(), jobContext.getPlatform(), jobContext.getSystem(), jobContext.getJob(), null, message.getMsg(), null,
				null, Objects);
	}
	
	public static MAlarm buildJobWarn(JobExecutionContext jobContext, Message message,
			Object... Objects) {
		return build(AlarmCode.JOB_WARN.name(), jobContext.getPlatform(), jobContext.getSystem(), jobContext.getJob(), null, message.getMsg(), null,
				null, Objects);
	}

	public static MAlarm build(String code, String platfrom, String systems, String job, String title, String content,
			String alarmLevel, String alarmType, Object... objects) {
		MAlarm mAlarm = new MAlarm();
		mAlarm.setPlatform(platfrom);
		mAlarm.setSystems(systems);
		mAlarm.setCode(code);
		mAlarm.setJob(job);
		mAlarm.setTitle(title);
		mAlarm.setSrcContent(content);
		mAlarm.setAlarmType(alarmType);
		mAlarm.setAlarmLevel(alarmLevel);
		if (ObjectUtils.isNotEmpty(objects)) {
			StringBuffer buffer = new StringBuffer();
			for (Object object : objects) {
				buffer.append(JSON.toJSONString(object)).append(Symbol.DOU_HAO);
			}
			mAlarm.setSrcParam(buffer.toString());
		}
		return mAlarm;
	}
}
