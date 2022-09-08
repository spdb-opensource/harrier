package cn.spdb.harrier.alarm.utils;

import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.MAlarm;

public class AlarmUtils {

	public static MAlarm build(String code, String platfrom, String systems, String job, Object... Objects) {
		return build(code, platfrom, systems, job, null, null, Objects);
	}

	public static MAlarm build(String code, String platfrom, String systems, String job, String title, String content,
			Object... objects) {
		MAlarm mAlarm = new MAlarm();
		mAlarm.setPlatform(platfrom);
		mAlarm.setSystems(systems);
		mAlarm.setCode(code);
		mAlarm.setJob(job);
		mAlarm.setTitle(title);
		mAlarm.setSrcContent(content);
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
