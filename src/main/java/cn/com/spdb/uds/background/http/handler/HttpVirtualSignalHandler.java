package cn.com.spdb.uds.background.http.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.JobType;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsJobDateFrequencyBean;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.db.dao.UdsJobControlDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;

@HttpMapProtocol(value = "/test/signal")
public class HttpVirtualSignalHandler extends AbstractHttpPostBodyWorkHandler{

	@Override
	public String handler(Map<String, Object> objectMap) {
		System.out.println("start-test-signal");
		String platform = (String) objectMap.get("platform");
		System.out.println(platform);
		
		String job_date = (String) objectMap.get("job_date");
		String param = (String) objectMap.get("param");
		System.out.println(job_date);
		String select_signalstm_job = new String();
		if(!platform.equals("EDW")) {
			if(platform.equals("BDP")) {
				select_signalstm_job = "SELECT platform,system,job,job_name,job_type,last_status,job_date,batch,virtual_enable,is_enable FROM UDS_JOB " + 
						"WHERE JOB NOT IN (SELECT STM_JOB FROM UDS_JOB_STREAM ) " + 
						"AND JOB NOT IN (SELECT JOB FROM UDS_JOB_DATE_TRIGGER WHERE IS_ENABLE='1') " + 
						"AND IS_ENABLE='1' and platform not in ('BDM','BDQ','EDW') and job <>'BDP_CTR_0_START'";
			}else {
				select_signalstm_job = "SELECT platform,system,job,job_name,job_type,last_status,job_date,batch,virtual_enable,is_enable FROM UDS_JOB " + 
						"WHERE JOB NOT IN (SELECT STM_JOB FROM UDS_JOB_STREAM ) " + 
						"AND JOB NOT IN (SELECT JOB FROM UDS_JOB_DATE_TRIGGER WHERE IS_ENABLE='1') " + 
						"AND IS_ENABLE='1' and platform= '"+platform+"' and job not in('BDM_CTR_0_START','BDQ_CTR_0_START');";
			}
		}else {
			if(param.equals("0")) {
				select_signalstm_job = "SELECT platform,system,job,job_name,job_type,last_status,job_date,batch,virtual_enable,is_enable FROM UDS_JOB " + 
						"WHERE JOB NOT IN (SELECT STM_JOB FROM UDS_JOB_STREAM where is_enable = '1') " + 
						"AND JOB NOT IN (SELECT JOB FROM UDS_JOB_DATE_TRIGGER WHERE IS_ENABLE='1') " + 
						"AND IS_ENABLE='1' and platform ='EDW' and system = 'CTR' and job like '%START%'";
			}else {
				return null;
			}
			
		}
		System.out.println(select_signalstm_job);
		BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
		UdsJobBaseDao baseDaot = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		//
		List<HashMap<Object, Object>> list1 = baseDao.select(select_signalstm_job);
		if (!list1.isEmpty()) {
			System.out.println(list1.size());
			for (HashMap<Object, Object> map : list1) {
//				String platformt = (String) map.get("platform");
//				System.out.println("platformt="+platformt);
//				String systemt = (String) map.get("system");
				String job = (String) map.get("job");
//				String jobtype = (String) map.get("job_type");
//				Integer batchtmp = (Integer) map.get("BATCH_NO");
//				System.out.println("batch="+batchtmp);
				UdsJobBean udsJobBean = baseDaot.getUdsJobBeanByJob(job);
				boolean flag = true;
				if(!udsJobBean.getJob_type().equals("D")) {
					JobType jobType = JobType.getJoyType(udsJobBean.getJob_type());
					UdsJobControlDao controlDao = DBManager.getInstance().getDao(UdsJobControlDao.class);
					String new_job_date = job_date;
					String old_job_date = udsJobBean.getJob_date();
					// 上次执行信号文件时间
					Date old_Date = DateUtils.parserDate(old_job_date, DateUtils.PATTERN_YYYYMMDD_CONS);
					// 本次需要执行信号文件时间
					Date new_Date = DateUtils.parserDate(new_job_date, DateUtils.PATTERN_YYYYMMDD_CONS);
					Date tDate = null;			
					switch (jobType) {
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
					flag = DateUtils.isSameDay(tDate, new_Date);
					
				}
				if(flag) {
					System.out.println("flag="+flag);
//					int tmp = baseDaot.updateJobPending(platformt,systemt,job, batchtmp, job_date);
					int tmp = baseDaot.updateJobPending(udsJobBean.getPlatform(), udsJobBean.getSystem(), job, udsJobBean.getBatch(), job_date);
					if (tmp <= 0) {
						return "作业执行失败";
					}
				}		
			}
		}
		return null;
	}

}
