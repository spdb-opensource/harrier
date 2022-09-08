package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobSource;
import java.util.List;

public interface UdsJobSourceService {
    /**
     *新增
     */
    int add(UdsJobSource record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobSource record);

    /**
     *查看详情
     */
    UdsJobSource getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobSource> listQuery();
}