package cn.spdb.harrier.api.service.complement.impl;

import cn.spdb.harrier.api.service.complement.UdsComplementService;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobComplement;
import cn.spdb.harrier.dao.mapper.UdsComplementMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class UdsComplementServiceImpl implements UdsComplementService {
    @Autowired
    private UdsComplementMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsComplement record) {
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
    public int update(UdsComplement record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsComplement getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsComplement> listQuery() {
        return mapper.select( null );
    }
    
    /**
     * 补数记录—查询所有记录(分页)
     */
	@Override
	public Page<UdsComplement> selectAll(Page<UdsComplement> page, String comName) {
		return mapper.selectAll(page,comName);
	}
}