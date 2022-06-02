package cn.spdb.harrier.server.worker.task;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.worker.exec.UdsSqlScriptRunner;

public class SqlTask extends AbstractTask {

	private Properties properties = new Properties();

	private String KEY = "sql.delimiter";
	private String SQL_DELIMITER = ";";

	public SqlTask(JobStepBean jobStepBean) {
		super(jobStepBean);
	}

	@Override
	public void init() {
		properties.clear();
		logger.info("SqlTask start");
		properties.putAll(jobStepBean.getEnvMap());
		SQL_DELIMITER = properties.getProperty(KEY);
		if (StringUtils.isEmpty(SQL_DELIMITER)) {
			SQL_DELIMITER = ";";
		}
	}

	@Override
	public void handle() {
		DataSource datasource = null;
		try {
			datasource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("datasource create is error ", e1);
		}
		if (datasource == null) {
			logger.error("datasource  is null ");
			return;
		}
		try (Connection connection = datasource.getConnection();) {
			connection.setAutoCommit(false);
			UdsSqlScriptRunner scriptRunner = new UdsSqlScriptRunner(connection, getLogger());
			scriptRunner.setAutoCommit(false);
			scriptRunner.setStopOnError(true);
			scriptRunner.setSendFullScript(true);
			scriptRunner.setDelimiter(SQL_DELIMITER);
			scriptRunner.setFullLineDelimiter(false);
			Resources.setCharset(StandardCharsets.UTF_8);
			logger.info("sql script start");
			if (StringUtils.isEmpty(jobStepBean.getStepPath())) {
				String[] strAwarry = jobStepBean.getCmd().split(Symbol.WEN_HAO);
				List<String> list = jobStepBean.getParameterList();
				StringBuffer sbuffer = new StringBuffer();
				for (int i = 0; i < strAwarry.length; i++) {
					sbuffer.append(strAwarry[i]);
					String para = list.get(i);
					if (StringUtils.isNotEmpty(para)) {
						sbuffer.append(Symbol.DAN_YING_HAO).append(para).append(Symbol.DAN_YING_HAO);
					}
				}
				try (StringReader reader = new StringReader(sbuffer.toString());) {
					scriptRunner.runScript(reader);
				}
			} else {
				File file = new File(jobStepBean.getStepPath());
				if (file.exists()) {
					try (Reader reader = new FileReader(file);) {
						scriptRunner.runScript(reader);
					}
				}
			}
			logger.info("sql script end");
			setExitStatusCode(0);
		} catch (Exception e) {
			logger.error("run  is error ", e);
			setExitStatusCode(902);
		}
	}

}
