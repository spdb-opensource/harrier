package cn.spdb.harrier.api.service.alarm;

import cn.spdb.harrier.dao.entity.MAlarmUserGroup;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface MAlarmUserGroupService {
    /**
     *新增
     */
    int add(MAlarmUserGroup record);

    /**
     *id删除
     */
    int delete(Integer id);

    /**
     *修改
     */
    int update(MAlarmUserGroup record);

    /**
     *查看详情
     */
    MAlarmUserGroup getDetail(Integer id);

    /**
     *未完成的列表查询，待完善
     */
    List<MAlarmUserGroup> listQuery();

	Page<MAlarmUserGroup> search(Page<MAlarmUserGroup> page, String groupName);

	List<MAlarmUserGroup> getDetailByGroupName(String groupName);
}