package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.service.job.UdsJobDateFrequencyService;
import cn.spdb.harrier.dao.entity.UdsJobDateFrequency;
import cn.spdb.harrier.dao.mapper.UdsJobDateFrequencyMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UdsJobDateFrequencyServiceImpl implements UdsJobDateFrequencyService {
    @Autowired
    private UdsJobDateFrequencyMapper mapper;

    /**
     *新增
     */
    @Override
    public int add(UdsJobDateFrequency record) {
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
    public int update(UdsJobDateFrequency record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJobDateFrequency getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJobDateFrequency> listQuery() {
        return mapper.select( null );
    }

    /**
     * 作业列表-查询作业频度
     */
	@Override
	public UdsJobDateFrequency selectJobFrequency(String platform, String system, String job) {
		UdsJobDateFrequency udsJobDateFrequency = mapper.selectJobFrequency(platform, system, job).orElse(null);
		String cron = udsJobDateFrequency.getCrontab();
		String[] cronArry = cron.split(" ");
		if (cronArry.length == 7) {
			udsJobDateFrequency.setDay(cronArry[3]);
			udsJobDateFrequency.setMonth(cronArry[4]);
			udsJobDateFrequency.setWeek(cronArry[5]);
			udsJobDateFrequency.setYear(cronArry[6]);
		}
		return udsJobDateFrequency;
	}
}