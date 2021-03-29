package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsServerBean;
import cn.com.spdb.uds.db.dao.UdsServerDao;

public class SendLocate implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		//本地添加客户端
		UdsServerDao dao = DBManager.getInstance().getDao(UdsServerDao.class);
		UdsServerBean serverBean = dao.getUdsServerByName(UdsConstant.SERVER_NAME);
		if (serverBean == null) {
			pw.println("serverBean is null");
			return HttpResultCode.ERROR;
		}
		UdsRpcClient udsRpcClient = new UdsRpcClient(serverBean);
		UdsRpcClientManager.getInstance().getRPC_CLIENT_MAP().put(UdsConstant.SERVER_NAME, udsRpcClient);
		UdsConstant.SEND_LOCATE=1;
		pw.println("ok rpcClient: "+udsRpcClient.toString());
		UdsConstant.loadNoticeFinshJob();
		return HttpResultCode.SUCCESS;
	}

}
