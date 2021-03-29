package cn.com.spdb.uds.background.socket.command;

import java.util.concurrent.ConcurrentHashMap;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.socket.AbsstactConsoleCommand;
import cn.com.spdb.uds.core.bean.ChildServerInfo;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.master.MasterManager;

public class SeeChildJobNumInMaster extends AbsstactConsoleCommand {

	@Override
	public void hanlder(String param, StringBuffer buffer) {
		if (UdsConstant.IS_PRIMARY_SERVER) {
			ConcurrentHashMap<String, ChildServerInfo> childServerJobMap = MasterManager.getInstance()
					.getChildServerJobMap();
			for (ChildServerInfo info : childServerJobMap.values()) {
				buffer.append("name:" + info.getName() + " jobnum: " + info.getJobNum() + " systemNum:"
						+ info.getSystemJobMap()).append("\n");
				buffer.append("WeightMap:" + info.getWeightMap().toString()).append("\n");
				buffer.append("--------------").append("\n");
			}
		} else {
			ChildServerInfo info = ChildManager.getInstance().buildChildServerDate();
			buffer.append(
					"name:" + info.getName() + " jobnum: " + info.getJobNum() + " systemNum:" + info.getSystemJobMap())
					.append("\n");
			buffer.append("QJonNum:" + ChildManager.getInstance().getExecutorJobNum()).append("\n");
			buffer.append("WeightMap:" + ChildManager.getInstance().getWeightMap().toString()).append("\n");
		}
	}
}
