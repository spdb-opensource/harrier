package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import java.util.List;

public interface UdsJobStepRecordService {
    /**
     *新增
     */
    int add(UdsJobStepRecord record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobStepRecord record);

    /**
     *查看详情
     */
    UdsJobStepRecord getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobStepRecord> listQuery();
    
    /**
     * 作业列表-查询作业脚本运行记录
     */
    List<UdsJobStepRecord> selectJobStepRecord(Long job_record_id);
}