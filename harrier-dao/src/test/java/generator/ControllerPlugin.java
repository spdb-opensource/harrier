package generator;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ControllerPlugin extends AbstractControllerPlugin {

	
	public ControllerPlugin() {
		super();
	}

	public ControllerPlugin(Properties properties) {
		super(properties);
	}

	@Override
    public void addControllerElements(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
        addAdd(interfaze, introspectedTable);
        addDelete(interfaze, introspectedTable);
        addUpdate(interfaze, introspectedTable);
        addGetDetail(interfaze, introspectedTable);
        addListQuery(interfaze, introspectedTable);
    }

    private void addUpdate(TopLevelClass interfaze, IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1){
            return;
        }

        // 创建import对象：mapper接口的导入包或者导入类型
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        //导入数据表对应实体类类型
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
//        importedTypes.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PostMapping"));

        // 方法访问类型为public
        JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

        // 创建返回类型：
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

        // 方法名称
        String methodName = "update";

        // 创建参数类型
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        Parameter parameter = new Parameter(parameterType, "record");

        // 创建方法对象
        Method method = new Method("修改");
//        method.addAnnotation("@ApiOperation(value = \"修改\", notes = \"\")");
        method.addAnnotation("@PostMapping(\"/update\")");
        // 设置访问类型
        method.setVisibility(methodVisibility);
        // 设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名
        method.setName(methodName);
        // 设置方法参数
        method.addParameter(parameter);

        List<Parameter> parameters = method.getParameters();
        StringBuilder paramBuilder = new StringBuilder();
        for (Parameter p : parameters){
            paramBuilder.append(p.getName()).append(", ");
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        method.addBodyLine("return service." + methodName + "(" + paramBuilder.toString() + ");");

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    private void addAdd(TopLevelClass interfaze, IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1){
            return;
        }

        // 创建import对象：mapper接口的导入包或者导入类型
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        //导入数据表对应实体类类型
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

        // 方法访问类型为public
        JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

        // 创建返回类型：
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");
//        importedTypes.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PutMapping"));

        // 方法名称
        String methodName = "add";

        // 创建参数类型
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        Parameter parameter = new Parameter(parameterType, "record");

        // 创建方法对象
        Method method = new Method("新增");
//        method.addAnnotation("@ApiOperation(value = \"新增\", notes = \"\")");
        method.addAnnotation("@PutMapping(\"/add\")");
        // 设置访问类型
        method.setVisibility(methodVisibility);
        // 设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名
        method.setName(methodName);
        // 设置方法参数
        method.addParameter(parameter);

        List<Parameter> parameters = method.getParameters();
        StringBuilder paramBuilder = new StringBuilder();
        for (Parameter p : parameters){
            paramBuilder.append(p.getName()).append(", ");
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        method.addBodyLine("return service." + methodName + "(" + paramBuilder.toString() + ");");

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    private void addDelete(TopLevelClass interfaze, IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1){
            return;
        }

        FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

        // 创建import对象：mapper接口的导入包或者导入类型
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
//        importedTypes.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.DeleteMapping"));

        //导入数据表对应实体类类型
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        //导入主键类型
        importedTypes.add(primaryKeyType);

        // 方法访问类型为public
        JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

        // 创建返回类型：
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

        // 方法名称
        String methodName = "delete";

        // 创建参数类型
        FullyQualifiedJavaType parameterType = primaryKeyType;
        Parameter parameter = new Parameter(parameterType, "id");

        // 创建方法对象
        Method method = new Method("删除");
//        method.addAnnotation("@ApiOperation(value = \"删除\", notes = \"\")");
        method.addAnnotation("@DeleteMapping(\"/delete\")");
        // 设置访问类型
        method.setVisibility(methodVisibility);
        // 设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名
        method.setName(methodName);
        // 设置方法参数
        method.addParameter(parameter);

        List<Parameter> parameters = method.getParameters();
        StringBuilder paramBuilder = new StringBuilder();
        for (Parameter p : parameters){
            paramBuilder.append(p.getName()).append(", ");
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        method.addBodyLine("return service." + methodName + "(" + paramBuilder.toString() + ");");

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    private void addGetDetail(TopLevelClass interfaze, IntrospectedTable introspectedTable){
        List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1){
            return;
        }

        FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

        // 创建import对象：mapper接口的导入包或者导入类型
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        //导入数据表对应实体类类型
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        //导入主键类型
        importedTypes.add(primaryKeyType);
        importedTypes.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));

        // 方法访问类型为public
        JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

        // 创建返回类型：
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        // 方法名称
        String methodName = "getDetail";

        // 创建参数类型
        FullyQualifiedJavaType parameterType = primaryKeyType;
        Parameter parameter = new Parameter(parameterType, "id");

        // 创建方法对象
        Method method = new Method("详情");
//        method.addAnnotation("@ApiOperation(value = \"详情\", notes = \"\")");
        method.addAnnotation("@GetMapping(\"/getDetail\")");
        // 设置访问类型
        method.setVisibility(methodVisibility);
        // 设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名
        method.setName(methodName);
        // 设置方法参数
        method.addParameter(parameter);

        List<Parameter> parameters = method.getParameters();
        StringBuilder paramBuilder = new StringBuilder();
        for (Parameter p : parameters){
            paramBuilder.append(p.getName()).append(", ");
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        method.addBodyLine("return service." + methodName + "(" + paramBuilder.toString() + ");");

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    private void addListQuery(TopLevelClass interfaze, IntrospectedTable introspectedTable){

        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        // 创建import对象：mapper接口的导入包或者导入类型
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        // 导入实体类
        importedTypes.add(modelType);
        // 导入List
        importedTypes.add(new FullyQualifiedJavaType("java.util.List"));
        importedTypes.add(new FullyQualifiedJavaType("io.swagger.annotations.ApiOperation"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));

        // 访问类型：public
        JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

        // 返回类型：List
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List");
        // 设置泛型参数
        returnType.addTypeArgument(modelType);

        // 方法名称
        String methodName = "listQuery";


        // 创建方法对象
        Method method = new Method("查询");
//        method.addAnnotation("@ApiOperation(value = \"列表查询\", notes = \"未完成的列表查询，待完善\")");
        method.addAnnotation("@GetMapping(\"/listQuery\")");
        // 设置访问类型
        method.setVisibility(methodVisibility);
        // 设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名
        method.setName(methodName);

        method.addBodyLine("return service.listQuery();");

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }
}
