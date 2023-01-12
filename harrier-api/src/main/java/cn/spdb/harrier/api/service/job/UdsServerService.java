package cn.spdb.harrier.api.service.job;

import cn.spdb.harrier.dao.entity.UdsServer;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UdsServerService {
    /**
     *新增
     */
    int add(UdsServer record);

    /**
     *id删除
     */
    int delete(Integer id);

    /**
     *修改
     */
    int update(UdsServer record);

    /**
     *查看详情
     */
    UdsServer getDetail(Integer id);

    /**
     *未完成的列表查询，待完善
     */
    List<UdsServer> listQuery();
    
    /**
     * 节点管理查询所有节点(分页)
     */
    Page<UdsServer> selectAll(Page<UdsServer> page,String servername);
    
    /**
     * 更新节点设置
     */
    int setEnable(String serverName,Boolean is_enable);

    /**
     * 获取可用的工作节点
     * @return
     */
    List<UdsServer> getAvailableWorker();

    /**
     * 获取可用的主节点
     * @return
     */
    List<UdsServer> getAvailableMaster();
}