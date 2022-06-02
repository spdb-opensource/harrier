package cn.spdb.harrier.api.service.alarm;

import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import java.util.List;

public interface MAlarmSendQueueService {
    /**
     *新增
     */
    int add(MAlarmSendQueue record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(MAlarmSendQueue record);

    /**
     *查看详情
     */
    MAlarmSendQueue getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<MAlarmSendQueue> listQuery();
}