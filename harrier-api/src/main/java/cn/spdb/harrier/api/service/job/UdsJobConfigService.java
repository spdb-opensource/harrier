package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobConfig;
import java.util.List;

public interface UdsJobConfigService {
    /**
     *新增
     */
    int add(UdsJobConfig record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobConfig record);

    /**
     *查看详情
     */
    UdsJobConfig getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobConfig> listQuery();
    
	/**
	 * 启用禁用
	 */
    int setEnable(String platform_,String systems_,String job_,Boolean enable_);
    
	/**
	 * 置实置虚
	 */
	int setVirtual(String platform_,String systems_,String job_,Boolean virtual_);
}