package cn.spdb.harrier.dao.datasource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;

public class PaginationInnerInterceptorByMybatis3 extends PaginationInnerInterceptor {

	@Override
	public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
		if (page == null || page.getSize() < 0 || !page.searchCount()) {
			return true;
		}
		parameter = findParameter(parameter);
		BoundSql countSql;
		MappedStatement countMs = buildCountMappedStatement(ms, page.countId());
		if (countMs != null) {
			countSql = countMs.getBoundSql(parameter);
		} else {
			countMs = buildAutoCountMappedStatement(ms);
			String countSqlStr = autoCountSql(page, boundSql.getSql());
			PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
			countSql = new BoundSql(countMs.getConfiguration(), countSqlStr, mpBoundSql.parameterMappings(), parameter);
			PluginUtils.setAdditionalParameter(countSql, mpBoundSql.additionalParameters());
		}

		CacheKey cacheKey = executor.createCacheKey(countMs, parameter, rowBounds, countSql);
		List<Object> result = executor.query(countMs, parameter, rowBounds, resultHandler, cacheKey, countSql);
		long total = 0;
		if (CollectionUtils.isNotEmpty(result)) {
			// 个别数据库 count 没数据不会返回 0
			Object o = result.get(0);
			if (o != null) {
				total = Long.parseLong(o.toString());
			}
		}
		page.setTotal(total);
		return continuePage(page);
	}

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
		if (null == page) {
			return;
		}
		parameter = findParameter(parameter);
		// 处理 orderBy 拼接
		boolean addOrdered = false;
		String buildSql = boundSql.getSql();
		List<OrderItem> orders = page.orders();
		if (CollectionUtils.isNotEmpty(orders)) {
			addOrdered = true;
			buildSql = this.concatOrderBy(buildSql, orders);
		}

		// size 小于 0 且不限制返回值则不构造分页sql
		Long _limit = page.maxLimit() != null ? page.maxLimit() : maxLimit;
		if (page.getSize() < 0 && null == _limit) {
			if (addOrdered) {
				PluginUtils.mpBoundSql(boundSql).sql(buildSql);
			}
			return;
		}

		handlerLimit(page, _limit);
		IDialect dialect = findIDialect(executor);

		final Configuration configuration = ms.getConfiguration();
		DialectModel model = dialect.buildPaginationSql(buildSql, page.offset(), page.getSize());
		PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);

		List<ParameterMapping> mappings = mpBoundSql.parameterMappings();
		Map<String, Object> additionalParameter = mpBoundSql.additionalParameters();
		model.consumers(mappings, configuration, additionalParameter);
		mpBoundSql.sql(model.getDialectSql());
		mpBoundSql.parameterMappings(mappings);
	}

	private Object findParameter(Object parameterObject) {
		if (parameterObject != null) {
			if (parameterObject instanceof Map) {
				Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
				for (Map.Entry entry : parameterMap.entrySet()) {
					if (entry.getValue() != null && entry.getValue() instanceof SelectStatementProvider) {
						SelectStatementProvider selectStatementProvide = (SelectStatementProvider) entry.getValue();
						parameterMap.put("parameters", selectStatementProvide.getParameters());
						return parameterMap;
					}
				}
			}
		}
		return parameterObject;
	}
}
