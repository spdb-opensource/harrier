package cn.spdb.harrier.api.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.dao.entity.MOperatLog;

public interface MOperatLogService {

	/**
     *未完成的列表查询，待完善
     */
    List<MOperatLog> listQuery();
    
    Page<MOperatLog> search(Page<MOperatLog> page, String userCode, String operatType, String job);
    
}
