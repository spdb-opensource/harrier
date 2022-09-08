package cn.spdb.harrier.server.worker.task;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.worker.exec.UdsSqlScriptRunner;

public class SqlTask extends AbstractTask {

	private Properties properties = new Properties();

	private static String SQL_DELIMITER = "sql.delimiter";
	private static String SQL_SEND_FULL_SCRIPT = "sql.send.full.script";

	private Boolean sendFull;
	private String delimiter;

	private Connection con;

	public SqlTask(JobStepBean jobStepBean) {
		super(jobStepBean);
	}

	@Override
	public void init() {
		setExitStatusCode(902);
		properties.clear();
		logger.info("SqlTask start");
		properties.putAll(jobStepBean.getEnvMap());
		delimiter = properties.getProperty(SQL_DELIMITER);
		if (StringUtils.isEmpty(delimiter)) {
			delimiter = Symbol.FEN_HAO;
		}
		String sendFullStr = properties.getProperty(SQL_SEND_FULL_SCRIPT);
		if (StringUtils.isEmpty(sendFullStr)) {
			sendFull = true;
		}else {
			sendFull=Boolean.valueOf(sendFullStr);
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
		try (Connection connection = this.con = datasource.getConnection()) {
			connection.setAutoCommit(false);
			UdsSqlScriptRunner scriptRunner = new UdsSqlScriptRunner(connection, getLogger());
			scriptRunner.setAutoCommit(false);
			scriptRunner.setStopOnError(true);
			scriptRunner.setSendFullScript(sendFull);
			scriptRunner.setDelimiter(delimiter);
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
		}
	}

	@Override
	protected void subkill() {
		if (ObjectUtils.isNotEmpty(this.con)) {
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("kill effect", e);
			}
		}
	}

}
