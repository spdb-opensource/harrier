package cn.spdb.harrier.api.service.develop;

import java.io.IOException;
import java.util.List;

import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.DyJobAttributes;

public interface IYamlManagerService {
	
	/**
	 * yaml对象预处理
	 * @param jobAttributes
	 * @return
	 */
	JobYamlObject YamlPretreat(DyJobAttributes jobAttributes, List<JobStepConfig> jobStepList, List<JobDepRelations> jobDepRelationList);
	
	/**
	 * 生成yaml文件
	 * @param jobAttributes
	 * @throws IOException
	 */
	String GenerateYaml(JobYamlObject jobYamlObject) throws IOException;
	
	/**
	 * yaml文件处理成字符串对象
	 * @param filePath
	 * @return
	 */
	String JobYaml(String filePath);
	
	/**
	 * 封装作业编排对象
	 * @param jobYamlObject
	 * @param jobYamlStr
	 * @return
	 */
	DyJobArrange createDyJobArrange(JobYamlObject jobYamlObject, String jobYamlStr);
	
	/**
	 * 解析作业部署yaml，获取作业属性配置信息
	 * @param dyJobArrange
	 * @return
	 */
	DyJobAttributes parseDeployYaml(DyJobArrange dyJobArrange);

	/**
	 * 解析编排表中的yaml获取作业依赖关系列表
	 * @param dyJobArrange
	 * @return
	 */
	List<JobDepRelations> parseJobDep(DyJobArrange dyJobArrange);

	/**
	 * 解析编排表中的yaml获取作业脚本关系列表
	 * @param dyJobArrange
	 * @return
	 */
	List<JobStepConfig> parseJobStep(DyJobArrange dyJobArrange);
	
}
