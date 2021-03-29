package cn.com.spdb.uds.core.filter.pending;

import java.util.Calendar;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;

public class JobTimeWindowFilter extends AbstractPendingFilter {

	@Override
	public boolean checkDependency(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean) {
		String timeWindow = udsJobBean.getTimewindow();
		if (StringUtils.isBlank(timeWindow)) {
			// 配置错误
			UdsLogger.logEvent(LogEvent.ERROR, "uds job timeWindow   error ", timeWindow);
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob());
			return false;
		}
		String[] times = timeWindow.split(Symbol.HENG_GANG);
		if (times.length < 2) {
			// 配置错误
			UdsLogger.logEvent(LogEvent.ERROR, "uds job timeWindow   error ", timeWindow);
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob());
			return false;
		}
		String[] startStr = times[0].split(Symbol.MAO_HAO);
		String[] endStr = times[1].split(Symbol.MAO_HAO);
		if (startStr.length < 2 || endStr.length < 2) {
			// 配置错误
			UdsLogger.logEvent(LogEvent.ERROR, "uds job timeWindow   error ", timeWindow);
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob());
			return false;
		}
		int startHour = Integer.parseInt(startStr[0].trim());
		int startMinute = Integer.parseInt(startStr[1].trim());
		int endHour = Integer.parseInt(endStr[0].trim());
		int endMinute = Integer.parseInt(endStr[1].trim());
		int hour = DateUtils.get(Calendar.HOUR_OF_DAY);
		int minute = DateUtils.get(Calendar.MINUTE);
		int start = startHour * 100 + startMinute;
		int end = endHour * 100 + endMinute;
		int now = hour * 100 + minute;
		if (start > now) {
			// 不再执行范围
			return false;
		}
		if (now > end) {
			// 预警
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DELAY, UdsErrorLevel.M, udsJobBean.getJob(), timeWindow);
		}
		return true;
	}

}
