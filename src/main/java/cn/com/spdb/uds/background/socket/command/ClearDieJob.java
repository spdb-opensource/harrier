package cn.com.spdb.uds.background.socket.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.child.job.JobRunable;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class ClearDieJob implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		StringBuffer buffer = new StringBuffer();
		if (UdsConstant.IS_PRIMARY_SERVER && UdsConstant.SEND_LOCATE == UdsConstant.FALSE_NUM) {
			buffer.append("server is master");
		} else {
			ConcurrentHashMap<String, JobRunable> concurrentHashMap = ChildManager.getInstance().getRunJobsPool();
			for (JobRunable jobRunable : concurrentHashMap.values()) {
				UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
				UdsJobBean job = baseDao.getUdsJobBeanByJob(jobRunable.getJob());
				if (job == null || !job.getLast_status().equals(JobStatus.RUNING.status())) {
					jobRunable.kill();
					if (param.equals("kill")) {
						try {
							Runtime.getRuntime().exec("ps -ef | grep uds | grep " + job.getJob()
									+ " |grep -v grep |awk '{print \"kill \" $2}' | sh");
						} catch (IOException e) {
							UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
						}
					}
					buffer.append("job:").append(jobRunable.getJob()).append("|jobdata:")
							.append(jobRunable.getJobDate()).append("|batch:").append(jobRunable.getBatch())
							.append("\r\n");
				}
			}
			buffer.append("clear end");
		}
		if (pw != null) {
			pw.println(buffer);
		}
		return buffer.toString();
	}

}
