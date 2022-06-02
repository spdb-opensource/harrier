package generator;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.TableConfiguration;

public class CreateServiceAndControllerByGenerate extends AbstractGeneratorMybatis {

	public static void main(String[] args) {
		CreateServiceAndControllerByGenerate generate=new CreateServiceAndControllerByGenerate();
		generate.overwrite=false;
		generate.start();
	}
	
	@Override
	protected void create(Context context) {
		creatJavaDaoAndSql(context);
		createJavaServiceAndController(context);
	}

	@Override
	protected void createJavaServiceAndController(Context context) {
		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
		context.addPluginConfiguration(pluginConfiguration);

		pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("generator.ServicePlugin");
		pluginConfiguration.addProperty("serviceTargetProject", "src/test/java");
		pluginConfiguration.addProperty("serviceImplTargetProject", "src/test/java");
		pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service.job");
		pluginConfiguration.addProperty("serviceImplPackage", "cn.spdb.harrier.api.service.job.impl");
		context.addPluginConfiguration(pluginConfiguration);

		pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("generator.ControllerPlugin");
		pluginConfiguration.addProperty("controllerTargetProject", "src/test/java");
		pluginConfiguration.addProperty("controllerPackage", "cn.spdb.harrier.api.controller.job");
		pluginConfiguration.addProperty("servicePackage", "cn.spdb.harrier.api.service.job");
		context.addPluginConfiguration(pluginConfiguration);

	}

	@Override
	protected void addTable(Context context) {
		TableConfiguration t1 = new TableConfiguration(context);
		t1.setTableName("uds_job%");
		context.addTableConfiguration(t1);

	}
}
