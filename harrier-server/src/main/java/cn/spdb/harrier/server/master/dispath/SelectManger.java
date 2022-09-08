package cn.spdb.harrier.server.master.dispath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.Host;
import cn.spdb.harrier.dao.entity.UdsSystem;
import cn.spdb.harrier.server.entity.WorkingInfo;
import cn.spdb.harrier.server.master.dispath.select.HostWeight;
import cn.spdb.harrier.server.master.dispath.select.LowerWeightRoundRobin;
import cn.spdb.harrier.server.master.dispath.select.RandomSelector;
import cn.spdb.harrier.server.master.dispath.select.RoundWeightSelector;
import cn.spdb.harrier.server.master.dispath.select.Selector;

public class SelectManger {

	private static class SelectMangerIner {
		private static SelectManger instance = new SelectManger();
	}

	public static SelectManger getInstance() {
		return SelectMangerIner.instance;
	}

	public Host select(Collection<WorkingInfo> source, UdsSystem udsSystem) {
		ArrayList<HostWeight> collection = new ArrayList<HostWeight>();
		int index = udsSystem.getSelect();
		String selectPro = udsSystem.getSelectPro();
		Select enumy = Select.getSelectEnum(index);
		if (StringUtils.isBlank(selectPro)) {
			source.forEach(info -> {
				HostWeight hostWeight = new HostWeight();
				hostWeight.setHost(info.getHost());
				hostWeight.setUpdateTime(info.getUpdate());
				hostWeight.setWeight(info.getJobNumMax());
				collection.add(hostWeight);
			});
		} else {
			source.forEach(info -> {
				HostWeight hostWeight = new HostWeight();
				hostWeight.setHost(info.getHost());
				hostWeight.setUpdateTime(info.getUpdate());
				int weight = enumy.equals(Select.LOWER_WEIGHT) ? info.getHostWeight(udsSystem.get(Constants.CPU),
						udsSystem.get(Constants.MEM), udsSystem.get(Constants.LOAD_AVERAGE))
						: udsSystem.get(info.getId().toString());
				hostWeight.setWeight(weight);
				collection.add(hostWeight);
			});

		}
		HostWeight hostWeight = select(collection, enumy);
		if (hostWeight != null) {
			return hostWeight.getHost();
		}
		return null;
	}

	public HostWeight select(Collection<HostWeight> source, int index) {
		return select(source, Select.getSelectEnum(index));
	}

	public HostWeight select(Collection<HostWeight> source, Select enumy) {
		List<HostWeight> hosts = new ArrayList<>(source);
		return enumy.select.select((Collection<HostWeight>) hosts);
	}

	public enum Select {

		RANDMON(new RandomSelector()), ROUND(new RoundWeightSelector()), LOWER_WEIGHT(new LowerWeightRoundRobin());

		private Selector<HostWeight> select = null;

		private Select(Selector<HostWeight> select) {
			this.select = select;
		}

		public static Select getSelectEnum(int index) {
			return index > Select.values().length ? RANDMON : Select.values()[index];
		}

	}

}
