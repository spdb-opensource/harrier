package cn.com.spdb.uds.core.filter.pending;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.JobType;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.bean.UdsJobDateTriggerBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

public class JobDependFilter extends AbstractPendingFilter {
	// 依赖检测
	public boolean checkDependency(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean) {
		String sdate = udsJobBean.getJob_date();
		Date s_Date = DateUtils.parserDate(sdate, DateUtils.PATTERN_YYYYMMDD_CONS);
		int sbatch = udsJobBean.getBatch();
		UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		List<UdsJobBean> jobNameList = controlDao.getUdsJobDependency(udsJobBean.getPlatform(), udsJobBean.getSystem(),
				udsJobBean.getJob());
		boolean flag = true;
		for (UdsJobBean bean : jobNameList) {
			// 依赖作业的作业日期
			Date tDate = DateUtils.parserDate(bean.getJob_date(), DateUtils.PATTERN_YYYYMMDD_CONS);
			// 依赖作业的作业日期大于需要运行的作业日期，说明依赖作业的满足数据日期条件
			if (tDate.compareTo(s_Date) > 0) {
				continue;
			}
			int tbatch = bean.getBatch();
			// 作业数据日期相同
			if (tDate.compareTo(s_Date) == 0) {
				// 依赖作业为单批，今日为Done即可
				if (tbatch == 0) {
					if (bean.getLast_status().equals(JobStatus.DONE.status())) {
						continue;
					} else {
						return false;
					}
				} else {
					// 多批次
					if (sbatch > 0) {
						// 依赖作业批次大于当前作业，说明依赖作业的满足多批次运行条件
						if (tbatch > sbatch) {
							continue;
						}
						// 批次相同
						if (tbatch == sbatch) {
							// 依赖作业为Done，说明依赖作业的满足多批次运行条件
							if (bean.getLast_status().equals(JobStatus.DONE.status())) {
								continue;
							} else {
								return false;
							}
						}
						if (tbatch < sbatch) {
							return false;
						}
					} else {
						// 获取依赖批次,作业为单批次，依赖作业为多批次
						int dep_batch = controlDao.getDepbatch(udsJobBean.getPlatform(), udsJobBean.getSystem(),
								udsJobBean.getJob(), bean.getPlatform(), bean.getSystem(), bean.getJob());
						dep_batch = dep_batch > 0 ? dep_batch : 0;
						if (tbatch > dep_batch) {
							continue;
						}
						if (tbatch == dep_batch && bean.getLast_status().equals(JobStatus.DONE.status())) {
							continue;
						}
						if (tbatch < dep_batch) {
							return false;
						}
					}
				}
			}

			// 作业为Done，获取下次运行时间，当前运行数据日期小于下次运行日期，依赖条件满足
			if (!bean.getLast_status().equals(JobStatus.DONE.status())) {
				return false;
			}
			// tDate.compareTo(s_Date)<0 计算下次运行日期，判断减1天是否大于等于 s_Date
			JobType jobType = JobType.getJoyType(bean.getJob_type());
			switch (jobType) {
			case M: {
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(bean.getPlatform(),
						bean.getSystem(), bean.getJob());
				Date minDate = null;
				// 获取下次执行的时间的最小值
				for (UdsJobDateFrequencyBean frequencyBean : list) {
					String day = frequencyBean.getDay();
					String month = frequencyBean.getMonth();
					String cron = " 0 0 0 " + day + " " + month + " ? *";
					try {
						tDate = DateUtils.getNextValidTimeAfter(cron, tDate);
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job JobDependFilter is error", e.getMessage());
						UdsLogger
								.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());

						e.printStackTrace();
					}
					if (minDate == null || tDate.compareTo(minDate) < 0) {
						minDate = tDate;
					}
				}
				if (minDate == null) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
							"UdsJobDateFrequencyBean is null");
					continue;
				}
				// 比较作业执行时间要小于下次执行作业时间
				flag = s_Date.compareTo(minDate) < 0;
			}
				break;
			case W: {
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(bean.getPlatform(),
						bean.getSystem(), bean.getJob());
				Date minDate = null;
				// 获取下次执行的时间的最小值
				for (UdsJobDateFrequencyBean frequencyBean : list) {
					String week = frequencyBean.getWeek();
					String cron = "0 0 0 ? * " + week + " *";
					try {
						tDate = DateUtils.getNextValidTimeAfter(cron, tDate);
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job JobDependFilter is error", e.getMessage());
						UdsLogger
								.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());

					}
					if (minDate == null || tDate.compareTo(minDate) < 0) {
						minDate = tDate;
					}
				}
				if (minDate == null) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
							"UdsJobDateFrequencyBean is null");
					continue;
				}
				// 比较作业执行时间要小于下次执行作业时间
				flag = s_Date.compareTo(minDate) < 0;
			}
				break;
			case Y: {
				List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(bean.getPlatform(),
						bean.getSystem(), bean.getJob());
				Date minDate = null;
				// 获取下次执行的时间的最小值
				for (UdsJobDateFrequencyBean frequencyBean : list) {
					String day = frequencyBean.getDay();
					String month = frequencyBean.getMonth();
					String year = frequencyBean.getYear();
					String cron = " 0 0 0 " + day + " " + month + " ? " + year;
					try {
						tDate = DateUtils.getNextValidTimeAfter(cron, tDate);
					} catch (ParseException e) {
						UdsLogger.logEvent(LogEvent.ERROR, "uds job JobDependFilter is error", e.getMessage());
						UdsLogger
								.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, bean.getJob());

						e.printStackTrace();
					}
					if (minDate == null || tDate.compareTo(minDate) < 0) {
						minDate = tDate;
					}
				}
				if (minDate == null) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
							"UdsJobDateFrequencyBean is null");
					continue;
				}
				// 比较作业执行时间要小于下次执行作业时间
				flag = s_Date.compareTo(minDate) < 0;
			}
				break;
			case C: {// 定时作业
				Date minDate = null;
				List<UdsJobDateTriggerBean> list = controlDao.getUdsJobDateTrigger(bean.getPlatform(), bean.getSystem(),
						bean.getJob());
				for (UdsJobDateTriggerBean dateTriggerBean : list) {
					String cron = " 0 0 0 " + dateTriggerBean.getDay() + " " + dateTriggerBean.getMonth()+ " "
							+ dateTriggerBean.getWeek() + " " + dateTriggerBean.getYear();
					try {
						tDate = DateUtils.getNextValidTimeAfter(cron, tDate);
						byte offsetDay = dateTriggerBean.getOffset_day();
						if (offsetDay != 0) {
							tDate = DateUtils.add(tDate, offsetDay);
						}
						if (minDate == null || tDate.compareTo(minDate) < 0) {
							minDate = tDate;
						}
					} catch (ParseException e) {
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
								"UdsJobDateTriggerBean is null");
						e.printStackTrace();
					}
				}
				// 比较作业执行时间要小于下次执行作业时间
				flag = s_Date.compareTo(minDate) < 0;
			}
				break;
			case D: {
				// 作业时间小于等于依赖作业本次的作业时间
				String tdate = bean.getJob_date();
				// 此处隐藏的minDate=tdate+1；既为s_Date.compareTo(minDate) < 0，转换为下面方式
				flag = sdate.compareTo(tdate) <= 0;
			}
				break;
			case S:
				break;
			default:
				flag = true;
			}
			if (!flag) {
				return flag;
			}
		}
		return flag;
	}
}
