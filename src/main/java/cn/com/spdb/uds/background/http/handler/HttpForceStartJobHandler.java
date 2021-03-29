package cn.com.spdb.uds.background.http.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.AbstractHttpPostBodyWorkHandler;
import cn.com.spdb.uds.background.http.HttpMapProtocol;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.command.RunScript;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.master.plan.AbstractDispatcherPlan;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

@HttpMapProtocol(value = "/forceStart/job")
public class HttpForceStartJobHandler extends AbstractHttpPostBodyWorkHandler {

	@Override
	public String handler(Map<String, Object> objectMap) {
		List<JSONObject> objectlist = (List<JSONObject>) objectMap.get("list");
		String serverName = (String) objectMap.get("server_name");
		serverName = serverName == null ? "" : String.valueOf(serverName);
		int listSize = objectlist.size();
		if (listSize <= 0) {
			return "job list size <= 0";
		}
		if (objectlist.size() > UdsConstant.FORCESTART_LIST_JOB) {
			return "job max size > " + UdsConstant.FORCESTART_LIST_JOB;
		}

		// FIXME 特殊处理此处不通用
		int depSize = 0;
		for (JSONObject json : objectlist) {
			String str = json.toJSONString();
			HashMap<Object, Object> map = JSON.parseObject(str, HashMap.class);
			String job = (String) map.get("JOB");
			if (job.contains("DEP_")) {
				depSize++;
			}
		}

		if (depSize != 0 && depSize != listSize) {
			return "list is not all DEP";
		}

		/** 获取机器名 */
		UdsRpcClient udsRpcClient = null;
		if (StringUtils.isBlank(serverName)) {
			String str = objectlist.get(0).toJSONString();
			HashMap<Object, Object> map = JSON.parseObject(str, HashMap.class);
			String job = (String) map.get("JOB");
			UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			UdsJobBean udsJobBean = udsJobBaseDao.getUdsJobBeanByJob(job);
			if (udsJobBean == null) {
				UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "udsJobBean is null", job);
				return "udsJobBean is null";
			}
			if (!(udsJobBean.getLast_status().equals(JobStatus.DONE.status())
					|| udsJobBean.getLast_status().equals(JobStatus.READY.status()))) {
				return "job: " + job + " status is error ,not Done or Ready";
			}
			if (!((udsJobBean.getLocation() & UdsConstant.LOCATION) > 0)) {
				UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "Location is error", job, UdsConstant.LOCATION);
				return "job location and servre location not the same";
			}
			UdsSystemBean udsSystemBean = UdsConstant.getUdsSystemBean(udsJobBean.getPlatform(),
					udsJobBean.getSystem());
			if (udsSystemBean == null || udsSystemBean.getMax_run_job() <= 0) {
				UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "udsSystemBean run job is 0", job);
				return "udsSystemBean is null or platform  run job num is 0";
			}
			udsRpcClient = AbstractDispatcherPlan.getUdsRpcClientOver(udsSystemBean, udsJobBean);
		} else {
			udsRpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(serverName);
		}

		if (udsRpcClient == null) {
			UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "udsRpcClient is null");
			return "udsRpcClient is null";
		}

		serverName = udsRpcClient.getServerName();
		ChildServerInfo childInfo = MasterManager.getInstance().getChildServerJobMap().get(serverName);
		if (childInfo == null || childInfo.getMaxJobNum() <= 0) {
			UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "udsRpcClient is null");
			return "server is null or run max job number is 0,please choose other";
		}

		String listStr = JSON.toJSONString(objectlist);
		UdsRpcEvent udsRpcEvent = UdsRpcEvent.buildUdsRpcEvent(serverName, RpcCommand.SERVER_COMMAND);
		boolean succ = UdsRpcClientManager.getInstance().sendMessage(udsRpcClient, udsRpcEvent,
				RunScript.class.getSimpleName() + " " + listStr);
		if (!succ) {
			UdsLogger.error(LogEvent.HTTP_ERROR, "/forceStart/job", "sendMessage is error", listStr);
			return HttpResultCode.ERROR;
		}
		return HttpResultCode.SUCCESS;
	}
}
