package cn.spdb.harrier.api.service.system;

import cn.spdb.harrier.dao.entity.MDictionary;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface MDictionaryService {
    /**
     *新增
     */
    int add(MDictionary record);

    /**
     *id删除
     */
    int delete(Long id);

    /**
     *修改
     */
    int update(MDictionary record);

    /**
     *查看详情
     */
    MDictionary getDetail(Long id);

    /**
     *未完成的列表查询，待完善
     */
    List<MDictionary> listQuery();

	Page<MDictionary> search(Page<MDictionary> page);
}