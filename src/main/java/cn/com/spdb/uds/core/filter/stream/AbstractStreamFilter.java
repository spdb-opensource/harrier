package cn.com.spdb.uds.core.filter.stream;

import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.db.bean.UdsJobBean;
/**
 *作业触发过滤
 * @author T-luzl
 *
 */
public abstract class AbstractStreamFilter {
	public abstract boolean check(SignalFileInfo signalFileInfo, UdsJobBean udsJobBean);
}
