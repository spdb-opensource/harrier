package cn.spdb.harrier.server.worker.cache;

import cn.spdb.harrier.server.entity.JobExecutionContext;
import cn.spdb.harrier.server.worker.exec.ExecuteRunable;

public interface TaskExecutionContextCacheManager {

	ExecuteRunable getByTaskInstanceId(Long taskInstanceId);

	void cacheTaskExecutionContext(ExecuteRunable jobExecutionContext);

	void removeByTaskInstanceId(Long taskInstanceId);

	boolean updateTaskExecutionContext(JobExecutionContext jobExecutionContext);

}