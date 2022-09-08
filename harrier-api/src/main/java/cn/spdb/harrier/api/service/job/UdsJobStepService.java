package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.rpc.transport.File.FileMessage;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsJobStepService {
	/**
	 * 新增
	 */
	int add(UdsJobStep record);

	/**
	 * id删除
	 */
	int delete(Long id);

	/**
	 * 修改
	 */
	int update(UdsJobStep record);

	/**
	 * 查看详情
	 */
	UdsJobStep getDetail(Long id);

	/**
	 * 未完成的列表查询，待完善
	 */
	List<UdsJobStep> listQuery();

	Page<UdsJobStep> selectJobStepList(Page<UdsJobStep> page, String platform, String system, String job);

	FileMessage downloadResource(URI stepURI, Long streamId, Long pos, Long size);

	UdsJobStep downloadJobStep(String platform, String system, String job, Byte index);
}