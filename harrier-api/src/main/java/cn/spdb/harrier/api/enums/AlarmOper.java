package cn.spdb.harrier.api.enums;

import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;

import cn.spdb.harrier.api.service.alarm.MAlarmService;
import cn.spdb.harrier.dao.utils.BeanContext;

public enum AlarmOper implements Function<Long, Boolean> {

	SET_VIRTUAL_JUMP("置虚跳过", 2, alramId -> BeanContext.getBean(MAlarmService.class).setVirtuaJump(alramId)),
	RECOVER("恢复告警", 4, alramId -> BeanContext.getBean(MAlarmService.class).alarmRecover(alramId)),
	DEALING("暂停告警", 3, alramId -> BeanContext.getBean(MAlarmService.class).alarmDealing(alramId)),
	AGAIN("再次执行", 5, alramId -> BeanContext.getBean(MAlarmService.class).invokeJob(alramId)),
	PROCESSED("已处理", 1, alramId -> BeanContext.getBean(MAlarmService.class).alarmProcessed(alramId)),;

	private String desc;
	private int oreder;
	private Function<Long, Boolean> function;

	private AlarmOper(String desc, int oreder, Function<Long, Boolean> function) {
		this.desc = desc;
		this.oreder = oreder;
		this.function = function;

	}

	private AlarmOper(String desc, int oreder) {
		this.desc = desc;
		this.oreder = oreder;
	}

	private AlarmOper(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOreder() {
		return oreder;
	}

	public void setOreder(int oreder) {
		this.oreder = oreder;
	}

	@Override
	public Boolean apply(Long t) {
		return ObjectUtils.isEmpty(function) ? false : function.apply(t);
	}

}
