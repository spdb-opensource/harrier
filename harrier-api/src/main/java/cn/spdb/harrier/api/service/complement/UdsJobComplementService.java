package cn.spdb.harrier.api.service.complement;

import cn.spdb.harrier.dao.entity.UdsJobComplement;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsJobComplementService {
    /**
     *新增
     */
    int add(UdsJobComplement record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobComplement record);

    /**
     *查看详情
     */
    UdsJobComplement getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobComplement> listQuery();
    
	/**
	 * 补数记录—查询所有记录(分页)
	 */
	Page<UdsJobComplement> selectAll(Page<UdsJobComplement> page, String platform, String systems, String job, String last_status,Long complementId);

}