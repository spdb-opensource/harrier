package cn.spdb.harrier.api.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.system.MOperatLogService;
import cn.spdb.harrier.dao.entity.MOperatLog;
import cn.spdb.harrier.dao.mapper.MOperatLogMapper;

@Service
public class MOperatLogServiceImpl implements MOperatLogService {
	@Autowired
    private MOperatLogMapper mapper;
    
	@Override
	public List<MOperatLog> listQuery() {
		return mapper.select( c -> c );
	}
    
    @Override
    public Page<MOperatLog> search(Page<MOperatLog> page, String userCode, String operatType, String job){
    	return mapper.selectAll(page, userCode, operatType, job);
    }

}
