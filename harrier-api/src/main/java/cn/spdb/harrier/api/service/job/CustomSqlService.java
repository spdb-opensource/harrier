package cn.spdb.harrier.api.service.job;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public interface CustomSqlService {
	int updateExec(String updateSql);
	int deleteExec(String deleteSql);
	int insertExec(String insertSql);
	List<Map<String, Object>> execSelect(int pageCurrent, int pageSize, String selectSql);
	int execSelectCount(String selectSql);
}
