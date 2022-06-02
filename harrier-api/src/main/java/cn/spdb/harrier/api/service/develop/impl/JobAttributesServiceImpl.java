package cn.spdb.harrier.api.service.develop.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.develop.IJobAttributesService;
import cn.spdb.harrier.dao.entity.DyJobAttributes;
import cn.spdb.harrier.dao.mapper.DyJobArrangeMapper;
import cn.spdb.harrier.dao.mapper.DyJobAttributesMapper;
import cn.spdb.harrier.dao.mapper.UdsJobConfigMapper;

@Service
public class JobAttributesServiceImpl implements IJobAttributesService {

	@Autowired
	private DyJobAttributesMapper dyJobAttributesMapper;
	@Autowired
	private DyJobArrangeMapper dyJobArrangeMapper;
	@Autowired
	private UdsJobConfigMapper udsJobConfigMapper;
	
	@Override
	public DyJobAttributes loadById(Long id) {
		Optional<DyJobAttributes> optional = dyJobAttributesMapper.selectByPrimaryKey(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public DyJobAttributes loadByJobName(String platform, String systems, String job) {
		return dyJobAttributesMapper.selectByJobName(platform, systems, job).orElse(null);
	}

	@Override
	public Integer createJob(DyJobAttributes dyJobAttributes) {
		return dyJobAttributesMapper.insert(dyJobAttributes);
	}

	@Override
	public Integer updateJobById(Long id) {
		return null;
	}

	@Override
	public Integer deleteJobById(Long id) {
		return dyJobAttributesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Page<DyJobAttributes> selectManyInfo(Page<DyJobAttributes> page) {
		Page<DyJobAttributes> selectManyInfo = dyJobAttributesMapper.selectManyInfo(page);
		return selectManyInfo;
	}

	@Override
	public Integer updateJobAttributes(DyJobAttributes dyJobAttributes) {
		return dyJobAttributesMapper.updateByPrimaryKey(dyJobAttributes);
	}
	
	@Override
	public Set<String> loadJob(String platform, String systems, String job) {
		// 存放所有可用的依赖作业
		HashSet<String> jobSet = new HashSet<String>();
		
		// 从编排表中获取暂未上线的新增作业
		Set<String> arrangeNewJobSet = dyJobArrangeMapper.newJobSearch(platform, systems, job);
		jobSet.addAll(arrangeNewJobSet);
		
		// 从已投产的作业表中获取可用的作业
		Set<String> udsJobSet =  udsJobConfigMapper.jobSearch(platform, systems, job);
		jobSet.addAll(udsJobSet);
		return jobSet;
	}
}
