package cn.spdb.harrier.api.service.job.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.dao.mapper.CustomSqlMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.job.CustomSqlService;
@Service
public class CustomSqlServiceImpl implements CustomSqlService {
    
	@Autowired
    private CustomSqlMapper mapper;
	
	@Override
	public List<Map<String, Object>> execSelect(int pageCurrent,int pageSize,String selectSql){
		return mapper.execSelect(pageCurrent,pageSize,selectSql);
	}
	
	@Override
	public int updateExec(String updateSql){
		return mapper.execUpdate(updateSql);
	}

	@Override
	public int deleteExec(String deleteSql){
		return mapper.execDelete(deleteSql);
	}

	@Override
	public int insertExec(String insertSql){
		return mapper.execInsert(insertSql);
	}
	
}
