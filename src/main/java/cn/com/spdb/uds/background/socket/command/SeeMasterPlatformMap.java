package cn.com.spdb.uds.background.socket.command;

import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.bean.PlatformSystemCenterBean;
import cn.com.spdb.uds.core.master.MasterFactory;
import cn.com.spdb.uds.core.master.MasterManager;

public class SeeMasterPlatformMap extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		if (!UdsConstant.IS_PRIMARY_SERVER) {
			buffer.append("not master");
		} else {
			MasterFactory masterFactory = MasterManager.getInstance().getMasterFactory();
			for (Entry<String, PlatformSystemCenterBean> t : masterFactory.getPlatformMap().entrySet()) {
				buffer.append(t.getKey()).append("\r\n");
				String json = JSON.toJSONString(t.getValue().getPlatformConterMap());
				buffer.append(json).append("\r\n");
				buffer.append("\r\n");
			}
		}

	}

}
