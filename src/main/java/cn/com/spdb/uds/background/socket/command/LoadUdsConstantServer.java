package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.dao.UdsServerDao;
import cn.com.spdb.uds.utils.UdsUtils;

public class LoadUdsConstantServer implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		UdsServerDao udsServerDao = DBManager.getInstance().getDao(UdsServerDao.class);
		UdsServerBean udsServerBean = udsServerDao.getUdsServerByName(UdsConstant.SERVER_NAME);
		if (udsServerBean.getLocation() != UdsConstant.LOCATION) {
			UdsConstant.loadServerConstant(udsServerBean);
			UdsRpcClientManager.getInstance().shutDownRpcClient();
			UdsUtils.linkServerMachine();
		} else {
			UdsConstant.loadServerConstant(udsServerBean);
		}
		ChildManager.getInstance().setExecutorMaxJob();
		return HttpResultCode.SUCCESS;
	}

}
