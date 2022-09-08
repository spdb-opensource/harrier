package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.api.model.TreeJobNode;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobMenu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UdsJobService {
    /**
     *新增
     */
    int add(UdsJob record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJob record);

    /**
     *查看详情
     */
    UdsJob getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJob> listQuery();
    
	/**
	 * 作业管理—查询所有作业(分页)
	 */
	Page<UdsJobMenu> selectAll(Page<UdsJobMenu> page, String platform, String systems, String job, String last_status);

	/**
	 * 作业列表-查询作业详情	
	 */
	UdsJobMenu selectJobDetail(String platform,String systems,String job);
	
	/**
	 * 作业列表-修改作业详情	
	 */
	int updateJobDetail(UdsJobMenu udsJobMenu);
	
	/**
	 * 作业列表-查询上下游
	 * @param job_
	 * @param level
	 * @return
	 */
	List<TreeJobNode> listAllDownjobs(String job, Integer level);

	List<TreeJobNode> listAllUpJobs(String job, Integer level);
	
	Page<UdsJobMenu> listUpJobs(Page<UdsJobMenu> page, String job);
	/**
	 * 作业列表-invoke作业
	 */
	int invokeJob(String platform,String systems,String job,LocalDate jobdate,Integer multibatch);





}