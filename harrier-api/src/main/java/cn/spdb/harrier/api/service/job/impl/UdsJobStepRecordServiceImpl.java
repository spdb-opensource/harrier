package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobStepRecordService;
import cn.spdb.harrier.dao.entity.UdsJobStepRecord;
import cn.spdb.harrier.dao.mapper.UdsJobStepRecordMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobStepRecordServiceImpl implements UdsJobStepRecordService {
    @Autowired
    private UdsJobStepRecordMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobStepRecord record) {
        return mapper.insertSelective( record );
    }

    /**
     *id删除
     */
    @Override
    public int delete(Long id) {
        return mapper.deleteByPrimaryKey( id );
    }

    /**
     *修改
     */
    @Override
    public int update(UdsJobStepRecord record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobStepRecord getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobStepRecord> listQuery() {
        return mapper.select( c -> c );
    }
    
    /**
     * 作业列表-查询作业脚本运行记录
     */
	@Override
	public List<UdsJobStepRecord> selectJobStepRecord(Long job_record_id) {
		return mapper.selectJobStepRecord(job_record_id);
	}
}