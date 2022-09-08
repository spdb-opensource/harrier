package cn.spdb.harrier.api.service.alarm.impl;

import cn.spdb.harrier.api.service.alarm.MAlarmConfigService;
import cn.spdb.harrier.dao.entity.MAlarmConfig;
import cn.spdb.harrier.dao.mapper.MAlarmConfigMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class MAlarmConfigServiceImpl implements MAlarmConfigService {
    @Autowired
    private MAlarmConfigMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(MAlarmConfig record) {
        return mapper.insertSelective( record );
    }

    /**
     *id删除
     */
    @Override
    public int delete(Integer id,String platfrom) {
		return mapper.deleteByPrimaryKey(id,platfrom);
    }

    /**
     *修改
     */
    @Override
    public int update(MAlarmConfig record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public MAlarmConfig getDetail(Integer id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<MAlarmConfig> listQuery() {
        return mapper.select( null );
    }

	@Override
	public Page<MAlarmConfig> search(Page<MAlarmConfig> page, String platfrom, String system, String job, String code) {
		
		return mapper.search(page, platfrom, system, code,job);
	}

	@Override
	public Page<MAlarmConfig> searchJobConfig(Page<MAlarmConfig> page, String platfrom, String system, String job,
			String code) {
		return mapper.searchJobConfig(page, platfrom, system, code,job);
	}
}