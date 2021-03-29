package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;

public class KillJobRuning implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		if (UdsConstant.IS_PRIMARY_SERVER) {
			return "server is master";
		} else {
			String job = param.trim();
			UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			UdsJobBean udsjobbean = baseDao.getUdsJobBeanByJob(job);
			if (udsjobbean == null) {
				if (pw != null) {
					pw.print("job is null please check job name");
				}
				return "job is null please check job name";
			}
			if (UdsConstant.IS_PRIMARY_SERVER) {
				pw.print("servre is master");
			}
			ChildManager.getInstance().killJobRun(job);
		}
		return HttpResultCode.SUCCESS;
	}

}
