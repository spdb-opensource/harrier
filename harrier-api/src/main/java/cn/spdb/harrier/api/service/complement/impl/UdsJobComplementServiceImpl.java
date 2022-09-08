package cn.spdb.harrier.api.service.complement.impl;

import cn.spdb.harrier.api.service.complement.UdsJobComplementService;
import cn.spdb.harrier.dao.entity.UdsJobComplement;
import cn.spdb.harrier.dao.entity.UdsJobMenu;
import cn.spdb.harrier.dao.mapper.UdsJobComplementMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class UdsJobComplementServiceImpl implements UdsJobComplementService {
    @Autowired
    private UdsJobComplementMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobComplement record) {
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
    public int update(UdsJobComplement record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobComplement getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobComplement> listQuery() {
        return mapper.select( null );
    }
    
    /**
     * 补数记录—查询所有记录(分页)
     */
	@Override
	public Page<UdsJobComplement> selectAll(Page<UdsJobComplement> page,String platform,String systems,String job,String last_status, Long complementId) {
		return mapper.selectAll(page,platform,systems,job,last_status,complementId);
	}
}