package cn.com.spdb.uds.background.socket.command;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.child.ChildManager;
import cn.com.spdb.uds.core.child.job.JobRunableList;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class RunScript implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		if (param.length() <= 0) {
			return HttpResultCode.ERROR;
		}
		List<HashMap<Object, Object>> list = new ArrayList<HashMap<Object, Object>>();
		List<JSONObject> tmpList = null;
		try {
			tmpList =  JSON.parseObject(param, List.class);
			for ( JSONObject json : tmpList) {
				String str=json.toJSONString();
				HashMap<Object, Object> map = JSON.parseObject(str,HashMap.class);
				list.add(map);
			}
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			return HttpResultCode.ERROR;
		}
		if (list.size() == 0) {
			return HttpResultCode.ERROR;
		}
		UdsLogger.logEvent(LogEvent.BACK_CONSOLE,"forcestart job",list);
		JobRunableList jobRunableList = new JobRunableList(list);
		ChildManager.getInstance().submitJobList(jobRunableList);
		return HttpResultCode.SUCCESS;

	}
}
