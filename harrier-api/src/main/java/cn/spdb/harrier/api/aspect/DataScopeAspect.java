package cn.spdb.harrier.api.aspect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;

import cn.spdb.harrier.api.enums.PermissionStatus;
import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.utils.SecurityUtils;
import cn.spdb.harrier.common.uitls.StringUtils;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.dao.datasource.SpringDruidFactory;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

@Aspect
@Component
public class DataScopeAspect implements DataPermissionHandler {

	@Autowired
	private SpringDruidFactory springDruidFactory;

	private ThreadLocal<DataScopeParam> threadParam = new ThreadLocal<DataScopeAspect.DataScopeParam>();

	@PostConstruct
	public void init() {
		springDruidFactory.addInnerInterceptor(new DataPermissionInterceptor(this));
	}

	@Before("@annotation(controllerDataScope)")
	public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable {
		handleDataScope(point, controllerDataScope);
	}

	protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope) {
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		DataScopeParam param = new DataScopeParam();
		if (loginUser == null) {
			threadParam.set(param);
			return;
		}
		param.setAdmin(SecurityUtils.isAdmin());
		String table=controllerDataScope.tableAlias();
		if(!StringUtils.isBlank(table)){
			param.setPlaftormAlias(table+Symbol.DIAN+controllerDataScope.plaftormAlias());
			param.setSystemsAlias(table+Symbol.DIAN+controllerDataScope.systemsAlias());
		}else {			
			param.setPlaftormAlias(controllerDataScope.plaftormAlias());
			param.setSystemsAlias(controllerDataScope.systemsAlias());
		}
		param.addPlatfromSystems(loginUser.getPermissions().toArray(new String[] {}));
		threadParam.set(param);
	}

	@After("@annotation(controllerDataScope)")
	public void doAfter(JoinPoint point, DataScope controllerDataScope) {
		threadParam.remove();
	}

	@Override
	public Expression getSqlSegment(Expression where, String mappedStatementId) {

		DataScopeParam param = threadParam.get();
		if (param == null || param.getAdmin()) {
			return where;
		}

		if (where == null) {
			where = new HexValue(" 1 = 1 ");
		}

		if (param.getPlatfromSytem().size() == 0) {
			return new AndExpression(where, new HexValue(" 1 = 2 "));
		}

		String platfromAlias = param.getPlaftormAlias();
		String systemsAlias = param.getSystemsAlias();

		Expression right = null;

		for (Entry<String, HashSet<String>> en : param.getPlatfromSytem().entrySet()) {
			HashSet<String> systemsSet = en.getValue();
			String platform = en.getKey();
			Expression expression = null;
			Expression left1 = new EqualsTo(new Column(platfromAlias), new StringValue(platform));
			Expression right1 = null;
			if (systemsSet.size() == 1) {
				String systems = systemsSet.iterator().next();
				if (Symbol.XING_HAO.equals(systems)) {
					right1 = null;
				} else {
					right1 = new EqualsTo(new Column(systemsAlias), new StringValue(systems));
				}
			} else {
				List<Expression> list = systemsSet.stream().map(StringValue::new).collect(Collectors.toList());
				right1 = new InExpression(new Column(systemsAlias), new ExpressionList(list));
			}
			if (right1 == null) {
				expression = left1;
			} else {
				expression = new AndExpression(left1, right1);
			}
			if (right == null) {
				right = expression;
			} else {
				right = new OrExpression(right, expression);
			}
		}

		return new AndExpression(where, right);
	}

	public class DataScopeParam {

		private Boolean admin;

		private String plaftormAlias;

		private String systemsAlias;

		private HashMap<String, HashSet<String>> platfromSytem = new HashMap<String, HashSet<String>>();

		public void addPlatfromSystems(String... list) {
			for (String str : list) {
				String[] permissions = str.split(Symbol.MAO_HAO);
				if (permissions.length < 3) {
					continue;
				}
				if (PermissionStatus.R.getStatus().equals(permissions[2])
						|| PermissionStatus.RW.getStatus().equals(permissions[2])) {
					addPlatfromSystems(permissions[0], permissions[1]);
				}
			}
		}

		public void addPlatfromSystems(String platfrom, String system) {
			HashSet<String> hashSet = platfromSytem.get(platfrom);
			if (hashSet == null) {
				hashSet = new HashSet<String>();
			}
			if (hashSet.size() == 1 && Symbol.XING_HAO.equals(hashSet.iterator().next())) {
				return;
			}

			if (Symbol.XING_HAO.equals(system)) {
				hashSet.clear();
			}
			hashSet.add(system);

			platfromSytem.put(platfrom, hashSet);
		}

		public Boolean getAdmin() {
			return admin;
		}

		public void setAdmin(Boolean admin) {
			this.admin = admin;
		}

		public String getPlaftormAlias() {
			return plaftormAlias;
		}

		public void setPlaftormAlias(String plaftormAlias) {
			this.plaftormAlias = plaftormAlias;
		}

		public String getSystemsAlias() {
			return systemsAlias;
		}

		public void setSystemsAlias(String systemsAlias) {
			this.systemsAlias = systemsAlias;
		}

		public HashMap<String, HashSet<String>> getPlatfromSytem() {
			return platfromSytem;
		}

		public void setPlatfromSytem(HashMap<String, HashSet<String>> platfromSytem) {
			this.platfromSytem = platfromSytem;
		}

	}

}
