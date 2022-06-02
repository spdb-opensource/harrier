package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobStepService;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.dao.mapper.UdsJobStepMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobStepServiceImpl implements UdsJobStepService {
    @Autowired
    private UdsJobStepMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobStep record) {
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
    public int update(UdsJobStep record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobStep getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobStep> listQuery() {
        return mapper.select( null );
    }
}