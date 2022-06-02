package cn.spdb.harrier.api.service.develop;

import java.util.List;

import cn.spdb.harrier.dao.entity.DyJobArrange;

public interface IDeploySqlService {
	/**
	 * 根据作业编排信息生成知识库部署SQL
	 * @param dyJobArrange
	 * @return
	 */
	boolean deploySqlGenerate(DyJobArrange dyJobArrange);
	
	/**
	 * 执行sql
	 * @param contextList
	 * @return
	 */
	int[] sqlExecute(List<String> contextList);

	/**
	 * 部署回退
	 * @param dyJobArrange
	 * @return
	 */
	boolean rollBack(DyJobArrange dyJobArrange);
}
