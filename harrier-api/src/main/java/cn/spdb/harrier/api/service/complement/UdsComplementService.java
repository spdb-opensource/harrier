package cn.spdb.harrier.api.service.complement;

import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobComplement;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsComplementService {
    /**
     *新增
     */
    int add(UdsComplement record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsComplement record);

    /**
     *查看详情
     */
    UdsComplement getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsComplement> listQuery();
    
	/**
	 * 补数记录—查询所有记录(分页)
	 */
	Page<UdsComplement> selectAll(Page<UdsComplement> page, String comName);

}