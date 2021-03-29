package cn.com.spdb.uds.core.filter.receiver;

import cn.com.spdb.uds.core.bean.SignalFileInfo;
import cn.com.spdb.uds.core.filter.InterfaceFilter;
import cn.com.spdb.uds.db.bean.UdsJobBean;
/**
 * 信号文件触发过滤
 * @author T-luzl
 *
 */
public abstract class AbstractReceiverFilter implements InterfaceFilter {

	public abstract boolean check(SignalFileInfo signalFileInfo, UdsJobBean udsJobBean);
}
