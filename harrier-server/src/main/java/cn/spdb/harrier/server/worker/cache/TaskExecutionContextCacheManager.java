package cn.spdb.harrier.server.worker.cache;

import cn.spdb.harrier.server.entity.JobExecutionContext;

public interface TaskExecutionContextCacheManager {

	/**
	 * get taskInstance by taskInstance id
	 *
	 * @param taskInstanceId taskInstanceId
	 * @return taskInstance
	 */
	JobExecutionContext getByTaskInstanceId(Integer taskInstanceId);

	/**
	 * cache taskInstance
	 *
	 * @param jobExecutionContext taskExecutionContext
	 */
	void cacheTaskExecutionContext(JobExecutionContext jobExecutionContext);

	/**
	 * remove taskInstance by taskInstanceId
	 *
	 * @param taskInstanceId taskInstanceId
	 */
	void removeByTaskInstanceId(Integer taskInstanceId);

	boolean updateTaskExecutionContext(JobExecutionContext jobExecutionContext);

}