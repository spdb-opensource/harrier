package cn.com.spdb.uds.core.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.com.spdb.uds.core.bean.JobType;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

public class GetNextTimeByJobType {
		public void getnexttime(UdsJobControlDao controlDao,UdsJobBean udsJobBean,JobType jobType,Date tDate,Date old_Date,Date new_Date) {
			switch (jobType) {
			case D: {
				// 每日作业是否相隔一天，下次作业执行日期
				tDate = DateUtils.add(old_Date, 1);
			}
				break;
			case M: {
				// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
						udsJobBean.getSystem(), udsJobBean.getJob());
				for (UdsJobDateFrequencyBean bean : list) {
					String day = bean.getDay().trim();
					String month = bean.getMonth().trim();
					String cron = " 0 0 0 " + day + " " + month + " ? *";
					try {
						Date tmpDate = DateUtils.getNextValidTimeAfter(cron, old_Date);
						if (tDate == null || tmpDate.compareTo(tDate) < 0) {
							tDate = tmpDate;
						}
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job UdsJobDateFrequencyBean is error", e.getMessage());
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
			}
				break;
			case W: {
				// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
						udsJobBean.getSystem(), udsJobBean.getJob());
				for (UdsJobDateFrequencyBean bean : list) {
					String week = bean.getWeek().trim();
					String cron = "0 0 0 ? * " + week + " *";
					try {
						Date tmpDate = DateUtils.getNextValidTimeAfter(cron, old_Date);
						if (tDate == null || tmpDate.compareTo(tDate) < 0) {
							tDate = tmpDate;
						}
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job UdsJobDateFrequencyBean is error", e.getMessage());
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
			}
				break;
			case Y: {
				// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
						udsJobBean.getSystem(), udsJobBean.getJob());
				for (UdsJobDateFrequencyBean bean : list) {
					String day = bean.getDay().trim();
					String month = bean.getMonth().trim();
					String year = bean.getYear().trim();
					String cron = " 0 0 0 " + day + " " + month + " ? " + year;
					try {
						Date tmpDate = DateUtils.getNextValidTimeAfter(cron, old_Date);
						if (tDate == null || tmpDate.compareTo(tDate) < 0) {
							tDate = tmpDate;
						}
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job UdsJobDateFrequencyBean is error", e.getMessage());
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
			}
				break;
			case S: {// 特殊作业不做时间间隔检测
				tDate = new_Date;
			}
				break;
			default:
				break;
			}

		}
}
