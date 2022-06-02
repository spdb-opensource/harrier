package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobDependencyService;
import cn.spdb.harrier.dao.entity.UdsJobDependency;
import cn.spdb.harrier.dao.mapper.UdsJobDependencyMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobDependencyServiceImpl implements UdsJobDependencyService {
    @Autowired
    private UdsJobDependencyMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobDependency record) {
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
    public int update(UdsJobDependency record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobDependency getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobDependency> listQuery() {
        return mapper.select( null );
    }

	/**
	 * 启用禁用依赖
	 */
	@Override
	public int setDepEnable(String pjob, String job_, Boolean enable_) {
		return mapper.setDepEnable(pjob, job_, enable_);
	}
    
}