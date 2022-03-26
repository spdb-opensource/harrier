package cn.com.spdb.uds.background.http.handler;

import java.util.Map;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.KillJobRuning;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

@HttpMapProtocol(value = "/runkill/job")
public class HttpJobRunningKillHandler extends AbstractHttpPostBodyWorkHandler{
	@Override
	public String handler(Map<String, Object> objectMap) {
		String job = (String) objectMap.get("job");
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsjobbean = baseDao.getUdsJobBeanByJob(job);
		if (udsjobbean == null) {
			return "job is null please check job name";
		}
		if (!udsjobbean.getLast_status().equals(JobStatus.RUNING.status())) {
			return "job status is not running";
		}
		String serverName = udsjobbean.getServer_name();
		if (StringUtils.isBlank(serverName)) {
			return "job serverName is null";
		}
		UdsRpcClient udsRpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
		if (udsRpcClient == null || !udsRpcClient.isActive()) {
			return "job of running client is null or notActive ";
		}
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(serverName, RpcCommand.SERVER_COMMAND);
		boolean succ = false;
		succ = UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent,
				KillJobRuning.class.getSimpleName() + " " + job);
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, "kill job ", job, succ);
		if (succ) {
			return HttpResultCode.SUCCESS;
		}
		return HttpResultCode.ERROR;
	}
}
