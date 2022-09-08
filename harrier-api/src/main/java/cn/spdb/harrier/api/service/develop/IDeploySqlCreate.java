package cn.spdb.harrier.api.service.develop;

import java.util.List;

import cn.spdb.harrier.api.model.JobYamlObject;

public interface IDeploySqlCreate {

	/**
	 * 新增作业SQL
	 * @param jobConfig
	 * @return
	 */
	List<String> newJobSql(JobYamlObject jobConfig);

	/**
	 * 变更作业SQL
	 * @param jobConfig
	 * @return
	 */
	List<String> updateJobSql(JobYamlObject jobConfig);

	/**
	 * 下线作业SQL
	 * @param jobConfig
	 * @return
	 */
	List<String> deleteJobSql(JobYamlObject jobConfig);

}