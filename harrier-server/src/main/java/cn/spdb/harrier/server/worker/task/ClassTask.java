package cn.spdb.harrier.server.worker.task;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.dynamicjava.DynamicClassEngine;
import cn.spdb.harrier.common.utils.JSONUtils;
import cn.spdb.harrier.server.entity.JobStepBean;

public class ClassTask extends AbstractTask {

	private Class<?> clazz = null;

	public ClassTask(JobStepBean jobStepBean) {
		super(jobStepBean);
	}

	@Override
	public void init() {
		setExitStatusCode(901);
		logger.info("classetask start");
		if (jobStepBean.getStepPath().endsWith(".java")) {
			File file = new File(jobStepBean.getStepPath());
			if (!file.exists() || file.isDirectory()) {
				logger.error("job step : {} is exists", jobStepBean.getStepPath());
				return;
			}
			try {
				clazz = DynamicClassEngine.getInstance().loadFromJavaFile(file);
				logger.info("class:{}", clazz.toString());
			} catch (IOException e) {
				logger.error("task class load error", e);
			}
		} else if (jobStepBean.getStepPath().endsWith(".class")) {
			File file = new File(jobStepBean.getStepPath());
			if (!file.exists() || file.isDirectory()) {
				logger.error("job step : {} is exists", jobStepBean.getStepPath());
				return;
			}
			try {
				clazz = DynamicClassEngine.getInstance().loadClass(file);
				logger.info("class:{}", clazz.toString());
			} catch (IOException e) {
				logger.error("task class load error", e);
			}
		} else {
			try {
				clazz = Class.forName(jobStepBean.getStepPath());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		if (clazz == null) {
			logger.error("class is null, job step : {}", jobStepBean.getStepPath());
			return;
		}
	}

	@Override
	public void handle() {
		if (ObjectUtils.isEmpty(clazz)) {
			return;
		}
		try {
			Map<String, String> envMap = jobStepBean.getEnvMap();
			String envs = JSON.toJSONString(envMap);
			logger.info("job step env:{}", envs);
			Object object = JSON.parseObject(envs, clazz);

			String methodName = jobStepBean.getCmd();
			logger.info("method:{}", methodName);
			Method method = clazz.getMethod(methodName, jobStepBean.getParameterClass());
			if (ObjectUtils.isEmpty(method)) {
				logger.error("method is null,method name{}", methodName);
				return;
			}
			Object result = method.invoke(object, jobStepBean.getParameterObject());
			logger.info("result:{}", JSONUtils.toJsonString(result));
			setExitStatusCode(0);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("task class load error", e);
		}
	}

	@Override
	protected void subkill() {
		
	}

}
