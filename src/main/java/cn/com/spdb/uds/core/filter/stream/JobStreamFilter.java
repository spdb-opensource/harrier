package cn.com.spdb.uds.core.filter.stream;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.UdsUtils;

public class JobStreamFilter extends AbstractStreamFilter {

	@Override
	public boolean check(SignalFileInfo signalFileInfo, UdsJobBean udsJobBean) {

		// 应用未注册报错
		if (UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(), udsJobBean.getSystem()) == null) {
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
					"UdsSystemBean is null");
			return false;
		}

		// 信号文件单批多批触发检测
		if (signalFileInfo.getBatch() == 0) {
			if (udsJobBean.getBatch() != 0) {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_BACTH_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
				return false;
			}
		} else {
			if (udsJobBean.getBatch() == 0) {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_BACTH_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
				return false;
			}
		}

		// 信号文件时间小于作业执行时间日志记录
		if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) < 0) {
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_DATE_ERROR, udsJobBean.getJob(), signalFileInfo.getJobDate(),
					udsJobBean.getJob_date(), udsJobBean.getJob_type());
			return false;
		}

		// 批次号检测
		if (udsJobBean.getBatch() > 0) {
			int nextBatch = udsJobBean.getBatch() + 1;
			if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) == 0) {
				if (signalFileInfo.getBatch() < nextBatch) {
					UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_BATCH_ERROR, UdsErrorLevel.H, udsJobBean.getJob(),
							signalFileInfo.getJobDate(), udsJobBean.getBatch());
					return false;
				}
				if (signalFileInfo.getBatch() > nextBatch) {
					if (UdsConstant.STREAM_FAIL_CREATE_SIGN_STREAM == 1
							&& udsJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
						if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
								signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
							// 生成信号文件失败
							UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
									signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
						}
					} else {
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_BACTH_ERROR, UdsErrorLevel.H,
								udsJobBean.getJob(), signalFileInfo.getJobDate(), udsJobBean.getBatch());
					}
					return false;
				}
			}
			if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) > 0) {
				if (signalFileInfo.getBatch() != 1) {
					if (UdsConstant.STREAM_FAIL_CREATE_SIGN_STREAM == 1
							&& udsJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
						if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
								signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
							// 生成信号文件失败
							UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
									signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
						}
					} else {
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_BACTH_ERROR, UdsErrorLevel.H,
								udsJobBean.getJob(), signalFileInfo.getJobDate(), udsJobBean.getBatch());
					}
					return false;
				}
			}
		}

		// 作业状态是否为DONE和READY
		if (!(udsJobBean.getLast_status().equals(JobStatus.DONE.status()) || udsJobBean.getLast_status().equals(
				JobStatus.READY.status()))) {
			// 触发作业的作业状态不正确
			if (UdsConstant.STREAM_FAIL_CREATE_SIGN_STREAM == 1
					&& udsJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
				// 转换生成信号文件
				if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
						signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
					// 生成信号文件失败
					UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
							signalFileInfo.getBatch(), signalFileInfo.getJobDate());
				}
			}
			// 告警
			if (udsJobBean.getBatch() == 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_STREAM_STATUS_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getJobDate(), udsJobBean.getLast_status());
			}
			// 记录
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_JOB_SATUS_ERROR, UdsErrorLevel.H, udsJobBean.getJob(),
					signalFileInfo.getJobDate(), udsJobBean.getLast_status());
			return false;
		}

		return true;
	}

}
