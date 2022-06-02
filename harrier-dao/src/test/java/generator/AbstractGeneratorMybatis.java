package generator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

public abstract class AbstractGeneratorMybatis {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://10.137.104.128:33006/harrier?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
	String user = "root";
	String password = "Harrier@2021";
	boolean overwrite = false;
	
	public void start() {
		try {
			run(driver, url, user, password);
		} catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	protected void run(String driver, String url, String user, String password)
			throws SQLException, IOException, InterruptedException, InvalidConfigurationException {
		List<String> warnings = new ArrayList<String>();
		

		Configuration config = new Configuration();

		Context context = new Context(ModelType.CONDITIONAL);
		context.setId("infoguardian");
		context.setTargetRuntime("MyBatis3DynamicSql");

//		context.setTargetRuntime("MyBatis3Simple");

		JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
		javaTypeResolverConfiguration.addProperty("useJSR310Types", "true");
		context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setDriverClass("com.mysql.cj.jdbc.Driver");
		jdbcConnectionConfiguration.setConnectionURL(
				"jdbc:mysql://10.137.104.128:33006/harrier?autoReconnect=true&useUnicode=true&characterEncoding=utf-8");
		jdbcConnectionConfiguration.setUserId("root");
		jdbcConnectionConfiguration.setPassword("Harrier@2021");
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
		create(context);
		addTable(context);
		config.addContext(context);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

		for (String str : warnings) {
			System.out.println(str);
		}
	}

	protected abstract void addTable(Context context);

	protected abstract void create(Context context);

	protected void creatJavaDaoAndSql(Context context) {
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetPackage("cn.spdb.harrier.dao.entity");
		javaModelGeneratorConfiguration.setTargetProject("src/main/java");
		javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetPackage("cn.spdb.harrier.dao.mapper");
		javaClientGeneratorConfiguration.setTargetProject("src/main/java");
		javaClientGeneratorConfiguration.setConfigurationType("ANNOTATEDMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

	}

	protected void createJavaServiceAndController(Context context) {
		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
		context.addPluginConfiguration(pluginConfiguration);

		pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("generator.ServicePlugin");
		pluginConfiguration.addProperty("serviceTargetProject", "src/main/java");
		pluginConfiguration.addProperty("serviceImplTargetProject", "src/main/java");
		pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service");
		pluginConfiguration.addProperty("serviceImplPackage", "cn.spdb.harrier.api.service.impl");
		context.addPluginConfiguration(pluginConfiguration);

		pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("generator.ControllerPlugin");
		pluginConfiguration.addProperty("controllerTargetProject", "src/main/java");
		pluginConfiguration.addProperty("controllerPackage", "cn.spdb.harrier.api.controller");
		pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service");
		context.addPluginConfiguration(pluginConfiguration);

	}
}
