
package cn.spdb.harrier.server.worker.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.worker.exec.ComplementRunable;
import cn.spdb.harrier.server.worker.exec.ExecuteRunable;

/**
 * TaskExecutionContextCache
 */
public class TaskExecutionContextCacheManagerImpl implements TaskExecutionContextCacheManager,ComplementContextCacheManager {

	/**
	 * taskInstance cache
	 */
	private Map<Long, ExecuteRunable> taskExecutionContextCache = new ConcurrentHashMap<>();
	private Map<Long, ComplementRunable> complementContextCache = new ConcurrentHashMap<>();

	/**
	 * get taskInstance by taskInstance id
	 *
	 * @param taskInstanceId taskInstanceId
	 * @return taskInstance
	 */
	@Override
	public ExecuteRunable getByTaskInstanceId(Long taskInstanceId) {
		return taskExecutionContextCache.get(taskInstanceId);
	}

	/**
	 * cache taskInstance
	 *
	 * @param jobExecutionContext taskExecutionContext
	 */
	@Override
	public void cacheTaskExecutionContext(ExecuteRunable executeRunable) {
		taskExecutionContextCache.put(executeRunable.getJobExecutionContext().getTaskInstanceId(), executeRunable);
	}

	/**
	 * remove taskInstance by taskInstanceId
	 *
	 * @param taskInstanceId taskInstanceId
	 */
	@Override
	public void removeByTaskInstanceId(Long taskInstanceId) {
		taskExecutionContextCache.remove(taskInstanceId);
	}

	@Override
	public boolean updateTaskExecutionContext(JobExecutionContext jobExecutionContext) {
		taskExecutionContextCache.computeIfPresent(jobExecutionContext.getTaskInstanceId(), (k, v) -> {
			v.setJobExecutionContext(jobExecutionContext);
			return v;
		});
		return taskExecutionContextCache.containsKey(jobExecutionContext.getTaskInstanceId());
	}

	@Override
	public ComplementRunable getByComplementId(Long taskInstanceId) {
		return complementContextCache.get(taskInstanceId);
	}

	@Override
	public void cacheComplementContext(ComplementRunable comRunable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeByComplementId(Long taskInstanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateComplementContext(ComplementRunable jobExecutionContext) {
		// TODO Auto-generated method stub
		return false;
	}


}
