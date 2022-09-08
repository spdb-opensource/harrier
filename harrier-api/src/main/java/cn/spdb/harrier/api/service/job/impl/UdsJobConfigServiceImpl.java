package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobConfigService;
import cn.spdb.harrier.dao.entity.UdsJobConfig;
import cn.spdb.harrier.dao.mapper.UdsJobConfigMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobConfigServiceImpl implements UdsJobConfigService {
    @Autowired
    private UdsJobConfigMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobConfig record) {
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
    public int update(UdsJobConfig record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobConfig getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobConfig> listQuery() {
        return mapper.select( null );
    }
    
	/**
	 * 启用禁用
	 */
	@Override
	public int setEnable(String platform, String systems, String job, Boolean enable) {
		return mapper.setEnable(platform, systems, job, enable);
	}

	/**
	 * 置实置虚
	 */
	@Override
	public int setVirtual(String platform, String systems, String job, Boolean virtual) {
		return mapper.setVirtual(platform, systems, job, virtual);
	}
}