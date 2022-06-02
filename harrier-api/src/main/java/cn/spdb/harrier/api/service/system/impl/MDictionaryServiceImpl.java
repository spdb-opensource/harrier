package cn.spdb.harrier.api.service.system.impl;

import cn.spdb.harrier.api.service.system.MDictionaryService;
import cn.spdb.harrier.dao.entity.MDictionary;
import cn.spdb.harrier.dao.mapper.MDictionaryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class MDictionaryServiceImpl implements MDictionaryService {
    @Autowired
    private MDictionaryMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(MDictionary record) {
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
    public int update(MDictionary record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public MDictionary getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<MDictionary> listQuery() {
        return mapper.select( null );
    }
    
    @Override
    public Page<MDictionary> search(Page<MDictionary> page){
    	return mapper.search(page);
    }
}