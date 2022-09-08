package cn.spdb.harrier.api.service.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.job.UdsSystemService;
import cn.spdb.harrier.dao.entity.UdsSystem;
import cn.spdb.harrier.dao.mapper.UdsSystemMapper;

@Service
public class UdsSystemServiceImpl implements UdsSystemService {
    @Autowired
    private UdsSystemMapper mapper;

	@Override
	public int add(UdsSystem record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UdsSystem record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UdsSystem getDetail(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UdsSystem> listQuery() {
		// TODO Auto-generated method stub
		return mapper.select( c->c );
	}

	@Override
	public Page<UdsSystem> selectAll(Page<UdsSystem> page, String platform, String systems, Boolean usePlatform,String server_role_name_group) {
		return mapper.selectAll(page, platform, systems, usePlatform, server_role_name_group);
	}

	@Override
	public int updateConcurrencyState(UdsSystem udsSystem) {
		return mapper.updateConcurrencyState(udsSystem);
	}

	@Override
	public List<UdsSystem> selectByPlatform(String platform) {
		return mapper.selectByPlatform(platform);
	}

	@Override
	public int delete(Integer[] ids) {
		return mapper.delete(ids);
	}

	@Override
	public List<UdsSystem> selectAllPlatform() {
		return mapper.selectAllPlatform();
	}
}
