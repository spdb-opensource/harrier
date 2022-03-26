package cn.com.spdb.uds.background.socket.command;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.dao.BaseDao;
import cn.com.spdb.uds.utils.Symbol;
import cn.com.spdb.uds.utils.UdsUtils;
import io.netty.util.internal.StringUtil;

public class AppointMaster extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		if (!UdsConstant.IS_PRIMARY_SERVER) {
			buffer.append("not master");
			return;
		}
		if(StringUtil.isNullOrEmpty(param)) {
			buffer.append("please get appoint master id and min id ").append("/r/n");
			return;
		}
		String[] strs=param.trim().split(Symbol.KONG_GE);
		if(!(strs.length>1&&strs[0].matches("[0-9]+")&&strs[1].matches("[0-9]+"))) {
			buffer.append("please get 2 init param").append("/r/n");
			return;
		}
		short order=Short.parseShort(strs[0]);
		if(UdsConstant.ORDER!=order) {
			buffer.append("please choose server ").append("/r/n");
			return;
		}
		short newOrder=Short.parseShort(strs[1]);	
		BaseDao baseDao= DBManager.getInstance().getDao(BaseDao.class);
		buffer.append("param:").append(param).append("/r/n");
		baseDao.update("update uds_server set `order`="+newOrder+ "where `order`="+order);
		UdsConstant.ORDER=newOrder;
		buffer.append(UdsUtils.linkServerMachine()).append("/r/n");
		MasterManager.getInstance().setCheckRecive(true);
		
	}

	//

}
