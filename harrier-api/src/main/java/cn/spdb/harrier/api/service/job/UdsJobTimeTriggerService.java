package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;
import java.util.List;

public interface UdsJobTimeTriggerService {
    /**
     *新增
     */
    int add(UdsJobTimeTrigger record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobTimeTrigger record);

    /**
     *查看详情
     */
    UdsJobTimeTrigger getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobTimeTrigger> listQuery();
}