package cn.spdb.harrier.api.service.alarm.impl;

import cn.spdb.harrier.api.service.alarm.MAlarmSendQueueService;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import cn.spdb.harrier.dao.mapper.MAlarmSendQueueMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MAlarmSendQueueServiceImpl implements MAlarmSendQueueService {
    @Autowired
    private MAlarmSendQueueMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(MAlarmSendQueue record) {
        return mapper.insertSelective( record );
    }

    /**
     *id删除
     */
    @Override
    public int delete(Long id) {
        return mapper.deleteByPrimaryKey( id );
    }

    /**
     *修改
     */
    @Override
    public int update(MAlarmSendQueue record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public MAlarmSendQueue getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<MAlarmSendQueue> listQuery() {
        return mapper.select( null );
    }
}