package cn.spdb.harrier.api.service.develop;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.dao.entity.DyJobArrange;

public interface IJobArrangeService {
	
	/**
	 * 根据id查询作业编排信息
	 * @param id
	 * @return
	 */
	DyJobArrange loadById(Long id);
	
	/**
	 * 根据作业名查询作业编排信息
	 * @param job
	 * @return
	 */
	DyJobArrange loadByJobName(String platform, String systems, String job);
	
	/**
	 * 获取当前最新版本的作业编排信息
	 * @return
	 */
	DyJobArrange loadMaxVersionJob(String platform, String systems, String job);
	
	/**
	 * 根据作业的版本号获取作业编排信息
	 * @param job
	 * @param version
	 * @return
	 */
	DyJobArrange loadByJobVersion(String platform, String systems, String job, Integer version);
	
	/**
	 * 新增一条作业编排信息
	 * @param dyJobArrange
	 * @return
	 */
	int insertOneJobArrange(DyJobArrange dyJobArrange);

	/**
	 * 分页查询作业编排表
	 * @param page
	 * @return
	 */
	Page<DyJobArrange> selectManyInfo(Page<DyJobArrange> page, String platform, String systems, String job, Integer version);

	/**
	 * 根据Id删除作业编排信息
	 * @param id
	 * @return
	 */
	Integer deleteJobById(Long id);
	
	/**
	 * 根据版本号删除作业编排信息
	 * @param job
	 * @param version
	 * @return
	 */
	Integer deleteJobByVersion(String job, Integer version);

	/**
	 * 更新作业编排信息
	 * @param dyJobArrange
	 * @return
	 */
	Integer updateJobArrange(DyJobArrange dyJobArrange);

	/**
	 * 获取某个作业所有的版本
	 * @param page
	 * @param job
	 * @return
	 */
	Page<DyJobArrange> findAllJobVersion(Page<DyJobArrange> page, String platform, String systems, String job);

	/**
	 * 获取某个作业的所有版本号信息
	 * @param job
	 * @return
	 */
	List<Integer> findJobVersionCode(String platform, String systems, String job);


}
