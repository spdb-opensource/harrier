package cn.spdb.harrier.server.master.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.uitls.Host;
import cn.spdb.harrier.server.entity.WorkingInfo;

@Component
public class MasterMangerWorker {

	private ConcurrentHashMap<String, WorkingInfo> workingMap = new ConcurrentHashMap<String, WorkingInfo>();

	public Collection<WorkingInfo> getWorkList() {
		return workingMap.values();
	}

	public void updateWork(WorkingInfo workingInfo) {
		workingMap.put(workingInfo.getHost().getAddress(), workingInfo);
	}

	public WorkingInfo deleteWork(Host host) {
		return deleteWork(host.getAddress());
	}

	public WorkingInfo deleteWork(String address) {
		return workingMap.remove(address);
	}

	public WorkingInfo getWork(Host host) {
		return getWork(host.getAddress());
	}
	public WorkingInfo getWork(String address) {
		return workingMap.get(address);
	}
	
	
	public int getJobNumByPlatformAndSystem(String platform, String systems) {
		int count = 0;
		for (WorkingInfo workingInfo : workingMap.values()) {
			count += workingInfo.getJobNumByPlatformAndSystem(platform, systems);
		}
		return count;
	}

}
