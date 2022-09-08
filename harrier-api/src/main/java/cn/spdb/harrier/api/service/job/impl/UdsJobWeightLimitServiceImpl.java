package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobWeightLimitService;
import cn.spdb.harrier.dao.entity.UdsJobWeightLimit;
import cn.spdb.harrier.dao.mapper.UdsJobWeightLimitMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class UdsJobWeightLimitServiceImpl implements UdsJobWeightLimitService {
    @Autowired
    private UdsJobWeightLimitMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobWeightLimit record) {
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
    public int update(UdsJobWeightLimit record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobWeightLimit getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobWeightLimit> listQuery() {
        return mapper.select( c -> c );
    }
    
	@Override
	public Page<UdsJobWeightLimit> selectAll(Page<UdsJobWeightLimit> page, Integer limit_type) {
		return mapper.selectAll(page, limit_type);
	}
	
	@Override
	public int updateWeightLimit(Integer limit_type,Integer limit_value,String serverIds_,String timeWindows) {
		return mapper.updateWeightLimit(limit_type, limit_value, serverIds_, timeWindows);
	}

	@Override
	public int delete(Long[] ids) {
		return mapper.delete(ids);
	}
}