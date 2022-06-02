package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsSystem;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsSystemService {
    /**
     *新增
     */
    int add(UdsSystem record);

    /**
     *id删除
     */
    int delete(Integer id);

    /**
     *修改
     */
    int update(UdsSystem record);

    /**
     *查看详情
     */
    UdsSystem getDetail(Integer id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsSystem> listQuery();
    
    /**
     * 应用并发设置查询(分页)
     */
    Page<UdsSystem> selectAll(Page<UdsSystem> page,String platform, String systems,String use_platform,String server_role_name_group);
    
    /**
     * 更新应用并发设置
     */
    int updateConcurrencyState(UdsSystem udsSystem);

    /**
     * 查询平台下的应用
     */
    List<UdsSystem> selectByPlatform(String platform);
}