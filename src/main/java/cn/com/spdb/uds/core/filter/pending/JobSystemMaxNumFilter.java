package cn.com.spdb.uds.core.filter.pending;

import cn.com.spdb.uds.core.master.MasterManager;
import cn.com.spdb.uds.db.bean.UdsJobBean;
import cn.com.spdb.uds.db.bean.UdsSystemBean;

public class JobSystemMaxNumFilter extends AbstractPendingFilter {

	@Override
	public boolean checkDependency(UdsJobBean udsJobBean, UdsSystemBean udsSystemBean) {
		// 该平台应用最大并行数
		int max = udsSystemBean.getMax_run_job();
		int num = MasterManager.getInstance().getSystemJobNum(udsSystemBean.getPlatform(), udsSystemBean.getSystem());
		if (num >= max) {
			return false;
		}
		return true;
	}
}
