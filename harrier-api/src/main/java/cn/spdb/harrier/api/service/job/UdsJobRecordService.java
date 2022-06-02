package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import cn.spdb.harrier.dao.entity.UdsJobRecord;
import java.util.List;

public interface UdsJobRecordService {
    /**
     *新增
     */
    int add(UdsJobRecord record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobRecord record);

    /**
     *查看详情
     */
    UdsJobRecord getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobRecord> listQuery();
    
    /**
     * 作业列表-查询作业运行记录
     */
    List<UdsJobRecord> selectJobRecord(String platform_, String system_, String job_);
    
}