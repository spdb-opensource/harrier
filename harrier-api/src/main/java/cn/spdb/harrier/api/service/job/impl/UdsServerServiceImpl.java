package cn.spdb.harrier.api.service.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.job.UdsServerService;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.mapper.UdsServerMapper;

@Service
public class UdsServerServiceImpl implements UdsServerService {
	@Autowired
	private UdsServerMapper mapper;
    
	@Override
	public int add(UdsServer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UdsServer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UdsServer getDetail(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UdsServer> listQuery() {
		return mapper.select( c -> c );
	}
	
	@Override
	public Page<UdsServer> selectAll(Page<UdsServer> page, String servername) {
		return mapper.selectAll(page, servername);
	}

	@Override
	public int setEnable(String serverName,Boolean is_enable) {
		return mapper.setEnable(serverName,is_enable);
	}

	@Override
	public List<UdsServer> getAvailableWorker() {
		return mapper.availableWorker();
	}

	@Override
	public List<UdsServer> getAvailableMaster() {
		return mapper.availableMaster();
	}

}
