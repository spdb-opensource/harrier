package cn.spdb.harrier.api.service.job.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.dao.mapper.CustomSqlMapper;


import cn.spdb.harrier.api.service.job.CustomSqlService;
@Service
public class CustomSqlServiceImpl implements CustomSqlService {
    
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private CustomSqlMapper mapper;
	
	@Override
	public List<Map<String, Object>> execSelect(int pageCurrent,int pageSize,String selectSql){
		
		SqlRowSetMetaData srsmd =  getSqlRowSetMetaData(selectSql);
		final String[] columnNames = srsmd.getColumnNames();
		Map<String, Object> columnNamesMap = new HashMap<String, Object>();
		columnNamesMap.put("columnNames", columnNames);
		
		List<Map<String, Object>> dataList = mapper.execSelect(pageCurrent,pageSize,selectSql);
		dataList.add(0, columnNamesMap);
		return dataList;
	}
	
	@Override
	public int execSelectCount(String selectSql){
		return mapper.execSelectCount(selectSql).size();
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
	
	private SqlRowSetMetaData getSqlRowSetMetaData(String selectSql) {
		try {
			String pageSql = "select page.* from ( " + selectSql  + ") page limit " + 0 + "," + 1;
			return jdbcTemplate.queryForRowSet(pageSql).getMetaData();
		} catch (DataAccessException dae) {
			throw dae;
		}
	}


	
}
