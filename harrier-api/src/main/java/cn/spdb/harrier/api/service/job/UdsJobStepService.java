package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobStep;
import java.util.List;

public interface UdsJobStepService {
    /**
     *新增
     */
    int add(UdsJobStep record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobStep record);

    /**
     *查看详情
     */
    UdsJobStep getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobStep> listQuery();
}