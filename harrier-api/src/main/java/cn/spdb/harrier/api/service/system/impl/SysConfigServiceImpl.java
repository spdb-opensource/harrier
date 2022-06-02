package cn.spdb.harrier.api.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.service.system.ISysConfigService;
import cn.spdb.harrier.dao.mapper.MDictionaryMapper;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

	@Autowired
	private MDictionaryMapper dictionaryMapper; 
	
	
	
	
	
	@Override
	public boolean selectCaptchaOnOff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object selectConfigByKey(String string) {
		return "true";
	}



}
