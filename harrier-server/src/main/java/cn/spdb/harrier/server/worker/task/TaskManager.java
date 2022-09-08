
package cn.spdb.harrier.server.worker.task;

import cn.spdb.harrier.server.entity.JobStepBean;

/**
 * task manager
 */
public class TaskManager {

	
	public static AbstractTask newTask(JobStepBean jobStepBean) throws IllegalArgumentException {
		String taskType = jobStepBean.getStepType();
		if (taskType == null) {
			throw new IllegalArgumentException("task type is null");
		}
		switch (taskType) {
		case "SQL":
			return new SqlTask(jobStepBean);
		case "JAVA":
			return new ClassTask(jobStepBean);
		case "HTTP_POST":
		case "HTTP_GET":
		case "HTTP_DELETE":
		case "HTTP_PUT":
		case "HTTP_HEAD":
			return new HttpTask(jobStepBean);
		case "CMD":
		case "SHELL":
		case "PYTHON":
		case "PYTHON3":
		case "PREL":
			return new CommandTask(jobStepBean);
		default:
//                logger.error("not support task type: {}", taskType);
			throw new IllegalArgumentException("not support task type");
		}
	}
}
