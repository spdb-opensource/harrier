package cn.com.spdb.uds.core.rpc.handler.protocol;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.core.rpc.handler.RpcEventProtocol;
import cn.com.spdb.uds.core.rpc.handler.ServerRpcEventHandler;

@RpcEventProtocol(RpcCommand.UPDATE_CHILD_INFO)
public class UpdateChildServerInfoHandler implements ServerRpcEventHandler {

	@Override
	public void sendHandle(UdsRpcEvent event, Object paramters) {

	}

	@Override
	public UdsRpcEvent receiveHandle(UdsRpcEvent event) {
		try {
			String platform = event.getAttribute("platform");
			String system = event.getAttribute("system");
			String job = event.getAttribute("job");
			Integer check_weight = event.getAttribute("check_weight");
			if (check_weight == UdsConstant.TRUE_NUM) {
				MasterManager.getInstance().subWeight(event.getSourceId(), job);
			}
			MasterManager.getInstance().decrementChildServerSystem(event.getSourceId(), platform, system);
			MasterManager.getInstance().offerDispatcherSignlaQueue(platform, system);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
