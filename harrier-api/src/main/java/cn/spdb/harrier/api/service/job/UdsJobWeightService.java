package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobWeight;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsJobWeightService {
    /**
     *新增
     */
    int add(UdsJobWeight record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobWeight record);

    /**
     *查看详情
     */
    UdsJobWeight getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobWeight> listQuery();
    
    Page<UdsJobWeight> selectAll(Page<UdsJobWeight> page,String platform,String systems,String job, Integer limitType);

	int updateConfValue(String platform_, String systems_, String job_, Integer limit_type, Integer conf_value);
	
	int delete(Long[] ids);
}