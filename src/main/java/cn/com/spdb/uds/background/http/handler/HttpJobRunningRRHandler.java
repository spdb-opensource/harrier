package cn.com.spdb.uds.background.http.handler;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;


@HttpMapProtocol(value = "/runpolling/job")
public class HttpJobRunningRRHandler extends AbstractHttpPostBodyWorkHandler{

	@Override
	public String handler(Map<String, Object> objectMap) {
		String job = (String) objectMap.get("job");
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsjobbean = baseDao.getUdsJobBeanByJob(job);
		if (udsjobbean == null) {
			return "job is null please check job name";
		}
		HashMap<String,String> resMap = new HashMap<String,String>();
		resMap.put("job_status", udsjobbean.getLast_status());
		resMap.put("job_date",udsjobbean.getJob_date());
		resMap.put("batch",""+udsjobbean.getBatch());
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, "POLLING JOB STATUS ", job);
		return JSON.toJSONString(resMap);
	}

}
