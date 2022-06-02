package cn.spdb.harrier.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CustomSqlMapper {
	
	@Select(
	"<script>"
    + "${selectSql}"
	+"<bind name=\"key_offset\" value=\"(current - 1)*pageSize\"> </bind>"
    + "<if test=\"current!=null and pageSize!=null\">"
    + "LIMIT ${key_offset},${pageSize}"
    + "</if>"
    + "</script>")
	List<Map<String, Object>> execSelectSql(@Param("current") int pageCurrent,@Param("pageSize") int pageSize,@Param("selectSql") String selectSql);
	
	default List<Map<String, Object>> execSelect(int pageCurrent,int pageSize,String selectSql){
		return execSelectSql(pageCurrent,pageSize,selectSql);
	}
	
	@Update("${updateSql}")
	int execUpdate(@Param("updateSql") String selectSql);
	
	@Delete("${deleteSql}")
	int execDelete(@Param("deleteSql") String selectSql);
	
	@Insert("${insertSql}")
	int execInsert(@Param("insertSql") String selectSql);
	
}
