package cn.spdb.harrier.api.service.alarm.impl;

import cn.spdb.harrier.api.service.alarm.MAlarmMsgService;
import cn.spdb.harrier.dao.entity.MAlarmMsg;
import cn.spdb.harrier.dao.mapper.MAlarmMsgMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class MAlarmMsgServiceImpl implements MAlarmMsgService {
    @Autowired
    private MAlarmMsgMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(MAlarmMsg record) {
        return mapper.insertSelective( record );
    }

    /**
     *id删除
     */
    @Override
    public int delete(Integer id) {
        return mapper.deleteByPrimaryKey( id );
    }

    /**
     *修改
     */
    @Override
    public int update(MAlarmMsg record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public MAlarmMsg getDetail(Integer id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<MAlarmMsg> listQuery() {
        return mapper.select( null );
    }

	@Override
	public Page<MAlarmMsg> search(Page<MAlarmMsg> page, String platfrom, String system, String code) {
		return mapper.search(page, platfrom, system, code);
	}
}