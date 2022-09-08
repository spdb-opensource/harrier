package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsJobDependency;
import java.util.List;

public interface UdsJobDependencyService {
    /**
     *新增
     */
    int add(UdsJobDependency record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(UdsJobDependency record);

    /**
     *查看详情
     */
    UdsJobDependency getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsJobDependency> listQuery();

	/**
	 * 启用禁用依赖
	 */
	int setDepEnable(String pjob, String job_, Boolean enable_);
}