package cn.com.spdb.uds.background.socket.command;

import java.util.Map.Entry;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.bean.PlatformSystemCenterBean;
import cn.com.spdb.uds.core.master.MasterFactory;
import cn.com.spdb.uds.core.master.MasterManager;

public class ClearMasterPlatformMap extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		String  platform= param!=null? param.trim():"";
		buffer.append("clear").append(platform).append("\r\n");
		if (!UdsConstant.IS_PRIMARY_SERVER) {
			buffer.append("not master");
		} else {
			if(platform.equals("ALL")) {
				buffer.append("clear platform all").append("\r\n");
			}else{
				MasterFactory masterFactory = MasterManager.getInstance().getMasterFactory();
				for (Entry<String, PlatformSystemCenterBean> t : masterFactory.getPlatformMap().entrySet()) {
					if(t.getKey().equals(param)) {
						masterFactory.getPlatformMap().remove(t.getKey());
						buffer.append("clear succ platform :").append(t.getKey()).append("\r\n");
					}
				}
			}
		}

	}

}
