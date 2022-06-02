

package cn.spdb.harrier.server.worker.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.spdb.harrier.server.entity.JobExecutionContext;

/**
 * TaskExecutionContextCache
 */
public class TaskExecutionContextCacheManagerImpl implements TaskExecutionContextCacheManager {

	/**
	 * taskInstance cache
	 */
	private Map<Long, JobExecutionContext> taskExecutionContextCache = new ConcurrentHashMap<>();

	/**
	 * get taskInstance by taskInstance id
	 *
	 * @param taskInstanceId taskInstanceId
	 * @return taskInstance
	 */
	@Override
	public JobExecutionContext getByTaskInstanceId(Integer taskInstanceId) {
		return taskExecutionContextCache.get(taskInstanceId);
	}

	/**
	 * cache taskInstance
	 *
	 * @param jobExecutionContext taskExecutionContext
	 */
	@Override
	public void cacheTaskExecutionContext(JobExecutionContext jobExecutionContext) {
		taskExecutionContextCache.put(jobExecutionContext.getTaskInstanceId(), jobExecutionContext);
	}

	/**
	 * remove taskInstance by taskInstanceId
	 *
	 * @param taskInstanceId taskInstanceId
	 */
	@Override
	public void removeByTaskInstanceId(Integer taskInstanceId) {
		taskExecutionContextCache.remove(taskInstanceId);
	}

	@Override
	public boolean updateTaskExecutionContext(JobExecutionContext jobExecutionContext) {
		taskExecutionContextCache.computeIfPresent(jobExecutionContext.getTaskInstanceId(),
				(k, v) -> jobExecutionContext);
		return taskExecutionContextCache.containsKey(jobExecutionContext.getTaskInstanceId());
	}
}
