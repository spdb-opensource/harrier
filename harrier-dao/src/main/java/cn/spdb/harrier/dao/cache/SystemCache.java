package cn.spdb.harrier.dao.cache;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.dao.entity.UdsSystem;
import cn.spdb.harrier.dao.mapper.UdsSystemMapper;
import cn.spdb.harrier.dao.utils.BeanContext;

@Component
public class SystemCache {

	private ConcurrentHashMap<String, ConcurrentHashMap<String, UdsSystem>> hashMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, UdsSystem>>();

	@Autowired
	private UdsSystemMapper udsSytemMapper;

	public static SystemCache getInstance() {
		return BeanContext.getBean(SystemCache.class);
	}

	@PostConstruct
	@Autowired
	public void load() {
		List<UdsSystem> systemsList = udsSytemMapper.select(c -> c);
		for (UdsSystem system : systemsList) {
			addUdsSytem(system);
		}
	}

	public UdsSystem getPlatfromUdsSystem(String platform) {
		return getUdsSystem(platform, Symbol.XING_HAO);
	}

	public void addUdsSytem(UdsSystem system) {
		ConcurrentHashMap<String, UdsSystem> platfromMap = hashMap.get(system.getPlatform());
		if (platfromMap == null) {
			platfromMap = new ConcurrentHashMap<String, UdsSystem>();
			hashMap.put(system.getPlatform(), platfromMap);
		}
		platfromMap.put(system.getSystems(), system);
	}

	public UdsSystem getUdsSystem(String platform, String system) {
		ConcurrentHashMap<String, UdsSystem> platfromMap = hashMap.get(platform);
		if (platform == null) {
			UdsSystem udsSystem = udsSytemMapper.selectUdsSystem(platform, system);
			if (udsSystem != null) {
				addUdsSytem(udsSystem);
				return udsSystem;
			}
		}
		return platfromMap.get(system);
	}

	public UdsSystem getUdsSystemByUseful(String platform, String system) {
		if (Symbol.XING_HAO.equals(system)) {
			return getPlatfromUdsSystem(platform);
		}
		UdsSystem udsSystem = getUdsSystem(platform, system);
		if (ObjectUtils.isEmpty(udsSystem)) {
			return getPlatfromUdsSystem(platform);
		} else {
			if (udsSystem.getUsePlatform()) {
				return getPlatfromUdsSystem(platform);
			}
			return udsSystem;
		}
	}

	public List<String> getPlatformList() {
		Enumeration<String> enumeration = hashMap.keys();
		List<String> platformList = new ArrayList<String>();
		while (enumeration.hasMoreElements()) {
			String platform = enumeration.nextElement();
			platformList.add(platform);
		}
		return platformList;
	}

}
