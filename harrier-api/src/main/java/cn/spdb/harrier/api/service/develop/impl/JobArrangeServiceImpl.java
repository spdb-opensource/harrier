package cn.spdb.harrier.api.service.develop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.mapper.DyJobArrangeMapper;

@Service
public class JobArrangeServiceImpl implements IJobArrangeService {

	@Autowired
	private DyJobArrangeMapper dyJobArrangeMapper;
	
	@Override
	public DyJobArrange loadById(Long id) {
		return dyJobArrangeMapper.selectByPrimaryKey(id).orElse(null);
	}
	
	@Override
	public Page<DyJobArrange> selectManyInfo(Page<DyJobArrange> page, String platform, String systems, String job, Integer version) {
		Page<DyJobArrange> selectManyInfo = dyJobArrangeMapper.selectManyInfo(page, platform, systems, job,version);
		return selectManyInfo;
	}

	@Override
	public DyJobArrange loadByJobName(String platform, String systems, String job) {
		return dyJobArrangeMapper.selectOne(platform, systems, job).orElse(null);
	}
	
	@Override
	public Page<DyJobArrange> findAllJobVersion(Page<DyJobArrange> page, String platform, String systems, String job) {
		return dyJobArrangeMapper.selectAllJobVersion(page, platform, systems, job);
	}

	@Override
	public int insertOneJobArrange(DyJobArrange dyJobArrange) {
		return dyJobArrangeMapper.insert(dyJobArrange);
	}

	@Override
	public Integer deleteJobById(Long id) {
		return dyJobArrangeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer updateJobArrange(DyJobArrange dyJobArrange) {
		return dyJobArrangeMapper.updateByPrimaryKey(dyJobArrange);
	}

	@Override
	public DyJobArrange loadMaxVersionJob(String platform, String systems, String job) {
		return dyJobArrangeMapper.selectLastJobArrange(platform, systems, job).orElse(null);
	}

	@Override
	public DyJobArrange loadByJobVersion(String platform, String systems, String job, Integer version) {
		return dyJobArrangeMapper.selectJobArrabgeByVersion(platform, systems, job, version).orElse(null);
	}

	@Override
	public Integer deleteJobByVersion(String job, Integer version) {
		return dyJobArrangeMapper.deleteJobByVersion(job, version);
	}

	@Override
	public List<Integer> findJobVersionCode(String platform, String systems, String job) {
		return dyJobArrangeMapper.findJobVersionCode(platform, systems, job);
	}
}
