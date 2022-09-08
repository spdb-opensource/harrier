package cn.spdb.harrier.api.service.develop;

import java.util.Set;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.dao.entity.DyJobAttributes;

public interface IJobAttributesService {
	/**
	 * 查询表中的所有信息，分页展示
	 * @param curentPageNo
	 * @param pageSize
	 * @return
	 */
	Page<DyJobAttributes> selectManyInfo(Page<DyJobAttributes> page);
	
	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	DyJobAttributes loadById(Long id);
	
	/**
	 * 通过作业名查询
	 * @param job 作业名
	 * @return
	 */
	DyJobAttributes loadByJobName(String platform, String systems, String job);
	
	/**
	 * 查询系统中的作业，用于配置作业依赖
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	Set<String> loadJob(String platform, String systems, String job);
	
	/**
	 * 新增作业
	 * @return
	 */
	Integer createJob(DyJobAttributes dyJobAttributes);
	
	/**
	 * 根据主键id更新作业属性
	 * @param id
	 * @return
	 */
	Integer updateJobById(Long id);
	
	/**
	 * 根据主键id删除作业
	 * @param id
	 * @return
	 */
	Integer deleteJobById(Long id);

	Integer updateJobAttributes(DyJobAttributes dyJobAttributes);
}
