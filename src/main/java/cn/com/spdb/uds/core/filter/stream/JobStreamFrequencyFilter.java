package cn.com.spdb.uds.core.filter.stream;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.JobType;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.UdsUtils;

public class JobStreamFrequencyFilter extends AbstractStreamFilter {

	@Override
	public boolean check(SignalFileInfo signalFileInfo, UdsJobBean udsJobBean) {
		JobType jobType = JobType.getJoyType(udsJobBean.getJob_type());
		// 作业类型不符合报错
		if (jobType == null) {
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
					"jobType is null");
			return false;
		}
		// 作业是否开启了时间间隔检测
		if (udsJobBean.getCheck_frequency() == UdsConstant.FALSE_NUM) {
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM, "uds job not check frequency", udsJobBean.getJob(),
					udsJobBean.getCheck_frequency());
			return true;
		}
		UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		String new_job_date = signalFileInfo.getJobDate();
		String old_job_date = udsJobBean.getJob_date();
		// 上次执行信号文件时间
		Date old_Date = DateUtils.parserDate(old_job_date, DateUtils.PATTERN_YYYYMMDD_CONS);
		// 本次需要执行信号文件时间
		Date new_Date = DateUtils.parserDate(new_job_date, DateUtils.PATTERN_YYYYMMDD_CONS);
		// 作业下次执行的真实时间
		Date tDate = null;
		// 批次号
		int batch = udsJobBean.getBatch();
		// 作业是否执行标志
		boolean flag = false;
		switch (jobType) {
		case D: {
			// 每日作业是否相隔一天，下次作业执行日期
			tDate = DateUtils.add(old_Date, 1);
		}
			break;
		case M: {
			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
					udsJobBean.getSystem(), udsJobBean.getJob());
			// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
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
			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
					udsJobBean.getSystem(), udsJobBean.getJob());
			// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
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
			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(udsJobBean.getPlatform(),
					udsJobBean.getSystem(), udsJobBean.getJob());
			// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
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
		// 作业没配置时间间隔
		if (tDate == null) {
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(), jobType,
					"no db JobDateFrequency");
			return false;
		}
		// 时间是否符合
		flag = DateUtils.isSameDay(tDate, new_Date);
		// 多批次可以当前再次执行
		if (batch > 0 && !flag) {
			flag = DateUtils.isSameDay(old_Date, new_Date);
		}
		// 记录是否是READY特殊状态
		if (!flag && udsJobBean.getLast_status().equals(JobStatus.READY.status())) {
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_READY, udsJobBean.getJob(), signalFileInfo.getJobDate(),
					udsJobBean.getJob_date(), DateUtils.getDateTime(tDate, DateUtils.PATTERN_YYYYMMDD_CONS),
					udsJobBean.getLast_status());
			return true;
		}
		// 作业时间间隔不符合报错
		if (!flag) {
			// 信号文件时间大于下次执行作业时间，说明中间有有一次没有执行
			if (new_Date.compareTo(tDate) > 0) {
				// 生成信号文件等待处理
				if (UdsConstant.STREAM_FAIL_CREATE_SIGN_STREAM == 1
						&& udsJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
					if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
							signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
						// 生成信号文件失败
						UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
								signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
					}
				} else {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_FREQUENCY_ERROR, UdsErrorLevel.H,
							udsJobBean.getJob(), signalFileInfo.getJobDate(),
							DateUtils.getDateTime(tDate, DateUtils.PATTERN_YYYYMMDD_CONS), udsJobBean.getBatch());
				}
			}
			// 记录不符合触发频率的作业
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_FREQUENCY_ERROR, udsJobBean.getJob(),
					signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());

		}
		return flag;
	}

}
