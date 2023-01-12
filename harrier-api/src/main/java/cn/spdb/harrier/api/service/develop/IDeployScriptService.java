package cn.spdb.harrier.api.service.develop;

import cn.spdb.harrier.dao.entity.DyJobArrange;

public interface IDeployScriptService {
    boolean deployScript(DyJobArrange dyJobArrange);
}
