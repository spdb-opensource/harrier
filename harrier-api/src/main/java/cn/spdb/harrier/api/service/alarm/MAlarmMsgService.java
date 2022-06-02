package cn.spdb.harrier.api.service.alarm;

import cn.spdb.harrier.dao.entity.MAlarmMsg;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface MAlarmMsgService {
    /**
     *新增
     */
    int add(MAlarmMsg record);

    /**
     *id删除
     */
    int delete(Integer id);

    /**
     *修改
     */
    int update(MAlarmMsg record);

    /**
     *查看详情
     */
    MAlarmMsg getDetail(Integer id);

    /**
     *未完成的列表查询，待完善
     */
    List<MAlarmMsg> listQuery();

	Page<MAlarmMsg> search(Page<MAlarmMsg> page, String platfrom, String system, String code);
}