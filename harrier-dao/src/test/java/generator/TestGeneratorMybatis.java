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
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class TestGeneratorMybatis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;

			Configuration config = new Configuration();

			Context context = new Context(ModelType.CONDITIONAL);
			context.setId("infoguardian");
			context.setTargetRuntime("MyBatis3DynamicSql");

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
			
//			
//			PluginConfiguration pluginConfiguration=new PluginConfiguration();
////			pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
////			context.addPluginConfiguration(pluginConfiguration);
//			
//			pluginConfiguration=new PluginConfiguration();
//			pluginConfiguration.setConfigurationType("generator.ServicePlugin");
//			pluginConfiguration.addProperty("serviceTargetProject", "src/test/java");                 
//			pluginConfiguration.addProperty("serviceImplTargetProject", "src/test/java");             
//			pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service.alarm");         
//			pluginConfiguration.addProperty("serviceImplPackage", "cn.spdb.harrier.api.service.alarm.impl");
//			context.addPluginConfiguration(pluginConfiguration);
//			
//			pluginConfiguration=new PluginConfiguration();
//			pluginConfiguration.setConfigurationType("generator.ControllerPlugin");
//			pluginConfiguration.addProperty("controllerTargetProject", "src/test/java");
//			pluginConfiguration.addProperty("controllerPackage", "cn.spdb.harrier.api.controller.alarm");
//			pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service.alarm");
//			context.addPluginConfiguration(pluginConfiguration);

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

			TableConfiguration t1 = new TableConfiguration(context);
			t1.setTableName("m_alarm%");
			context.addTableConfiguration(t1);

//			TableConfiguration t2 = new TableConfiguration(context);
//			t2.setTableName("m_user%");
//			context.addTableConfiguration(t2);
//
//			TableConfiguration t3 = new TableConfiguration(context);
//			t3.setTableName("m_menu%");
//			context.addTableConfiguration(t3);
//			
			config.addContext(context);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);

			for (String str : warnings) {
				System.out.println(str);
			}
		} catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
