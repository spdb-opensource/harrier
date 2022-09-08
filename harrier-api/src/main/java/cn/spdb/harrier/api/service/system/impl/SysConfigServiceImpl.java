package cn.spdb.harrier.api.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.service.system.ISysConfigService;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.dao.mapper.MDictionaryMapper;
import cn.spdb.harrier.dao.entity.MDictionary;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

	@Autowired
	private MDictionaryMapper dictionaryMapper; 
	
	@Override
	public boolean selectCaptchaOnOff(String key) {
		MDictionary mDictionary = dictionaryMapper.selectOneByKey(key).orElse(null);
		return mDictionary == null ? false :Boolean.valueOf(mDictionary.getDicValue());
	}

	@Override
	public Object selectConfigByKey(String string) {
		MDictionary mDictionary = dictionaryMapper.selectOneByKey(string).orElse(null);
		return mDictionary.getDicValue();
	}



}
