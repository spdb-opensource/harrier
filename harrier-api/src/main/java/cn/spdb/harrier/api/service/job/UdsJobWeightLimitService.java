package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobWeightLimit;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsJobWeightLimitService {
    /**
     *新增
     */
    int add(UdsJobWeightLimit record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobWeightLimit record);

    /**
     *查看详情
     */
    UdsJobWeightLimit getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobWeightLimit> listQuery();
    
	Page<UdsJobWeightLimit> selectAll(Page<UdsJobWeightLimit> page, Integer limit_type);

	int updateWeightLimit(Integer limit_type, Integer limit_value, String serverIds_, String timeWindows);
}