package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobTimeTriggerService;
import cn.spdb.harrier.dao.entity.UdsJobTimeTrigger;
import cn.spdb.harrier.dao.mapper.UdsJobTimeTriggerMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobTimeTriggerServiceImpl implements UdsJobTimeTriggerService {
    @Autowired
    private UdsJobTimeTriggerMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobTimeTrigger record) {
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
    public int update(UdsJobTimeTrigger record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobTimeTrigger getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobTimeTrigger> listQuery() {
        return mapper.select( null );
    }

    @Override
    public UdsJobTimeTrigger selectJobTrigger(String platform, String system, String job) {
    	UdsJobTimeTrigger udsJobTimeTrigger = mapper.selectJobTrigger(platform, system, job).orElse(null);
    	String cron = udsJobTimeTrigger.getCrontab();
		String[] cronArry = cron.split(" ");
		if (cronArry.length == 7) {
			udsJobTimeTrigger.setSecond(cronArry[0]);
			udsJobTimeTrigger.setMinute(cronArry[1]);
			udsJobTimeTrigger.setHour(cronArry[2]);
			udsJobTimeTrigger.setDay(cronArry[3]);
			udsJobTimeTrigger.setDay(cronArry[3]);
			udsJobTimeTrigger.setMonth(cronArry[4]);
			udsJobTimeTrigger.setWeek(cronArry[5]);
			udsJobTimeTrigger.setYear(cronArry[6]);
		}
    	return udsJobTimeTrigger;
    }
}