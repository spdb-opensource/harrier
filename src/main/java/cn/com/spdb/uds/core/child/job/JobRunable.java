package cn.com.spdb.uds.core.child.job;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.JobStepInfo;
import cn.com.spdb.uds.core.bean.JobType;
import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.filter.stream.AbstractStreamFilter;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.bean.UdsJobDateTriggerBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.db.dao.UdsJobRecordDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;
import cn.com.spdb.uds.utils.UdsIdBuild;
import cn.com.spdb.uds.utils.UdsUtils;

public class JobRunable implements Runnable {

	public static UdsIdBuild udsIdBuild = new UdsIdBuild(UdsConstant.ORDER);

	// 平台
	private String platform;
	// 系统
	private String system;
	// 作业
	private String job;
	// 作业类型
	private JobType jobType;
	// 批次号
	private int batch;
	// 作业日期
	private String jobDate;
	// 当前第几次执行该作业
	private long numTimes;
	// Start时间 是YYYYmmdd
	private Timestamp jobStart;
	// 最大重调次数
	private byte call_again_max_num;
	// 当前重调次数
	private byte call_again_num;
	// 是否启用文件触发
	private byte check_file_stream;
	// 是否检测
	private byte check_weight;
	// 是否忽视错误
	private byte ignore_error;
	// 当前步骤
	private byte setpNum;
	// 是否为虚作业
	private byte virtualEnable;
	// 返回码
	private int returnCode = 255;
	// 脚本执行器
	private Process processWork = null;
	// 脚本信息
	private List<JobStepInfo> jobStepList;
	// 线程名称
	private String name;
	// 是否出发下游
	private boolean notForcestartJob = true;
	/**
	 * 权重
	 */
	private HashMap<Integer, Integer> weightMap = new HashMap<Integer, Integer>();

	@Override
	public void run() {
		try {
			ChildManager.getInstance().addJobPool(job, this);
			setpNum = 0;
			for (JobStepInfo jobStepInfo : jobStepList) {
				setpNum++;
				returnCode = 255;
				doStep(jobStepInfo);
				if (returnCode != 0) {
					break;
				}
			}
			UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			UdsJobRecordDao udsJobRecordDao = DBManager.getInstance().getDao(UdsJobRecordDao.class);
			String last_script_name = "";
			String last_script_log_name = "";
			if (setpNum == 0) {
				udsJobBaseDao.updateJobFailed(platform, system, job, "null");
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job, "脚本没有配置");
				return;
			}
			JobStepInfo jobStepInfo = jobStepList.get(setpNum - 1);
			if (jobStepInfo != null) {
				last_script_name = jobStepInfo.getScript_name();
				last_script_log_name = jobStepInfo.getLogName();
			}
			// 作业错误处理
			if (returnCode != 0) {
				UdsUtils.jobErrorMoveToFile(job, jobDate, batch);
				udsJobBaseDao.updateJobFailed(platform, system, job, last_script_name);
				udsJobRecordDao.insertJobRecord(platform, system, job);
				// 失败忽视--并继续触发下游
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_SCRIPT, UdsErrorLevel.H, job, returnCode, batch,
						last_script_log_name);
				if (ignore_error == UdsConstant.TRUE_NUM) {
					udsJobBaseDao.updateJobStatus(platform, system, job, JobStatus.DONE.status());
					// 触发下游作业
					if (notForcestartJob) {
						// 检测文件是否触发
						if (UdsConstant.DEP_STREAM == UdsConstant.TRUE_NUM) {
							checkDepStreamJob();
						} else {
							checkStreamJob();
						}
					}
					checkStreamSelfJob();
				}
			} else {
				if (setpNum == jobStepList.size()) {
					// 作业完成
					udsJobBaseDao.updateJobDone(platform, system, job, last_script_name);
					// 记录
					udsJobRecordDao.insertJobRecord(platform, system, job);

					// 触发下游作业
					if (notForcestartJob) {
						// 检测文件是否触发
						if (UdsConstant.DEP_STREAM == UdsConstant.TRUE_NUM) {
							checkDepStreamJob();
						} else {
							checkStreamJob();
						}
					}
					checkStreamSelfJob();
				}
				// 判断是否发送完成通知
				if (UdsConstant.FINSH_NOTICE_JOB_MAP.containsKey(job)
						&& UdsConstant.FINSH_NOTICE_JOB_MAP.get(job).contains(batch)) {
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_FINSH_NOTICE, UdsErrorLevel.L, job,
							DateUtils.getDateTime(new Date(), DateUtils.PATTERN_NORMAL), batch);
				}
			}
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		} finally {
			ChildManager.getInstance().removeJobPool(job);
			if (check_weight == UdsConstant.TRUE_NUM) {
				ChildManager.getInstance().subWeightValueMap(weightMap);
			}
			UdsRpcEvent event = UdsRpcEvent.buildBroadcastEvent(RpcCommand.UPDATE_CHILD_INFO);
			event.addAttribute("platform", platform);
			event.addAttribute("system", system);
			event.addAttribute("job", job);
			event.addAttribute("check_weight", check_weight);
			UdsRpcClientManager.getInstance().sendBroadcastMessage(event, null, true);
		}
	}

	// 检测stream是否可以出发自身
	private void checkStreamSelfJob() {
		if (check_file_stream != UdsConstant.TRUE_NUM) {
			return;
		}
		if (UdsConstant.RCV_SIGN_FAIL_MOVE_STREAM == 0 && UdsConstant.STREAM_FAIL_CREATE_SIGN_STREAM == 0
				&& UdsConstant.SCHEDULER_FAIL_MOVE_STREAM == 0) {
			return;
		}
		Date old_Date = DateUtils.parserDate(jobDate, DateUtils.PATTERN_YYYYMMDD_CONS);
		Date tDate = null;
		UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		switch (jobType) {
		case M: {
			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(platform, system, job);
			if (list.size() <= 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
						"job end stream UdsJobDateFrequencyBean is null");
			}
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
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
							"job end stream UdsJobDateFrequencyBean is null");

					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}
			break;
		case W: {

			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(platform, system, job);
			if (list.size() <= 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
						"job end stream UdsJobDateFrequencyBean is null");
			}
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
					UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
							"job end stream UdsJobDateFrequencyBean is null");
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}
			break;
		case Y: {
			List<UdsJobDateFrequencyBean> list = controlDao.getUdsJobDateFrequency(platform, system, job);
			if (list.size() <= 0) {
				UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
						"job end stream UdsJobDateFrequencyBean is null");
			}
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
		case C: {// 定时作业
			List<UdsJobDateTriggerBean> list = controlDao.getUdsJobDateTrigger(platform, system, job);
			for (UdsJobDateTriggerBean bean : list) {
				String cron = " 0 0 0 " + bean.getDay() + " " + bean.getMonth() + " " + bean.getWeek() + " "
						+ bean.getYear();
				try {
					Date tmpDate = DateUtils.getNextValidTimeAfter(cron, old_Date);
					byte offsetDay = bean.getOffset_day();
					if (offsetDay != 0) {
						tmpDate = DateUtils.add(tmpDate, offsetDay);
					}
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
		case D:
		case S: {// 特殊作业不做时间间隔检测
			tDate = DateUtils.add(old_Date, 1);
		}
			break;
		default:
			break;
		}
		if (tDate == null) {
			UdsLogger.logEvent(LogEvent.ERROR, job, "uds job jobType is error");
			return;
		}
		String tjobDate = DateUtils.getDateTime(tDate, DateUtils.PATTERN_YYYYMMDD_CONS);
		StringBuffer fileBuffer = new StringBuffer();
		// FIXME 这里不使用源信号文件名，使用作业名，减少数据库操作
		// UdsJobBaseDao udsJobBaseDao =
		// DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// String signFileKey = udsJobBaseDao.getSignFileKeyByJob(job);
		// if (StringUtils.isEmpty(signFileKey)) {
		// return false;
		// }
		String signFileKey = job;

		if (batch == 0) {
			fileBuffer.append("dir.0@").append(signFileKey).append(tjobDate);
			if (UdsUtils.isExists(UdsConstant.AUTO_STREAM_DIR_PATH, fileBuffer.toString())) {
				UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
				int tmp = udsJobBaseDao.updateJobPending(platform, system, job, batch, tjobDate);
				if (tmp > 0) {
					// success
					UdsUtils.moveToDayFile(UdsConstant.AUTO_COMPLETE_DIR_PATH,
							UdsConstant.AUTO_STREAM_DIR_PATH + Symbol.XIE_XIAN + fileBuffer.toString());
					UdsLogger.logEvent(LogEvent.CHILD_FILE_STREAM_FAIL_JOB, job, "uds update job status is success");
				}
			}
		} else {
			int nextBatch = batch + 1;
			fileBuffer.append("dir.").append(nextBatch).append("@").append(signFileKey).append(jobDate);
			if (UdsUtils.isExists(UdsConstant.AUTO_STREAM_DIR_PATH, fileBuffer.toString())) {
				UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
				int tmp = udsJobBaseDao.updateJobPending(platform, system, job, nextBatch, jobDate);
				if (tmp > 0) {
					// success
					UdsUtils.moveToDayFile(UdsConstant.AUTO_COMPLETE_DIR_PATH,
							UdsConstant.AUTO_STREAM_DIR_PATH + Symbol.XIE_XIAN + fileBuffer.toString());
					UdsLogger.logEvent(LogEvent.CHILD_FILE_STREAM_FAIL_JOB, job, "uds update job status is success");
				}
			} else {
				nextBatch = 1;
				fileBuffer.setLength(0);
				fileBuffer.append("dir.").append(nextBatch).append("@").append(signFileKey).append(tjobDate);
				if (UdsUtils.isExists(UdsConstant.AUTO_STREAM_DIR_PATH, fileBuffer.toString())) {
					UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
					int tmp = udsJobBaseDao.updateJobPending(platform, system, job, nextBatch, tjobDate);
					if (tmp > 0) {
						// success
						if (UdsUtils.moveToDayFile(UdsConstant.AUTO_COMPLETE_DIR_PATH,
								UdsConstant.AUTO_STREAM_DIR_PATH + Symbol.XIE_XIAN + fileBuffer.toString())) {
							// 移动信号文件失败
							UdsLogger.logEvent(LogEvent.ERROR, "stream moveToDayFile error",
									UdsConstant.AUTO_STREAM_DIR_PATH + Symbol.XIE_XIAN + fileBuffer.toString());
						}
						UdsLogger.logEvent(LogEvent.CHILD_FILE_STREAM_FAIL_JOB, job,
								"uds update job status is success");
					}
				}
			}
		}
	}

	/**
	 * 触发下游作业
	 */
	private void checkStreamJob() {
		UdsJobControlDao udsJobControlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		// 触发下游作业
		List<UdsJobBean> beans = udsJobControlDao.getUdsJobStream(platform, system, job);
		// 可以触发的作业立即触发
		List<String> pass_list_singular = new ArrayList<String>();
		// 可以触发的作业立即触发
		List<String> pass_list_many = new ArrayList<String>();
		// 不可以触发的作业生成信号文件等待触发
		List<String> no_pass_list = new ArrayList<String>();
		for (UdsJobBean jobBean : beans) {
			boolean pass = true;
			SignalFileInfo signalFileInfo = null;
			if (batch > 0 && jobBean.getBatch() == 0) {
				int stm_batch = udsJobControlDao.getStmbatch(platform, system, job, jobBean.getPlatform(),
						jobBean.getSystem(), jobBean.getJob());
				if (stm_batch == batch) {
					signalFileInfo = new SignalFileInfo(jobBean.getJob(), jobDate, 0);
				} else {
					continue;
				}
			} else {
				signalFileInfo = new SignalFileInfo(jobBean.getJob(), jobDate, batch);
			}
			for (AbstractStreamFilter filter : ChildManager.getInstance().getJobStreamFilterList()) {
				if (!pass) {
					break;
				}
				pass = filter.check(signalFileInfo, jobBean);
			}
			if (pass) {
				if (jobBean.getBatch() == 0) {
					pass_list_singular.add(jobBean.getJob());
				} else {
					pass_list_many.add(jobBean.getJob());
				}
			} else {
				no_pass_list.add(jobBean.getJob());
			}
		}
		// 触发成功
		if (pass_list_many.size() > 0) {
			int tmp = udsJobBaseDao.updateJobTriggerByStatusPending(pass_list_many, batch, jobDate);
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM, job, "stream job many batch num: " + tmp, pass_list_many);
		}
		if (pass_list_singular.size() > 0) {
			int tmp = udsJobBaseDao.updateJobTriggerByStatusPending(pass_list_singular, 0, jobDate);
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM, job, "stream job singular batch num: " + tmp,
					pass_list_singular);
		}
		// 触发失败作业收集
		if (no_pass_list.size() > 0) {
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STREAM_FAIL_JOB, job, "stream error num: " + no_pass_list.size(),
					no_pass_list);
		}
	}

	//FIXME 还没有从UDS_SOURCE表中去除外部触发的作业。
	private void checkDepStreamJob() {
		UdsJobControlDao udsJobControlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		List<UdsJobBean> depingList = udsJobControlDao.getUdsJobByDependency(this.platform, this.system, this.job,
				this.batch);
		Date jobDate = DateUtils.parserDate(this.jobDate, DateUtils.PATTERN_YYYYMMDD_CONS);
		for (UdsJobBean depingJobBean : depingList) {
			Date depingJobDate = DateUtils.parserDate(depingJobBean.getJob_date(), DateUtils.PATTERN_YYYYMMDD_CONS);
			Date nextJobDate = null;
			Date next2JobDate = null;
			int nextBatch = 0;
			// 获取正确的下次执行时间
			if (jobDate.compareTo(depingJobDate) < 0) {
				continue;
			} else if (jobDate.compareTo(depingJobDate) == 0) {
				if (depingJobBean.getBatch() == 0) {
					continue;
				} else {
					nextBatch = depingJobBean.getBatch() + 1;
					if (this.batch > 0) {
						if (nextBatch == this.batch) {
							nextJobDate = jobDate;
						} else if (nextBatch > this.batch) {
							continue;
						} else {
							if (depingJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
								continue;
							} else {
								// 告警
							}
						}
					} else {
						continue;
					}
				}
			} else {
				if (depingJobBean.getBatch() == 0) {
					nextBatch = 0;
				} else {
					nextBatch = 1;
				}
				switch (JobType.getJoyType(depingJobBean.getJob_type())) {
				case D: {
					nextJobDate = DateUtils.add(depingJobDate, 1);
					next2JobDate = DateUtils.add(nextJobDate, 1);
				}
					break;
				case W:
				case M:
				case Y: {
					List<UdsJobDateFrequencyBean> list = udsJobControlDao.getUdsJobDateFrequency(
							depingJobBean.getPlatform(), depingJobBean.getSystem(), depingJobBean.getJob());
					if (list.size() <= 0) {
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
								"job end stream UdsJobDateFrequencyBean is null");
					}
					// 根据上次执行作业时间,计算下次执行作业的最近的 一次时间
					for (UdsJobDateFrequencyBean frequencyBean : list) {
						String day = frequencyBean.getDay().trim();
						String month = frequencyBean.getMonth().trim();
						String week = frequencyBean.getWeek().trim();
						String year = frequencyBean.getYear();
						String cron = null;
						if (depingJobBean.getJob_type().equals(jobType.W.getId())) {
							cron = " 0 0 0 ? " + month + " " + week + " " + year;
						} else {
							cron = " 0 0 0 " + day + " " + month + " ? " + year;
						}
						try {
							Date tmpDate = DateUtils.getNextValidTimeAfter(cron, depingJobDate);
							if (nextJobDate == null || tmpDate.compareTo(nextJobDate) < 0) {
								nextJobDate = tmpDate;
							}
						} catch (ParseException e) {
							UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
									"job end stream UdsJobDateFrequencyBean is null");
							UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
							continue;
						}
					}
					// 根据上次执行作业时间,计算下次执行作业2的最近的 一次时间
					for (UdsJobDateFrequencyBean frequencyBean : list) {
						String day = frequencyBean.getDay().trim();
						String month = frequencyBean.getMonth().trim();
						String week = frequencyBean.getWeek().trim();
						String year = frequencyBean.getYear();
						String cron = null;
						if (depingJobBean.getJob_type().equals(jobType.W.getId())) {
							cron = " 0 0 0 ? " + month + " " + week + " " + year;
						} else {
							cron = " 0 0 0 " + day + " " + month + " ? " + year;
						}
						try {
							Date tmpDate = DateUtils.getNextValidTimeAfter(cron, nextJobDate);
							if (next2JobDate == null || tmpDate.compareTo(next2JobDate) < 0) {
								next2JobDate = tmpDate;
							}
						} catch (ParseException e) {
							UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, job,
									"job end stream UdsJobDateFrequencyBean is null");
							UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
							continue;
						}
					}
				}
					break;
				default:
					continue;
				}

				if (jobDate.compareTo(next2JobDate) >= 0) {
					if (depingJobBean.getCheck_file_stream() == UdsConstant.TRUE_NUM) {
						continue;
					} else {
						// 告警
					}
				}
				if (jobDate.compareTo(nextJobDate) < 0) {
					continue;
				}
			}
			// 判断准备触发

			List<UdsJobBean> depList = udsJobControlDao.getUdsJobDependency(depingJobBean.getPlatform(),
					depingJobBean.getSystem(), depingJobBean.getJob());
			boolean isStream = true;
			for (UdsJobBean depJobBean : depList) {
				// 依赖作业的作业日期
				Date tDate = DateUtils.parserDate(depJobBean.getJob_date(), DateUtils.PATTERN_YYYYMMDD_CONS);
				int tbatch = depJobBean.getBatch();
				if (tDate.compareTo(nextJobDate) > 0) {
					continue;
				} else if (tDate.compareTo(nextJobDate) == 0) {
					if (tbatch == 0) {
						if (depJobBean.getLast_status().equals(JobStatus.DONE.status())) {
							continue;
						} else {
							isStream = false;
							break;
						}
					} else {
						// 多批次
						if (nextBatch > 0) {
							// 依赖作业批次大于当前作业，说明依赖作业的满足多批次运行条件
							if (tbatch > nextBatch) {
								continue;
							}
							// 批次相同
							if (tbatch == nextBatch) {
								// 依赖作业为Done，说明依赖作业的满足多批次运行条件
								if (depJobBean.getLast_status().equals(JobStatus.DONE.status())) {
									continue;
								} else {
									isStream = false;
									break;
								}
							}
							if (tbatch < nextBatch) {
								isStream = false;
								break;
							}
						} else {
							// 获取依赖批次,作业为单批次，依赖作业为多批次
							int dep_batch = udsJobControlDao.getDepbatch(depingJobBean.getPlatform(),
									depingJobBean.getSystem(), depingJobBean.getJob(), depJobBean.getPlatform(),
									depJobBean.getSystem(), depJobBean.getJob());
							dep_batch = dep_batch > 0 ? dep_batch : 0;
							if (tbatch > dep_batch) {
								continue;
							}
							if (tbatch == dep_batch && depJobBean.getLast_status().equals(JobStatus.DONE.status())) {
								continue;
							}
							if (tbatch < dep_batch) {
								isStream = false;
								break;
							}
						}
					}
				} else {
					// 作业为Done，获取下次运行时间，当前运行数据日期小于下次运行日期，依赖条件满足
					if (!depJobBean.getLast_status().equals(JobStatus.DONE.status())) {
						isStream = false;
						break;
					}
					JobType jobType = JobType.getJoyType(depJobBean.getJob_type());
					Date minDate = null;
					switch (jobType) {
					case W:
					case Y:
					case M: {
						List<UdsJobDateFrequencyBean> list = udsJobControlDao.getUdsJobDateFrequency(
								depJobBean.getPlatform(), depJobBean.getSystem(), depJobBean.getJob());
						// 获取下次执行的时间的最小值
						for (UdsJobDateFrequencyBean frequencyBean : list) {
							String day = frequencyBean.getDay().trim();
							String month = frequencyBean.getMonth().trim();
							String week = frequencyBean.getWeek().trim();
							String year = frequencyBean.getYear();
							String cron = null;
							if (depJobBean.getJob_type().equals(jobType.W.getId())) {
								cron = "0 0 0 ? " + month + " " + week + " " + year;
							} else {
								cron = "0 0 0 " + day + " " + month + " ? " + year;
							}
							try {
								Date tmpDate = DateUtils.getNextValidTimeAfter(cron, tDate);
								if (minDate == null || tmpDate.compareTo(minDate) < 0) {
									minDate = tmpDate;
								}
							} catch (ParseException e) {
								UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M,
										depJobBean.getJob());
								UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
							}
						}

					}
						break;
					case C: {// 定时作业
						List<UdsJobDateTriggerBean> list = udsJobControlDao.getUdsJobDateTrigger(
								depJobBean.getPlatform(), depJobBean.getSystem(), depJobBean.getJob());
						for (UdsJobDateTriggerBean dateTriggerBean : list) {
							String cron = " 0 0 0 " + dateTriggerBean.getDay() + " " + dateTriggerBean.getMonth() + " "
									+ dateTriggerBean.getWeek() + " " + dateTriggerBean.getYear();
							try {
								Date tmpDate = DateUtils.getNextValidTimeAfter(cron, tDate);
								byte offsetDay = dateTriggerBean.getOffset_day();
								if (offsetDay != 0) {
									tmpDate = DateUtils.add(tDate, offsetDay);
								}
								if (minDate == null || tmpDate.compareTo(minDate) < 0) {
									minDate = tmpDate;
								}
							} catch (ParseException e) {
								UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M,
										depJobBean.getJob(), "UdsJobDateTriggerBean is null");
								UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
							}
						}
					}
						break;
					case D: {
						minDate = DateUtils.add(tDate, 1);
					}
						break;
					default:
						break;
					}
					if (minDate == null) {
						UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, depJobBean.getJob(),
								"UdsJobDateFrequencyBean is null");
						continue;
					}
					// 比较作业执行时间要小于下次执行作业时间
					if (nextJobDate.compareTo(minDate) < 0) {
						continue;
					} else {
						isStream = false;
						break;
					}
				}
			}
			if (isStream) {
				// 触发
				udsJobBaseDao.updateJobTriggerByStatusPending(Arrays.asList(new String[] { depingJobBean.getJob() }),
						nextBatch, DateUtils.getDateTime(nextJobDate, DateUtils.PATTERN_YYYYMMDD_CONS));
			}
		}
	}

	private void doStep(JobStepInfo jobStepInfo) {
		UdsJobRecordDao udsJobRecordDao = DBManager.getInstance().getDao(UdsJobRecordDao.class);
		Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
		long stepId = udsIdBuild.getNextId();
		jobStepInfo.setStartTime(startTimestamp);
		if (virtualEnable == UdsConstant.TRUE_NUM) {
			returnCode = 0;
			jobStepInfo.setEndtTime(new Timestamp(System.currentTimeMillis()));
			jobStepInfo.setReturnCode(0);
			jobStepInfo.setLogName("");
			udsJobRecordDao.insertJobStepRecord(stepId, platform, system, job, numTimes, jobDate, jobStepInfo,
					virtualEnable);
			UdsLogger.logEvent(LogEvent.CHILD_JOB_STEP, "virtual job ", job);
			return;
		} else {
			ArrayList<String> cmd = new ArrayList<String>();
			cmd.addAll(jobStepInfo.getCmdList());
			// 载入命令
			ProcessBuilder processBuilder = new ProcessBuilder(cmd);
			// 配置环境变量
			Map<String, String> env = processBuilder.environment();
			env.put("AUTO_HOME", UdsConstant.AUTO_HOME);
			env.putAll(jobStepInfo.getEnvMap());
			// 设置工作路径
			String workDir = UdsConstant.SERVER_BIN_DIR_PATH;
			// 工作路径路劲
			if (!StringUtils.isBlank(jobStepInfo.getWorkDir())) {
				workDir = jobStepInfo.getWorkDir();
			}
			File workDirFile = new File(workDir);
			if (workDirFile.exists()) {
				processBuilder.directory(workDirFile);
			}
			// 设置错误日志和标准日志合并
			processBuilder.redirectErrorStream(true);
			// 输出日志文件夹
			File logDir = new File(jobStepInfo.getLogDir());
			if (!logDir.exists()) {
				logDir.mkdirs();
			}
			// 日志名字
			String logPath = jobStepInfo.getLogDir() + File.separator + jobStepInfo.getLogName();
			BufferedReader bufferedReader = null;
			BufferedWriter bufferedWriter = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				// 日志开始插入给的默认值
				UdsLogger.logEvent(LogEvent.CHILD_JOB_STEP, cmd, workDir, logPath);
				Date date = DateUtils.parserDate("2099-01-01 00:00:01", DateUtils.PATTERN_NORMAL);
				jobStepInfo.setEndtTime(new Timestamp(date.getTime()));
				jobStepInfo.setReturnCode(-1);
				udsJobRecordDao.insertJobStepRecord(stepId, platform, system, job, numTimes, jobDate, jobStepInfo,
						virtualEnable);
				processWork = processBuilder.start();
				File logFile = new File(logPath);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				String stepPath = jobStepInfo.getScript_dir() + File.separator + jobStepInfo.getScript_name();

				if (UdsConstant.TRANSFER_STEP_UTF8 == UdsConstant.TRUE_NUM) {
					bufferedReader = new BufferedReader(
							new InputStreamReader(processWork.getInputStream()));
					bufferedWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(logFile, true), "utf-8"));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						bufferedWriter.write(line);
						bufferedWriter.flush();
					}
					bufferedWriter.flush();
				} else {
					inputStream = new BufferedInputStream(processWork.getInputStream());
				    outputStream = new BufferedOutputStream(new FileOutputStream(logFile, true));
					byte[] bytes = new byte[1024];
					int tmp = 0;
					while ((tmp = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, tmp);
						outputStream.flush();
					}
					outputStream.flush();
				}

				// 线程阻塞，等待外部返回执行结果
				processWork.waitFor();
				// 执行结束退出获取返回值
				returnCode = processWork.exitValue();

			} catch (IOException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			} catch (InterruptedException e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
				if (bufferedWriter != null) {
					try {
						bufferedWriter.close();
					} catch (IOException e) {
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
					}
				}
				processWork.destroy();
				jobStepInfo.setReturnCode(returnCode);
				jobStepInfo.setEndtTime(new Timestamp(System.currentTimeMillis()));
				udsJobRecordDao.updateJobStepRecordById(stepId, platform, system, job, numTimes, jobDate, jobStepInfo,
						virtualEnable);
			}
		}
	}

	public JobRunable(UdsJobBean udsJobBean, List<JobStepInfo> tmplist) {
		Collections.sort(tmplist);
		this.jobStepList = new ArrayList<JobStepInfo>();
		jobStepList.addAll(tmplist);
		this.platform = udsJobBean.getPlatform();
		this.system = udsJobBean.getSystem();
		this.job = udsJobBean.getJob();
		this.jobDate = udsJobBean.getJob_date();
		this.jobType = JobType.valueOf(udsJobBean.getJob_type());
		this.numTimes = udsJobBean.getNum_times();
		this.batch = udsJobBean.getBatch();
		this.virtualEnable = udsJobBean.getVirtual_enable();
		StringBuffer nameBuffer = new StringBuffer();
		nameBuffer.append(batch).append(Symbol.XIA_HUA_XIAN).append(job).append(Symbol.XIA_HUA_XIAN).append(jobDate);
		this.name = nameBuffer.toString();
		this.setpNum = 0;
		this.jobStart = new Timestamp(System.currentTimeMillis());
		this.returnCode = 255;
		this.call_again_max_num = udsJobBean.getCall_again_max_num();
		this.call_again_num = udsJobBean.getCall_again_num();
		this.check_file_stream = udsJobBean.getCheck_file_stream();
		this.ignore_error = udsJobBean.getIgnore_error();
		this.check_weight = udsJobBean.getCheck_weight();
		this.weightMap = udsJobBean.getWeightConfMap();

	}

	public void kill() {
		processWork.destroy();
	}

	public Timestamp getJobStart() {
		return jobStart;
	}

	public void setJobStart(Timestamp jobStart) {
		this.jobStart = jobStart;
	}

	public List<JobStepInfo> getJobStepList() {
		return jobStepList;
	}

	public void setJobStepList(List<JobStepInfo> jobStepList) {
		this.jobStepList = jobStepList;
	}

	public byte getSetpNum() {
		return setpNum;
	}

	public void setSetpNum(byte setpNum) {
		this.setpNum = setpNum;
	}

	public Process getProcessWork() {
		return processWork;
	}

	public void setProcessWork(Process processWork) {
		this.processWork = processWork;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public JobType getJoyType() {
		return jobType;
	}

	public void setJoyType(JobType jobType) {
		this.jobType = jobType;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String job_date) {
		this.jobDate = job_date;
	}

	public long getNumTimes() {
		return numTimes;
	}

	public void setNumTimes(long num_times) {
		this.numTimes = num_times;
	}

	public byte getVirtualEnable() {
		return virtualEnable;
	}

	public void setVirtualEnable(byte virtualEnable) {
		this.virtualEnable = virtualEnable;
	}

	public boolean isNotForcestartJob() {
		return notForcestartJob;
	}

	public void setNotForcestartJob(boolean blean) {
		this.notForcestartJob = blean;
	}

	public byte getCall_again_max_num() {
		return call_again_max_num;
	}

	public void setCall_again_max_num(byte call_again_max_num) {
		this.call_again_max_num = call_again_max_num;
	}

	public byte getCall_again_num() {
		return call_again_num;
	}

	public void setCall_again_num(byte call_again_num) {
		this.call_again_num = call_again_num;
	}

}
