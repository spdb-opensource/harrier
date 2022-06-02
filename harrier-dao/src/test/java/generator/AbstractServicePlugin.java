package generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractServicePlugin extends PluginAdapter {

	protected String foreignKeysFormat = "([A-Za-z_][A-Za-z0-9_]{0,})(,[A-Za-z_][A-Za-z0-9_]{0,}){0,}";

	protected String uniqueKeysFormat = "(([A-Za-z_][A-Za-z0-9_]{0,})(,[A-Za-z_][A-Za-z0-9_]{0,}){0,})(;([A-Za-z_][A-Za-z0-9_]{0,})(,[A-Za-z_][A-Za-z0-9_]{0,}){0,}){0,}";

	protected ServicePropertyConfig servicePropertyConfig;

	public AbstractServicePlugin(Properties properties) {
		super.setProperties(properties);
	}

	public AbstractServicePlugin() {

	}

	@Override
	public boolean validate(List<String> list) {
		String serviceTargetProject = properties.getProperty("serviceTargetProject");
		String serviceImplTargetProject = properties.getProperty("serviceImplTargetProject");

		String servicePackage = properties.getProperty("servicePackage");
		String serviceImplPackage = properties.getProperty("serviceImplPackage");

		if (serviceTargetProject == null) {
			throw new RuntimeException("serviceTargetProject为空");
		}
		if (serviceImplTargetProject == null) {
			throw new RuntimeException("serviceImplTargetProject为空");
		}

		if (servicePackage == null) {
			throw new RuntimeException("servicePackage为空");
		}
		if (serviceImplPackage == null) {
			throw new RuntimeException("serviceImplPackage为空");
		}

		String servicePrefix = properties.getProperty("servicePrefix");
		servicePrefix = servicePrefix == null ? "" : servicePrefix;
		String serviceSuffix = properties.getProperty("serviceSuffix");
		serviceSuffix = serviceSuffix == null ? "Service" : serviceSuffix;

		String serviceImplPrefix = properties.getProperty("serviceImplPrefix");
		serviceImplPrefix = serviceImplPrefix == null ? "" : serviceImplPrefix;
		String serviceImplSuffix = properties.getProperty("serviceImplSuffix");
		serviceImplSuffix = serviceImplSuffix == null ? "ServiceImpl" : serviceImplSuffix;

		String enableService = properties.getProperty("enableService");
		enableService = enableService == null ? "true" : enableService;
		String enableServiceImpl = properties.getProperty("enableServiceImpl");
		enableServiceImpl = enableServiceImpl == null ? "true" : enableServiceImpl;

		servicePropertyConfig = new ServicePropertyConfig();

		servicePropertyConfig.setServiceTargetProject(serviceTargetProject);
		servicePropertyConfig.setServiceImplTargetProject(serviceImplTargetProject);

		servicePropertyConfig.setServicePackage(servicePackage);
		servicePropertyConfig.setServiceImplPackage(serviceImplPackage);

		servicePropertyConfig.setServicePrefix(servicePrefix);
		servicePropertyConfig.setServiceSuffix(serviceSuffix);

		servicePropertyConfig.setServiceImplPrefix(serviceImplPrefix);
		servicePropertyConfig.setServiceImplSuffix(serviceImplSuffix);

		servicePropertyConfig.setEnableService("true".equals(enableService));
		servicePropertyConfig.setEnableServiceImpl("true".equals(enableServiceImpl));

		return true;
	}

	public abstract void addServiceElements(Interface interfaze, IntrospectedTable introspectedTable);

	public abstract void addServiceImplElements(TopLevelClass interfaze, IntrospectedTable introspectedTable);

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		List<GeneratedJavaFile> answer = new ArrayList<>();

		String baseRecordType = introspectedTable.getBaseRecordType();
		String modelName = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1);
		String servicePackage = servicePropertyConfig.getServicePackage();
		String servicePrefix = servicePropertyConfig.getServicePrefix();
		String serviceSuffix = servicePropertyConfig.getServiceSuffix();

		String serviceImplPackage = servicePropertyConfig.getServiceImplPackage();
		String serviceImplPrefix = servicePropertyConfig.getServiceImplPrefix();
		String serviceImplSuffix = servicePropertyConfig.getServiceImplSuffix();

		String serviceClassName = servicePackage + "." + servicePrefix + modelName + serviceSuffix;
		String serviceImplClassName = serviceImplPackage + "." + serviceImplPrefix + modelName + serviceImplSuffix;

		servicePropertyConfig.setServiceClassName(serviceClassName);
		servicePropertyConfig.setServiceImplClassName(serviceImplClassName);

		if (servicePropertyConfig.getEnableService()) {
			answer.add(generateServiceInterface(introspectedTable));
		}
		if (servicePropertyConfig.getEnableServiceImpl()) {
			answer.add(generateServiceImpl(introspectedTable));
		}
		return answer;
	}

	// 生成service接口
	private GeneratedJavaFile generateServiceInterface(IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType service = new FullyQualifiedJavaType(servicePropertyConfig.getServiceClassName());
		Interface serviceInterface = new Interface(service);
		serviceInterface.setVisibility(JavaVisibility.PUBLIC);
		addServiceElements(serviceInterface, introspectedTable);
		return new GeneratedJavaFile(serviceInterface, servicePropertyConfig.getServiceTargetProject(),
				context.getJavaFormatter());
	}

	// 生成serviceImpl实现类
	private GeneratedJavaFile generateServiceImpl(IntrospectedTable introspectedTable) {

		String mapperFieldType = introspectedTable.getMyBatis3JavaMapperType();
		FullyQualifiedJavaType serviceJavaType = new FullyQualifiedJavaType(
				servicePropertyConfig.getServiceClassName());
		FullyQualifiedJavaType serviceImplJavaType = new FullyQualifiedJavaType(
				servicePropertyConfig.getServiceImplClassName());

		TopLevelClass clazz = new TopLevelClass(serviceImplJavaType);

		clazz.addImportedType(new FullyQualifiedJavaType(mapperFieldType));
		clazz.addImportedType(serviceJavaType);
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));

		// 描述类的作用域修饰符
		clazz.setVisibility(JavaVisibility.PUBLIC);

		// 添加实现类
		clazz.addSuperInterface(serviceJavaType);

		clazz.addAnnotation("@Service");

		String mapperFieldName = "mapper";

		// 描述类的成员属性
		Field daoField = new Field(mapperFieldName, new FullyQualifiedJavaType(mapperFieldType));

		// 描述成员属性修饰符
		daoField.setVisibility(JavaVisibility.PRIVATE);
		// 描述成员属性 的注解
		daoField.addAnnotation("@Autowired");
		clazz.addField(daoField);

		addServiceImplElements(clazz, introspectedTable);
		return new GeneratedJavaFile(clazz, servicePropertyConfig.getServiceImplTargetProject(),
				context.getJavaFormatter());

	}

	private static class ServicePropertyConfig {

		// 项目目录，一般为 src/main/java
		private String serviceTargetProject;

		// 项目目录，一般为 src/main/java
		private String serviceImplTargetProject;

		// service包名
		private String servicePackage;

		// serviceImpl包名
		private String serviceImplPackage;

		// service接口名前缀
		private String servicePrefix;
		// service接口名后缀
		private String serviceSuffix;

		// serviceImpl接口名前缀
		private String serviceImplPrefix;
		// serviceImpl接口名后缀
		private String serviceImplSuffix;

		private String serviceClassName;

		private String serviceImplClassName;

		private Boolean enableService;

		private Boolean enableServiceImpl;

		public ServicePropertyConfig() {
		}

		public String getServiceTargetProject() {
			return serviceTargetProject;
		}

		public void setServiceTargetProject(String serviceTargetProject) {
			this.serviceTargetProject = serviceTargetProject;
		}

		public String getServiceImplTargetProject() {
			return serviceImplTargetProject;
		}

		public void setServiceImplTargetProject(String serviceImplTargetProject) {
			this.serviceImplTargetProject = serviceImplTargetProject;
		}

		public String getServicePackage() {
			return servicePackage;
		}

		public void setServicePackage(String servicePackage) {
			this.servicePackage = servicePackage;
		}

		public String getServiceImplPackage() {
			return serviceImplPackage;
		}

		public void setServiceImplPackage(String serviceImplPackage) {
			this.serviceImplPackage = serviceImplPackage;
		}

		public String getServicePrefix() {
			return servicePrefix;
		}

		public void setServicePrefix(String servicePrefix) {
			this.servicePrefix = servicePrefix;
		}

		public String getServiceSuffix() {
			return serviceSuffix;
		}

		public void setServiceSuffix(String serviceSuffix) {
			this.serviceSuffix = serviceSuffix;
		}

		public String getServiceImplPrefix() {
			return serviceImplPrefix;
		}

		public void setServiceImplPrefix(String serviceImplPrefix) {
			this.serviceImplPrefix = serviceImplPrefix;
		}

		public String getServiceImplSuffix() {
			return serviceImplSuffix;
		}

		public void setServiceImplSuffix(String serviceImplSuffix) {
			this.serviceImplSuffix = serviceImplSuffix;
		}

		public String getServiceClassName() {
			return serviceClassName;
		}

		public void setServiceClassName(String serviceClassName) {
			this.serviceClassName = serviceClassName;
		}

		public String getServiceImplClassName() {
			return serviceImplClassName;
		}

		public void setServiceImplClassName(String serviceImplClassName) {
			this.serviceImplClassName = serviceImplClassName;
		}

		public Boolean getEnableService() {
			return enableService;
		}

		public void setEnableService(Boolean enableService) {
			this.enableService = enableService;
		}

		public Boolean getEnableServiceImpl() {
			return enableServiceImpl;
		}

		public void setEnableServiceImpl(Boolean enableServiceImpl) {
			this.enableServiceImpl = enableServiceImpl;
		}
	}

}
