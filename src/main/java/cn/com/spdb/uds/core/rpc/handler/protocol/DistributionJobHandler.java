package cn.com.spdb.uds.core.rpc.handler.protocol;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.core.bean.UdsErrorCode;
import cn.com.spdb.uds.core.bean.UdsErrorLevel;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventCallBack;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

@RpcEventProtocol(RpcCommand.DISTRIBUTION_JOB)
public class DistributionJobHandler implements ServerRpcEventCallBack, ServerRpcEventHandler {

	@Override
	public void callback(UdsRpcEvent callBackEvent) {
		String jobName = callBackEvent.getAttribute(RpcAttrKey.JOB_NAME);
		int code = callBackEvent.getAttribute(RpcAttrKey.CODE);
		if (code == RpcResultCode.SUCCESS) {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "distribut job  sucess", jobName);
		} else {
			UdsLogger.logEvent(LogEvent.MASTER_DISPATCHER, "distribut job  error", jobName);
			UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
			UdsJobBean bean = udsJobBaseDao.getUdsJobBeanByJob(jobName);
			if (bean != null) {
				// 并发归还
				MasterManager.getInstance().decrementChildServerSystem(callBackEvent.getSourceId(), bean.getPlatform(),
						bean.getSystem());
				// 权重归还
				MasterManager.getInstance().subWeight(callBackEvent.getSourceId(), jobName);
				// 重新分发
				// MasterManager.getInstance().addJobToDispatcherWaitMap(bean);
			}
		}
	}

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {
		event.addAttribute(RpcAttrKey.JOB_NAME, String.valueOf(paramters));
	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		String jobName = event.getAttribute(RpcAttrKey.JOB_NAME);
		if (StringUtils.isEmpty(jobName)) {
			return null;
		}
		UdsRpcEvent callbackEvent = event.callBackEvent();
		callbackEvent.addAttribute(RpcAttrKey.JOB_NAME, jobName);
		UdsJobBaseDao udsJobBaseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsJobBean = udsJobBaseDao.getUdsJobBeanByJob(jobName);
		if (udsJobBean != null) {
			boolean sucess = false;
			// 地域判断
			if ((udsJobBean.getLocation() & UdsConstant.LOCATION) > 0
					&& udsJobBean.getLast_status().equals(JobStatus.DISPATCHER.status())) {
				sucess = ChildManager.getInstance().submitJob(udsJobBean);
			}
			// 成功回调
			if (sucess) {
				callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.SUCCESS);
				return callbackEvent;
			}
			UdsLogger.logEvent(LogEvent.CHILD_DISPATCHER, "job not DISPATCHER", udsJobBean.getJob(),
					udsJobBean.getLast_status());
		} else {
			UdsLogger.logErrorInstertDbError(UdsErrorCode.JOB_DB_NULL, UdsErrorLevel.M, jobName, "udsJobBean is null");
		}
		callbackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
		return callbackEvent;
	}

}
