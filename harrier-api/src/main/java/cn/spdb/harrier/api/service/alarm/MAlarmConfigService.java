package cn.spdb.harrier.api.service.alarm;

import cn.spdb.harrier.dao.entity.MAlarmConfig;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface MAlarmConfigService {
    /**
     *新增
     */
    int add(MAlarmConfig record);

    /**
     *id删除
     */
    int delete(Integer id,String platfrom);

    /**
     *修改
     */
    int update(MAlarmConfig record);

    /**
     *查看详情
     */
    MAlarmConfig getDetail(Integer id);

    /**
     *未完成的列表查询，待完善
     */
    List<MAlarmConfig> listQuery();

	Page<MAlarmConfig> search(Page<MAlarmConfig> page, String platfrom, String system, String job, String code);

	Page<MAlarmConfig> searchJobConfig(Page<MAlarmConfig> page, String platfrom, String system, String job, String code);
}