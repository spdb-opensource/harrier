package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

public class ServicePlugin extends AbstractServicePlugin {

	public ServicePlugin() {
		super();
	}

	public ServicePlugin(Properties properties) {
		super(properties);
	}

	@Override
	public void addServiceElements(Interface interfaze, IntrospectedTable introspectedTable) {
		serviceAddAdd(interfaze, introspectedTable);
		serviceAddDelete(interfaze, introspectedTable);
		serviceAddUpdate(interfaze, introspectedTable);
		serviceAddGetDetail(interfaze, introspectedTable);
		serviceAddListQuery(interfaze, introspectedTable);
	}

	@Override
	public void addServiceImplElements(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		implAddAdd(interfaze, introspectedTable);
		implAddDelete(interfaze, introspectedTable);
		implAddUpdate(interfaze, introspectedTable);
		implAddGetDetail(interfaze, introspectedTable);
		implAddListQuery(interfaze, introspectedTable);
	}

	private void serviceAddUpdate(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

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
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *修改");
		method.addJavaDocLine(" */");
		method.setAbstract(true);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddAdd(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "add";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *新增");
		method.addJavaDocLine(" */");

		method.setAbstract(true);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddDelete(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
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
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *id删除");
		method.addJavaDocLine(" */");

		method.setAbstract(true);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddGetDetail(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

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
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *查看详情");
		method.addJavaDocLine(" */");

		method.setAbstract(true);
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddListQuery(Interface interfaze, IntrospectedTable introspectedTable) {

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类
		importedTypes.add(modelType);
		// 导入List
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List");
		// 设置泛型参数
		returnType.addTypeArgument(modelType);

		// 方法名称
		String methodName = "listQuery";

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);

		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *未完成的列表查询，待完善");
		method.addJavaDocLine(" */");

		method.setAbstract(true);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

	}

	// -------------------

	private void serviceAddSelectUniqueKeyCollection(Interface interfaze, IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String uniqueKeysString = introspectedTable.getTableConfigurationProperty("uniqueKeys");
		if (uniqueKeysString == null) {
			return;
		}
		if (!(uniqueKeysString.matches(uniqueKeysFormat))) {
			System.out.println(String.format("【%s】表配置的【uniqueKeys】属性格式错误，插件不生成唯一索引的查询接口", tableName));
			return;
		}

		String[] uniqueKeys = uniqueKeysString.split(";");

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				if (column == null) {
					System.out.println(String.format("【%s】表生成【%s】唯一索引查询失败，原因是【%s】字段不存在", tableName, uniqueKey, key));
					return;
				}
			}
		}

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入List类型
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			List<IntrospectedColumn> keyColumnList = new ArrayList<IntrospectedColumn>();
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				keyColumnList.add(column);
			}

			IntrospectedColumn lastColumn = keyColumnList.remove(keyColumnList.size() - 1);
			String lastActualColumnName = lastColumn.getActualColumnName();
			String lastJdbcTypeName = lastColumn.getJdbcTypeName();
			FullyQualifiedJavaType lastJavaType = lastColumn.getFullyQualifiedJavaType();
			String lastJavaProperty = lastColumn.getJavaProperty();
			String lastJavaPropertyUp = lastJavaProperty.substring(0, 1).toUpperCase() + lastJavaProperty.substring(1);
			String lastJavaPropertyCollection = lastJavaProperty + "Collection";

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型：实体类类型
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("List");
			methodReturnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

			StringBuilder methodName = new StringBuilder("listBy");
			StringBuilder remarkItem = new StringBuilder();
			for (IntrospectedColumn column : keyColumnList) {
				String javaProperty = column.getJavaProperty();
				remarkItem.append(javaProperty).append(",");

				String javaPropertyUp0 = javaProperty.substring(0, 1).toUpperCase() + javaProperty.substring(1);
				methodName.append(javaPropertyUp0);
			}
			remarkItem.append(lastJavaPropertyCollection);
			methodName.append(lastJavaPropertyUp).append("Collection");

			List<Parameter> methodParameterList = new ArrayList<Parameter>();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();

				FullyQualifiedJavaType javaType = column.getFullyQualifiedJavaType();

				// 参数类型
				FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType(javaType.getShortName());

				// 方法参数
				Parameter methodParameter = new Parameter(methodParameterType, javaProperty);

				methodParameterList.add(methodParameter);

				// 导入参数类型
				importedTypes.add(javaType);
			}
			FullyQualifiedJavaType lastParameterType = new FullyQualifiedJavaType("Collection");
			lastParameterType.addTypeArgument(lastJavaType);
			methodParameterList.add(new Parameter(lastParameterType, lastJavaPropertyCollection));

			Method method = new Method(methodName.toString());
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName.toString());
			for (Parameter parameter : methodParameterList) {
				method.addParameter(parameter);
			}
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *唯一索引集合查询：通过" + remarkItem.toString() + "查询");
			method.addJavaDocLine(" */");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void serviceAddSelectUniqueKey(Interface interfaze, IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String uniqueKeysString = introspectedTable.getTableConfigurationProperty("uniqueKeys");
		if (uniqueKeysString == null) {
			return;
		}
		if (!(uniqueKeysString.matches(uniqueKeysFormat))) {
			System.out.println(String.format("【%s】表配置的【uniqueKeys】属性格式错误，插件不生成唯一索引的查询接口", tableName));
			return;
		}

		String[] uniqueKeys = uniqueKeysString.split(";");

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				if (column == null) {
					System.out.println(String.format("【%s】表生成【%s】唯一索引查询失败，原因是【%s】字段不存在", tableName, uniqueKey, key));
					return;
				}
			}
		}

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			List<IntrospectedColumn> keyColumnList = new ArrayList<IntrospectedColumn>();
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				keyColumnList.add(column);
			}

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型：实体类类型
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

			StringBuilder methodName = new StringBuilder("getBy");
			StringBuilder remarkItem = new StringBuilder();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();
				remarkItem.append(javaProperty).append(",");

				String substring0 = javaProperty.substring(0, 1);
				String substring1 = javaProperty.substring(1);
				String javaPropertyUp0 = substring0.toUpperCase() + substring1;
				methodName.append(javaPropertyUp0);
			}
			remarkItem.deleteCharAt(remarkItem.length() - 1);

			List<Parameter> methodParameterList = new ArrayList<Parameter>();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();

				FullyQualifiedJavaType javaType = column.getFullyQualifiedJavaType();

				// 参数类型
				FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType(javaType.getShortName());

				// 方法参数
				Parameter methodParameter = new Parameter(methodParameterType, javaProperty);

				methodParameterList.add(methodParameter);

				// 导入参数类型
				importedTypes.add(javaType);
			}

			Method method = new Method(methodName.toString());
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName.toString());
			for (Parameter parameter : methodParameterList) {
				method.addParameter(parameter);
			}
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *唯一索引查询：通过" + remarkItem.toString() + "查询");
			method.addJavaDocLine(" */");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void serviceAddSelectForeignKey(Interface interfaze, IntrospectedTable introspectedTable) {

		String foreignKeyString = introspectedTable.getTableConfigurationProperty("foreignKeys");
		if (foreignKeyString == null) {
			return;
		}
		if (!(foreignKeyString.matches(foreignKeysFormat))) {
			System.out.println(String.format("【%s表】配置foreignKeys属性格式错误：请保证外键字段与数据库字段完全一致，如果有多个字段，不同字段用‘,’隔开",
					introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		String[] foreignKeys = foreignKeyString.split(",");

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		for (String foreignKey : foreignKeys) {
			IntrospectedColumn introspectedColumn = introspectedTable.getColumn(foreignKey).orElse(null);
			if (introspectedColumn == null) {
				continue;
			}

			// 添加字段类型
			importedTypes.add(introspectedColumn.getFullyQualifiedJavaType());

			String javaProperty = introspectedColumn.getJavaProperty();

			String substring0 = javaProperty.substring(0, 1);
			String substring1 = javaProperty.substring(1);
			String javaPropertyUp0 = substring0.toUpperCase() + substring1;
			String javaPropertyCollection = javaProperty + "Collection";

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型是List
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("List");
			methodReturnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

			String methodName = "listBy" + javaPropertyUp0 + "Collection";

			// 设置参数类型
			FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
			// 给参数类型补充泛型
			methodParameterType.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType());
			// 方法参数
			Parameter methodParameter = new Parameter(methodParameterType, javaPropertyCollection);

			Method method = new Method(methodName);
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName);
			method.addParameter(methodParameter);
			// 设置备注
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *外键查询：通过" + javaProperty + "集合查询");
			method.addJavaDocLine(" */");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void serviceAddUpdateBatch(Interface interfaze, IntrospectedTable introspectedTable) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 设置返回类型是List
		FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("int");

		String methodName = "updateBatch";

		// 设置参数类型
		FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
		// 给参数类型补充泛型
		methodParameterType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 方法参数
		Parameter methodParameter = new Parameter(methodParameterType, "dataCollection");

		Method method = new Method(methodName);
		method.setVisibility(methodVisibility);
		method.setReturnType(methodReturnType);
		method.setName(methodName);
		method.addParameter(methodParameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *批量修改：数据为空会报错，使用时请判断是否空");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddInsertBatch(Interface interfaze, IntrospectedTable introspectedTable) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 设置返回类型是List
		FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("int");

		String methodName = "insertBatch";

		// 设置参数类型
		FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
		// 给参数类型补充泛型
		methodParameterType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 方法参数
		Parameter methodParameter = new Parameter(methodParameterType, "dataCollection");

		Method method = new Method(methodName);
		method.setVisibility(methodVisibility);
		method.setReturnType(methodReturnType);
		method.setName(methodName);
		method.addParameter(methodParameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *批量插入；数据集合为空会报错，使用时请判断是否空");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddListSelective(Interface interfaze, IntrospectedTable introspectedTable) {

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类
		importedTypes.add(modelType);
		// 导入List
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List");
		// 设置泛型参数
		returnType.addTypeArgument(modelType);

		// 方法名称
		String methodName = "listSelective";

		// 设置参数类型是对象
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "query");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *属性动态查询");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddDeleteByIdCollection(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			System.out.println(
					String.format("【%s表】无法生成deleteByIdCollection方法，原因是CustomPlugin插件只生成单主键表的deleteByIdCollection方法",
							introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();
		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入数据表对应实体类类型
		importedTypes.add(modelType);
		// 导入主键类型
		importedTypes.add(primaryKeyType);
		// 导入Collection
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：int
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "deleteByIdCollection";

		// 参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("Collection");
		parameterType.addTypeArgument(primaryKeyType);
		Parameter parameter = new Parameter(parameterType, "idCollection");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id集合删除");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddSelectByIdCollection(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			System.out.println(
					String.format("【%s表】无法生成selectByIdCollection方法，原因是CustomPlugin插件只生成单主键表的selectByIdCollection方法",
							introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);
		// 导入List类型
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		// 导入Collection
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：List
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		// 设置返回类型（List）的泛型参数
		returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法名称
		String methodName = "selectByIdCollection";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("Collection");
		// 为参数类型添加泛型
		parameterType.addTypeArgument(primaryKeyType);
		Parameter parameter = new Parameter(parameterType, "idCollection");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id集合查询");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	// -------------------

	private void serviceAddUpdateByPrimaryKeySelective(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "updateByPrimaryKeySelective";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *修改");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddInsert(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "insert";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *插入");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddDeleteByPrimaryKey(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "deleteByPrimaryKey";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = primaryKeyType;
		Parameter parameter = new Parameter(parameterType, "id");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id查询删除");
		method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void serviceAddSelectByPrimaryKey(Interface interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 方法名称
		String methodName = "selectByPrimaryKey";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = primaryKeyType;
		Parameter parameter = new Parameter(parameterType, "id");

		// 创建方法对象
		Method method = new Method(methodName);
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id查询");
		method.addJavaDocLine(" */");

		method.setAbstract(true);

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	// --------------------------

	private void implAddUpdate(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

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
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *修改");
		method.addJavaDocLine(" */");

		method.addBodyLine("return mapper.updateByPrimaryKeySelective(" + parameter.getName() + ");");
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddAdd(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "add";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *新增");
		method.addJavaDocLine(" */");

		method.addBodyLine("return mapper.insertSelective( " + parameter.getName() + " );");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddDelete(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
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
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *id删除");
		method.addJavaDocLine(" */");

		method.addBodyLine("return mapper.deleteByPrimaryKey( " + parameter.getName() + " );");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddGetDetail(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

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
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *查看详情");
		method.addJavaDocLine(" */");

		method.addBodyLine("return mapper.selectByPrimaryKey( " + parameter.getName() + " ).orElse(null);");
		
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddListQuery(TopLevelClass interfaze, IntrospectedTable introspectedTable) {

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类
		importedTypes.add(modelType);
		// 导入List
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List");
		// 设置泛型参数
		returnType.addTypeArgument(modelType);

		// 方法名称
		String methodName = "listQuery";

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);

		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *列表查询");
		method.addJavaDocLine(" */");

		method.addBodyLine("return mapper.select( null );");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

	}

	// ---------------------------------

	private void implAddSelectUniqueKeyCollection(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String uniqueKeysString = introspectedTable.getTableConfigurationProperty("uniqueKeys");
		if (uniqueKeysString == null) {
			return;
		}
		if (!(uniqueKeysString.matches(uniqueKeysFormat))) {
			System.out.println(String.format("【%s】表配置的【uniqueKeys】属性格式错误，插件不生成唯一索引的查询接口", tableName));
			return;
		}

		String[] uniqueKeys = uniqueKeysString.split(";");

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				if (column == null) {
					System.out.println(String.format("【%s】表生成【%s】唯一索引查询失败，原因是【%s】字段不存在", tableName, uniqueKey, key));
					return;
				}
			}
		}

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入List类型
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			List<IntrospectedColumn> keyColumnList = new ArrayList<IntrospectedColumn>();
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				keyColumnList.add(column);
			}

			IntrospectedColumn lastColumn = keyColumnList.remove(keyColumnList.size() - 1);
			String lastActualColumnName = lastColumn.getActualColumnName();
			String lastJdbcTypeName = lastColumn.getJdbcTypeName();
			FullyQualifiedJavaType lastJavaType = lastColumn.getFullyQualifiedJavaType();
			String lastJavaProperty = lastColumn.getJavaProperty();
			String lastJavaPropertyUp = lastJavaProperty.substring(0, 1).toUpperCase() + lastJavaProperty.substring(1);
			String lastJavaPropertyCollection = lastJavaProperty + "Collection";

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型：实体类类型
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("List");
			methodReturnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

			StringBuilder methodName = new StringBuilder("listBy");
			StringBuilder remarkItem = new StringBuilder();
			for (IntrospectedColumn column : keyColumnList) {
				String javaProperty = column.getJavaProperty();
				remarkItem.append(javaProperty).append(",");

				String javaPropertyUp0 = javaProperty.substring(0, 1).toUpperCase() + javaProperty.substring(1);
				methodName.append(javaPropertyUp0);
			}
			remarkItem.append(lastJavaPropertyCollection);
			methodName.append(lastJavaPropertyUp).append("Collection");

			List<Parameter> methodParameterList = new ArrayList<Parameter>();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();

				FullyQualifiedJavaType javaType = column.getFullyQualifiedJavaType();

				// 参数类型
				FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType(javaType.getShortName());

				// 方法参数
				Parameter methodParameter = new Parameter(methodParameterType, javaProperty);

				methodParameterList.add(methodParameter);

				// 导入参数类型
				importedTypes.add(javaType);
			}
			FullyQualifiedJavaType lastParameterType = new FullyQualifiedJavaType("Collection");
			lastParameterType.addTypeArgument(lastJavaType);
			methodParameterList.add(new Parameter(lastParameterType, lastJavaPropertyCollection));

			Method method = new Method(methodName.toString());
			method.addAnnotation("@Override");
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName.toString());
			for (Parameter parameter : methodParameterList) {
				method.addParameter(parameter);
			}
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *唯一索引集合查询：通过" + remarkItem.toString() + "查询");
			method.addJavaDocLine(" */");

			List<Parameter> parameters = method.getParameters();
			StringBuilder paramBuilder = new StringBuilder();
			for (Parameter p : parameters) {
				paramBuilder.append(p.getName()).append(", ");
			}
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void implAddSelectUniqueKey(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String uniqueKeysString = introspectedTable.getTableConfigurationProperty("uniqueKeys");
		if (uniqueKeysString == null) {
			return;
		}
		if (!(uniqueKeysString.matches(uniqueKeysFormat))) {
			System.out.println(String.format("【%s】表配置的【uniqueKeys】属性格式错误，插件不生成唯一索引的查询接口", tableName));
			return;
		}

		String[] uniqueKeys = uniqueKeysString.split(";");

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				if (column == null) {
					System.out.println(String.format("【%s】表生成【%s】唯一索引查询失败，原因是【%s】字段不存在", tableName, uniqueKey, key));
					return;
				}
			}
		}

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		for (String uniqueKey : uniqueKeys) {
			String[] keys = uniqueKey.split(",");
			List<IntrospectedColumn> keyColumnList = new ArrayList<IntrospectedColumn>();
			for (String key : keys) {
				IntrospectedColumn column = introspectedTable.getColumn(key).orElse(null);
				keyColumnList.add(column);
			}

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型：实体类类型
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

			StringBuilder methodName = new StringBuilder("getBy");
			StringBuilder remarkItem = new StringBuilder();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();
				remarkItem.append(javaProperty).append(",");

				String substring0 = javaProperty.substring(0, 1);
				String substring1 = javaProperty.substring(1);
				String javaPropertyUp0 = substring0.toUpperCase() + substring1;
				methodName.append(javaPropertyUp0);
			}
			remarkItem.deleteCharAt(remarkItem.length() - 1);

			List<Parameter> methodParameterList = new ArrayList<Parameter>();
			for (IntrospectedColumn column : keyColumnList) {

				String javaProperty = column.getJavaProperty();

				FullyQualifiedJavaType javaType = column.getFullyQualifiedJavaType();

				// 参数类型
				FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType(javaType.getShortName());

				// 方法参数
				Parameter methodParameter = new Parameter(methodParameterType, javaProperty);

				methodParameterList.add(methodParameter);

				// 导入参数类型
				importedTypes.add(javaType);
			}

			Method method = new Method(methodName.toString());
			method.addAnnotation("@Override");
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName.toString());
			for (Parameter parameter : methodParameterList) {
				method.addParameter(parameter);
			}
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *唯一索引查询：通过" + remarkItem.toString() + "查询");
			method.addJavaDocLine(" */");

			List<Parameter> parameters = method.getParameters();
			StringBuilder paramBuilder = new StringBuilder();
			for (Parameter p : parameters) {
				paramBuilder.append(p.getName()).append(", ");
			}
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void implAddSelectForeignKey(TopLevelClass interfaze, IntrospectedTable introspectedTable) {

		String foreignKeyString = introspectedTable.getTableConfigurationProperty("foreignKeys");
		if (foreignKeyString == null) {
			return;
		}
		if (!(foreignKeyString.matches(foreignKeysFormat))) {
			System.out.println(String.format("【%s表】配置foreignKeys属性格式错误：请保证外键字段与数据库字段完全一致，如果有多个字段，不同字段用‘,’隔开",
					introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		String[] foreignKeys = foreignKeyString.split(",");

		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		for (String foreignKey : foreignKeys) {
			IntrospectedColumn introspectedColumn = introspectedTable.getColumn(foreignKey).orElse(null);
			if (introspectedColumn == null) {
				continue;
			}

			// 添加字段类型
			importedTypes.add(introspectedColumn.getFullyQualifiedJavaType());

			String javaProperty = introspectedColumn.getJavaProperty();

			String substring0 = javaProperty.substring(0, 1);
			String substring1 = javaProperty.substring(1);
			String javaPropertyUp0 = substring0.toUpperCase() + substring1;
			String javaPropertyCollection = javaProperty + "Collection";

			JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

			// 设置返回类型是List
			FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("List");
			methodReturnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

			String methodName = "listBy" + javaPropertyUp0 + "Collection";

			// 设置参数类型
			FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
			// 给参数类型补充泛型
			methodParameterType.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType());
			// 方法参数
			Parameter methodParameter = new Parameter(methodParameterType, javaPropertyCollection);

			Method method = new Method(methodName);
			method.addAnnotation("@Override");
			method.setVisibility(methodVisibility);
			method.setReturnType(methodReturnType);
			method.setName(methodName);
			method.addParameter(methodParameter);
			// 设置备注
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" *外键查询：通过" + javaProperty + "集合查询");
			method.addJavaDocLine(" */");

			List<Parameter> parameters = method.getParameters();
			StringBuilder paramBuilder = new StringBuilder();
			for (Parameter p : parameters) {
				paramBuilder.append(p.getName()).append(", ");
			}
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			paramBuilder.deleteCharAt(paramBuilder.length() - 1);
			method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void implAddUpdateBatch(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 设置返回类型是List
		FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("int");

		String methodName = "updateBatch";

		// 设置参数类型
		FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
		// 给参数类型补充泛型
		methodParameterType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 方法参数
		Parameter methodParameter = new Parameter(methodParameterType, "dataCollection");

		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		method.setVisibility(methodVisibility);
		method.setReturnType(methodReturnType);
		method.setName(methodName);
		method.addParameter(methodParameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *批量修改：数据为空会报错，使用时请判断是否空");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddInsertBatch(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 设置返回类型是List
		FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType("int");

		String methodName = "insertBatch";

		// 设置参数类型
		FullyQualifiedJavaType methodParameterType = new FullyQualifiedJavaType("Collection");
		// 给参数类型补充泛型
		methodParameterType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 方法参数
		Parameter methodParameter = new Parameter(methodParameterType, "dataCollection");

		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		method.setVisibility(methodVisibility);
		method.setReturnType(methodReturnType);
		method.setName(methodName);
		method.addParameter(methodParameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *批量插入；数据集合为空会报错，使用时请判断是否空");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddListSelective(TopLevelClass interfaze, IntrospectedTable introspectedTable) {

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入实体类
		importedTypes.add(modelType);
		// 导入List
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List");
		// 设置泛型参数
		returnType.addTypeArgument(modelType);

		// 方法名称
		String methodName = "listSelective";

		// 设置参数类型是对象
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "query");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *属性动态查询");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddDeleteByIdCollection(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			System.out.println(
					String.format("【%s表】无法生成deleteByIdCollection方法，原因是CustomPlugin插件只生成单主键表的deleteByIdCollection方法",
							introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();
		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 导入数据表对应实体类类型
		importedTypes.add(modelType);
		// 导入主键类型
		importedTypes.add(primaryKeyType);
		// 导入Collection
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		// 访问类型：public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 返回类型：int
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "deleteByIdCollection";

		// 参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("Collection");
		parameterType.addTypeArgument(primaryKeyType);
		Parameter parameter = new Parameter(parameterType, "idCollection");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id集合删除");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddSelectByIdCollection(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			System.out.println(
					String.format("【%s表】无法生成selectByIdCollection方法，原因是CustomPlugin插件只生成单主键表的selectByIdCollection方法",
							introspectedTable.getFullyQualifiedTableNameAtRuntime()));
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);
		// 导入List类型
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		// 导入Collection
		importedTypes.add(new FullyQualifiedJavaType("java.util.Collection"));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：List
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		// 设置返回类型（List）的泛型参数
		returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法名称
		String methodName = "selectByIdCollection";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("Collection");
		// 为参数类型添加泛型
		parameterType.addTypeArgument(primaryKeyType);
		Parameter parameter = new Parameter(parameterType, "idCollection");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id集合查询");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	// -------------------

	private void implAddUpdateByPrimaryKeySelective(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "updateByPrimaryKeySelective";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *修改");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddInsert(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "insert";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		Parameter parameter = new Parameter(parameterType, "record");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *新增");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddDeleteByPrimaryKey(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");

		// 方法名称
		String methodName = "deleteByPrimaryKey";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = primaryKeyType;
		Parameter parameter = new Parameter(parameterType, "id");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id查询删除");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	private void implAddSelectByPrimaryKey(TopLevelClass interfaze, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> primaryKeyColumnList = introspectedTable.getPrimaryKeyColumns();
		if (primaryKeyColumnList == null || primaryKeyColumnList.size() != 1) {
			return;
		}

		FullyQualifiedJavaType primaryKeyType = primaryKeyColumnList.get(0).getFullyQualifiedJavaType();

		// 创建import对象：mapper接口的导入包或者导入类型
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		// 导入数据表对应实体类类型
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		// 导入主键类型
		importedTypes.add(primaryKeyType);

		// 方法访问类型为public
		JavaVisibility methodVisibility = JavaVisibility.PUBLIC;

		// 创建返回类型：
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		// 方法名称
		String methodName = "selectByPrimaryKey";

		// 创建参数类型
		FullyQualifiedJavaType parameterType = primaryKeyType;
		Parameter parameter = new Parameter(parameterType, "id");

		// 创建方法对象
		Method method = new Method(methodName);
		method.addAnnotation("@Override");
		// 设置访问类型
		method.setVisibility(methodVisibility);
		// 设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名
		method.setName(methodName);
		// 设置方法参数
		method.addParameter(parameter);
		// 设置备注
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" *通过id查询");
		method.addJavaDocLine(" */");

		List<Parameter> parameters = method.getParameters();
		StringBuilder paramBuilder = new StringBuilder();
		for (Parameter p : parameters) {
			paramBuilder.append(p.getName()).append(", ");
		}
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		paramBuilder.deleteCharAt(paramBuilder.length() - 1);
		method.addBodyLine("return mapper." + methodName + "(" + paramBuilder.toString() + ");");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}
}
