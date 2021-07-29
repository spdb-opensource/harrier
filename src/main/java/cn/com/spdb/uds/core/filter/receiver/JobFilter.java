package cn.com.spdb.uds.core.filter.receiver;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.UdsUtils;

public class JobFilter extends AbstractReceiverFilter {

	@Override
	public boolean check(SignalFileInfo signalFileInfo, UdsJobBean udsJobBean) {
		if (UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(), udsJobBean.getSystem()) == null) {
			// 应用未注册
			UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, udsJobBean.getJob(),
					"UdsSystemBean is null");
			return false;
		}

		//信号文件单批多批触发检测
		if (signalFileInfo.getBatch() == 0) {
			if (udsJobBean.getBatch() != 0) {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_BACTH_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
				return false;
			}
		} else {
			if (udsJobBean.getBatch() == 0) {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_BACTH_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
				return false;
			}
		}
		
		// 执行的信号文件日期不对
		if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) < 0) {
			UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_DATE_ERROR, UdsErrorLevel.H, udsJobBean.getJob(),
					signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
			return false;
		}

		// 批次号检测
		if (udsJobBean.getBatch() > 0) {
			int nextBatch = udsJobBean.getBatch() + 1;
			if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) == 0) {
				if (signalFileInfo.getBatch() < nextBatch) {
					UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_BACTH_ERROR, UdsErrorLevel.H,
							udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
					return false;
				}
				if (signalFileInfo.getBatch() > nextBatch) {
					if (UdsConstant.RCV_SIGN_FAIL_MOVE_STREAM == 1&&udsJobBean.getCheck_file_stream()==UdsConstant.TRUE_NUM) {
						UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
						if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
								signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
							// 生成信号文件失败
							UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
									signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
						}
					} else {
						UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
					}
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_BACTH_ERROR, UdsErrorLevel.H,
							udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
					return false;
				}
			}
			if (signalFileInfo.getJobDate().compareTo(udsJobBean.getJob_date()) > 0) {
				if (signalFileInfo.getBatch() != 1) {
					if (UdsConstant.RCV_SIGN_FAIL_MOVE_STREAM == 1&&udsJobBean.getCheck_file_stream()==UdsConstant.TRUE_NUM) {
						UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
						if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
								signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
							// 生成信号文件失败
							UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
									signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
						}
						//FIXME 这里不使用源信号文件名，使用作业名，减少数据库操作 

					} else {
						UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_BACTH_ERROR, UdsErrorLevel.H,
								udsJobBean.getJob(), signalFileInfo.getBatch(), udsJobBean.getBatch());
					}
					return false;
				}
			}
		}

		// 作业状态是否为DONE和READY
		if (!(udsJobBean.getLast_status().equals(JobStatus.DONE.status()) || udsJobBean.getLast_status().equals(
				JobStatus.READY.status()))) {
			if (UdsConstant.RCV_SIGN_FAIL_MOVE_STREAM == 1&&udsJobBean.getCheck_file_stream()==UdsConstant.TRUE_NUM) {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
				if (!UdsUtils.createSignFile(udsJobBean.getJob(), signalFileInfo.getBatch(),
						signalFileInfo.getJobDate(), UdsConstant.AUTO_STREAM_DIR_PATH)) {
					// 生成信号文件失败
					UdsLogger.logEvent(LogEvent.MASTER_JOB_STREAM_CREATE_SIGN_FILE_FAIL, udsJobBean.getJob(),
							signalFileInfo.getJobDate(), udsJobBean.getJob_date(), udsJobBean.getJob_type());
				}
				//FIXME 这里不使用源信号文件名，使用作业名，减少数据库操作 

			} else {
				UdsUtils.moveToDayFile(UdsConstant.AUTO_FAIL_DIR_PATH, signalFileInfo.getFile());
			}
			if (udsJobBean.getBatch() == 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_RCV_STATUS_ERROR, UdsErrorLevel.H,
						udsJobBean.getJob(), signalFileInfo.getJobDate(), udsJobBean.getLast_status(),
						udsJobBean.getBatch());
			}

			return false;
		}

		return true;
	}

}
