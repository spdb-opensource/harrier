package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobSourceService;
import cn.spdb.harrier.dao.entity.UdsJobSource;
import cn.spdb.harrier.dao.mapper.UdsJobSourceMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobSourceServiceImpl implements UdsJobSourceService {
    @Autowired
    private UdsJobSourceMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobSource record) {
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
    public int update(UdsJobSource record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobSource getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobSource> listQuery() {
        return mapper.select( null );
    }
}