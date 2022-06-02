package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobRecordService;
import cn.spdb.harrier.dao.entity.UdsJobRecord;
import cn.spdb.harrier.dao.mapper.UdsJobRecordMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobRecordServiceImpl implements UdsJobRecordService {
    @Autowired
    private UdsJobRecordMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobRecord record) {
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
    public int update(UdsJobRecord record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobRecord getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobRecord> listQuery() {
        return mapper.select( null );
    }

	@Override
	public List<UdsJobRecord> selectJobRecord(String platform_, String system_, String job_) {
		return mapper.selectJobRecord(platform_, system_, job_);
	}
}