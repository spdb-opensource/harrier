package cn.spdb.harrier.api.service.develop;

import java.util.List;

import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobConfig;
import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import cn.spdb.harrier.dao.entity.UdsJobDependency;
import cn.spdb.harrier.dao.entity.UdsJobSource;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;
import cn.spdb.harrier.dao.entity.UdsJobWeight;
import cn.spdb.harrier.dao.entity.UdsSystem;

/**
 * 获取正常批量表中的作业属性值，反向处理作业变更
 * 1. 属性配置变更
 * 2. 依赖信息变更
 * 3. 脚本信息变更
 * 4. 作业下线处理
 * @author guon2
 *
 */
public interface IJobConfigChangeService {
	
	UdsJob selectUdsJob(String platform, String systems, String job);
	
	UdsJobConfig selectUdsJobConfig(String platform, String systems, String job);
	
	List<UdsJobDateFrequency> selectUdsJobDateFrequency(String platform, String systems, String job);
	
	List<UdsJobDependency> selectUdsJobDependency(String platform, String systems, String job);
	
	UdsJobSource selectUdsJobSource(String platform, String systems, String job);
	
	List<UdsJobStep> selectUdsJobStep(String platform, String systems, String job);
	
	List<UdsJobTimeTrigger> selectUdsJobTimeTrigger(String platform, String systems, String job);
	
	List<UdsJobWeight> selectUdsJobWeight(String platform, String systems, String job);
	
	UdsSystem selectUdsSystem(String platform, String systems);
	
	JobYamlObject createJobYamloJobYamlObject(String platform, String systems, String job, int taskStatus);
}
