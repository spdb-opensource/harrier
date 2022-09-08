package cn.spdb.harrier.server.worker.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.OSUtils;
import cn.spdb.harrier.common.utils.ThreadUtils;
import cn.spdb.harrier.server.entity.JobStepBean;

public class CommandTask extends AbstractTask {

	private Process process;

	public CommandTask(JobStepBean jobStepBean) {
		super(jobStepBean);
	}

	@Override
	public void handle() {
		try {
			buildProcessStart();
			parseProcessOutput();
			Integer processId = getProcessId(process);
			jobStepBean.setProcessId(processId);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			setExitStatusCode(process.exitValue());
			process.destroyForcibly();
		}
	}

	private void buildProcessStart() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (StringUtils.isNotBlank(jobStepBean.getWorkdir())) {
			File workDirFile = new File(jobStepBean.getWorkdir());
			if (workDirFile.exists()) {
				processBuilder.directory(workDirFile);
			}
		}
		processBuilder.redirectErrorStream(true);
		List<String> command = new ArrayList<String>();
//		if (OSUtils.isWindows()) {
//			command.add("cmd");
//			command.add("/c");
//			command.add(jobStepBean.getRunCmd());
//		} else 
		if (OSUtils.isLinux()) {
			command.add("sudo");
//			command.add("-c");
			command.add(jobStepBean.getRunCmd());
		} else {
			command.addAll(jobStepBean.getRunCmdList());
		}
		processBuilder.command(command);

		Map<String, String> env = jobStepBean.getEnvMap();
		processBuilder.environment().putAll(env);
		process = processBuilder.start();
	}

	private void parseProcessOutput() {
		ExecutorService getOutputLogService = ThreadUtils.newDaemonFixedThreadPool(2,
				CommandTask.class.getSimpleName() + "-" + "getOutputLogService");
		getOutputLogService.submit(() -> {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
				for (String line = reader.readLine(); line != null; logger.info(line), line = reader.readLine())
					;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		getOutputLogService.submit(() -> {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
				for (String line = reader.readLine(); line != null; logger.error(line), line = reader.readLine())
					;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private int getProcessId(Process process) {
		Long processId = 0L;
		try {
			Field f = process.getClass().getDeclaredField(Constants.PID);
			f.setAccessible(true);
			processId = f.getLong(process);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return processId.intValue();
	}

	@Override
	protected void subkill() {
		if (process == null) {
			return;
		}
		process.destroyForcibly();
	}
}
