package cn.com.spdb.uds.core.filter.pending;

import cn.com.spdb.uds.core.filter.InterfaceFilter;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;
/**
 * Pending 等待过滤
 * @author T-luzl
 *
 */
public abstract class AbstractPendingFilter implements InterfaceFilter {
	public abstract boolean checkDependency(UdsJobBean udsJobBean,UdsSystemBean udsSystemBean);
}
