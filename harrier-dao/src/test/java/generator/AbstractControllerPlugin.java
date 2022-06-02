package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;


public abstract class AbstractControllerPlugin  extends PluginAdapter {

    protected ControllerPropertyConfig controllerPropertyConfig;

    public AbstractControllerPlugin(Properties properties) {
    	super.setProperties(properties);
    }
    
    public AbstractControllerPlugin() {
		
	}

	@Override
    public boolean validate(List<String> warnings) {
        String controllerTargetProject = properties.getProperty("controllerTargetProject");
        String controllerPackage = properties.getProperty("controllerPackage");

        String servicePackage = properties.getProperty("servicePackage");

        if (controllerTargetProject == null){
            throw new RuntimeException("controllerTargetProject为空");
        }
        if (controllerPackage == null){
            throw new RuntimeException("controllerPackage为空");
        }
        if (servicePackage == null){
            throw new RuntimeException("servicePackage为空");
        }

        String controllerPrefix = properties.getProperty("controllerPrefix");
        controllerPrefix = controllerPrefix == null ? "" : controllerPrefix;
        String controllerSuffix = properties.getProperty("controllerSuffix");
        controllerSuffix = controllerSuffix == null ? "Controller" : controllerSuffix;

        String enableController = properties.getProperty("enableController");
        enableController = enableController == null ? "true" : enableController;

        String servicePrefix = properties.getProperty("servicePrefix");
        servicePrefix = servicePrefix == null ? "" : servicePrefix;
        String serviceSuffix = properties.getProperty("serviceSuffix");
        serviceSuffix = serviceSuffix == null ? "Service" : serviceSuffix;

        controllerPropertyConfig = new ControllerPropertyConfig();
        controllerPropertyConfig.setControllerTargetProject(controllerTargetProject);
        controllerPropertyConfig.setControllerPackage(controllerPackage);
        controllerPropertyConfig.setControllerPrefix(controllerPrefix);
        controllerPropertyConfig.setControllerSuffix(controllerSuffix);
        controllerPropertyConfig.setEnableController("true".equals(enableController));

        controllerPropertyConfig.setServicePackage(servicePackage);
        controllerPropertyConfig.setServicePrefix(servicePrefix);
        controllerPropertyConfig.setServiceSuffix(serviceSuffix);
        return true;
    }

    public abstract void addControllerElements(TopLevelClass interfaze, IntrospectedTable introspectedTable);


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> answer = new ArrayList<>();
        String baseRecordType = introspectedTable.getBaseRecordType();
        String modelName = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1);
        String servicePackage = controllerPropertyConfig.getServicePackage();
        String servicePrefix = controllerPropertyConfig.getServicePrefix();
        String serviceSuffix = controllerPropertyConfig.getServiceSuffix();

        String controllerPackage = controllerPropertyConfig.getControllerPackage();
        String controllerPrefix = controllerPropertyConfig.getControllerPrefix();
        String controllerSuffix = controllerPropertyConfig.getControllerSuffix();

        String serviceClassName =servicePackage + "." + servicePrefix + modelName + serviceSuffix;
        String controllerClassName = controllerPackage + "." + controllerPrefix + modelName + controllerSuffix;

        controllerPropertyConfig.setModelName(modelName);
        controllerPropertyConfig.setServiceClassName(serviceClassName);
        controllerPropertyConfig.setControllerClassName(controllerClassName);
        if (controllerPropertyConfig.getEnableController()){
            answer.add(generateController(introspectedTable));
        }
        return answer;
    }

    // 生成controller类
    private GeneratedJavaFile generateController(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType serviceJavaType = new FullyQualifiedJavaType(controllerPropertyConfig.getServiceClassName());
        FullyQualifiedJavaType controllerJavaType = new FullyQualifiedJavaType(controllerPropertyConfig.getControllerClassName());
        TopLevelClass clazz = new TopLevelClass(controllerJavaType);

        clazz.addImportedType(serviceJavaType);
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        //添加@Api注解，并引入相应的类
//        clazz.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.Api"));
        //添加@RequestMapping注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        //添加@Controller注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));

        String modelName = controllerPropertyConfig.getModelName();
        String lowerCase0ModelName = modelName.substring(0,1).toLowerCase() + modelName.substring(1);

        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.addAnnotation("@RestController");
        clazz.addAnnotation("@RequestMapping(\"/"+ lowerCase0ModelName +"\")");
//        clazz.addAnnotation("@Api(tags = \""+""+"\")");

        String serviceFieldName = "service";

        //描述类的成员属性
        Field daoField = new Field(serviceFieldName, serviceJavaType);
        //描述成员属性修饰符
        daoField.setVisibility(JavaVisibility.PRIVATE);
        //描述成员属性 的注解
        daoField.addAnnotation("@Autowired");
        clazz.addField(daoField);

        addControllerElements(clazz, introspectedTable);

        return new GeneratedJavaFile(clazz, controllerPropertyConfig.getControllerTargetProject(), context.getJavaFormatter());
    }




    protected static class ControllerPropertyConfig{

        // 项目目录，一般为 src/main/java
        private String controllerTargetProject;

        // Controller类包名
        private String controllerPackage;

        // controller接口名前缀
        private String controllerPrefix;

        // controller接口名后缀
        private String controllerSuffix;

        private String controllerClassName;

        private Boolean enableController;

        // service包名
        private String servicePackage;
        // service接口名前缀
        private String servicePrefix;
        // service接口名后缀
        private String serviceSuffix;

        private String serviceClassName;

        private String modelName;

        public ControllerPropertyConfig() {
        }

        public String getControllerTargetProject() {
            return controllerTargetProject;
        }

        public void setControllerTargetProject(String controllerTargetProject) {
            this.controllerTargetProject = controllerTargetProject;
        }

        public String getControllerPackage() {
            return controllerPackage;
        }

        public void setControllerPackage(String controllerPackage) {
            this.controllerPackage = controllerPackage;
        }

        public String getControllerPrefix() {
            return controllerPrefix;
        }

        public void setControllerPrefix(String controllerPrefix) {
            this.controllerPrefix = controllerPrefix;
        }

        public String getControllerSuffix() {
            return controllerSuffix;
        }

        public void setControllerSuffix(String controllerSuffix) {
            this.controllerSuffix = controllerSuffix;
        }

        public String getControllerClassName() {
            return controllerClassName;
        }

        public void setControllerClassName(String controllerClassName) {
            this.controllerClassName = controllerClassName;
        }

        public Boolean getEnableController() {
            return enableController;
        }

        public void setEnableController(Boolean enableController) {
            this.enableController = enableController;
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

        public String getServiceClassName() {
            return serviceClassName;
        }

        public void setServiceClassName(String serviceClassName) {
            this.serviceClassName = serviceClassName;
        }

        public String getServicePackage() {
            return servicePackage;
        }

        public void setServicePackage(String servicePackage) {
            this.servicePackage = servicePackage;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }
    }
}
