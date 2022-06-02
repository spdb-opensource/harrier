package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobWeightService;
import cn.spdb.harrier.dao.entity.UdsJobWeight;
import cn.spdb.harrier.dao.mapper.UdsJobWeightMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class UdsJobWeightServiceImpl implements UdsJobWeightService {
    @Autowired
    private UdsJobWeightMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobWeight record) {
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
    public int update(UdsJobWeight record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobWeight getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobWeight> listQuery() {
        return mapper.select( c -> c );
    }
    
	@Override
	public Page<UdsJobWeight> selectAll(Page<UdsJobWeight> page, String platform, String systems, String job) {
		return mapper.selectAll(page, platform, systems, job);
	}
	@Override
	public int updateConfValue(String platform_,String systems_,String job_,Integer limit_type,Integer conf_value) {
		return mapper.updateConfValue(platform_, systems_, job_, limit_type, conf_value);
	}
}