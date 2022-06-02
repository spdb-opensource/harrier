package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import java.util.List;

public interface UdsJobDateFrequencyService {
    /**
     *新增
     */
    int add(UdsJobDateFrequency record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobDateFrequency record);

    /**
     *查看详情
     */
    UdsJobDateFrequency getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobDateFrequency> listQuery();
    
    /**
     * 作业列表-查询作业频度
     */
    List<UdsJobDateFrequency> selectJobFrequency(String platform_, String system_, String job_);
    
}