package cn.com.spdb.uds.background.socket.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.core.bean.JobStatus;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.dao.UdsJobBaseDao;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.Symbol;

//TTA_AAA_TJOB_0_T
public class KillJobScript implements InterfaceConsoleCommand {

	@Override
	public String hanlder(String param, PrintWriter pw) {
		String job = param.trim();
		UdsJobBaseDao baseDao = DBManager.getInstance().getDao(UdsJobBaseDao.class);
		UdsJobBean udsjobbean = baseDao.getUdsJobBeanByJob(job);
		if (udsjobbean == null) {
			return "job is null please check job name";
		}
		File file = new File("./bin/uds_sys_kill_script.sh");
		if (!file.exists()) {
			return "kill script file is not exist path:./bin/uds_sys_kill_script.sh";
		}
		StringBuffer jobSignbuffer = new StringBuffer();
		jobSignbuffer.append(udsjobbean.getBatch());
		jobSignbuffer.append(Symbol.AT);
		jobSignbuffer.append(udsjobbean.getJob());
		jobSignbuffer.append(Symbol.XIA_HUA_XIAN);
		jobSignbuffer.append(udsjobbean.getJob_date());
		jobSignbuffer.append(".dir");

		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add("sh");
		cmd.add("./bin/uds_sys_kill_script.sh");
		cmd.add(jobSignbuffer.toString());
		// 载入命令
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		// 配置环境变量
		Map<String, String> env = processBuilder.environment();
		env.put("AUTO_HOME", UdsConstant.AUTO_HOME);
		// 设置错误日志和标准日志合并
		processBuilder.redirectErrorStream(true);
		// 输出日志文件夹
		StringBuffer logDirBuffer = new StringBuffer();
		logDirBuffer.append(UdsConstant.AUTO_JOB_LOG_DIR_PATH);
		logDirBuffer.append(File.separator);
		logDirBuffer.append("UDS");
		logDirBuffer.append(File.separator);
		logDirBuffer.append("SYS");
		logDirBuffer.append(File.separator);
		logDirBuffer.append(DateUtils.getDateTime(DateUtils.PATTERN_YYYYMMDD_CONS));
		File logDir = new File(logDirBuffer.toString());
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		// 日志名字
		String logPath = logDirBuffer.toString() + File.separator + "uds_sys_kill_script" + Symbol.XIA_HUA_XIAN
				+ DateUtils.getDateTime(DateUtils.PATTERN_YYYYMMDD_CONS) + ".log";
		Process processWork = null;
		int code = 128;
		BufferedReader jobStepOut = null;
		PrintStream logFileInput = null;
		try {
			UdsLogger.logEvent(LogEvent.BACK_CONSOLE, "kill job", job, logPath);
			processWork = processBuilder.start();
			/** 将脚本运行的信息记录文件 */
			jobStepOut = new BufferedReader(
					new InputStreamReader(processWork.getInputStream(), "utf-8"));
			logFileInput = new PrintStream(new FileOutputStream(new File(logPath)), true);
			String line;
			while ((line = jobStepOut.readLine()) != null) {
				logFileInput.println(line);
				logFileInput.flush();
				UdsLogger.logEvent(LogEvent.BACK_CONSOLE, "kill log", job, line);
			}
			// 线程阻塞，等待外部返回执行结果
			processWork.waitFor();
			// 执行结束退出获取返回值
			code = processWork.exitValue();
		} catch (IOException | InterruptedException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			UdsLogger.logEvent(LogEvent.BACK_CONSOLE, "kill job", job, e.getMessage());
		} finally {
			if(jobStepOut != null) {
				try {
					jobStepOut.close();
				} catch (IOException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
			if(logFileInput != null) {
				logFileInput.close();
			}
			processWork.destroy();
		}
		if (code != 0) {
			return "kill error please log";
		}
		return HttpResultCode.SUCCESS;
	}
}
