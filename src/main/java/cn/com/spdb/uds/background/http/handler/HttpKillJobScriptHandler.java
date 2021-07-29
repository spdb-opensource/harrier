package cn.com.spdb.uds.background.http.handler;

import java.util.Map;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.KillJobRuning;
import cn.com.spdb.uds.background.socket.command.KillJobScript;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

@HttpMapProtocol(value = "/kill/job/script")
public class HttpKillJobScriptHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		String job = (String) objectMap.get("job");
		Integer type = (Integer) objectMap.get("type");
		if (type == null) {
			type = 0;
		}
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsjobbean = baseDao.getUdsJobBeanByJob(job);
		if (udsjobbean == null) {
			return "job is null please check job name";
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
		if (type == 0) {
			succ = UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent,
					KillJobRuning.class.getSimpleName() + " " + job);
		} else {
			succ = UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent,
					KillJobScript.class.getSimpleName() + " " + job);
		}
		UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, "kill job ", job, succ);
		if (succ) {
			return HttpResultCode.SUCCESS;
		}
		return HttpResultCode.ERROR;
	}

}
