package cn.spdb.harrier.dao.entity;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Generated;

import org.apache.commons.lang3.StringUtils;

import cn.spdb.harrier.common.uitls.Symbol;

public class UdsSystem {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.id")
	private Integer id;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.platform")
	private String platform;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.systems")
	private String systems;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.max_run_job")
	private Short maxRunJob;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select")
	private Byte selects;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select_pro")
	private String selectsPro;

	private transient HashMap<String, Integer> selectProMap = new HashMap<String, Integer>();

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.use_platform")
	private Boolean usePlatform;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.server_role_name_group")
	private String serverRoleNameGroup;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.des")
	private String des;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.id")
	public Integer getId() {
		return id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.id")
	public void setId(Integer id) {
		this.id = id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.platform")
	public String getPlatform() {
		return platform;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.platform")
	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.systems")
	public String getSystems() {
		return systems;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.systems")
	public void setSystems(String systems) {
		this.systems = systems == null ? null : systems.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.max_run_job")
	public Short getMaxRunJob() {
		return maxRunJob;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.max_run_job")
	public void setMaxRunJob(Short maxRunJob) {
		this.maxRunJob = maxRunJob;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select")
	public Byte getSelect() {
		return selects;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select")
	public void setSelect(Byte select) {
		this.selects = select;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select_pro")
	public String getSelectPro() {
		return selectsPro;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.select_pro")
	public void setSelectPro(String selectPro) {
		this.selectsPro = selectPro == null ? null : selectPro.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.use_platform")
	public Boolean getUsePlatform() {
		return usePlatform;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.use_platform")
	public void setUsePlatform(Boolean usePlatform) {
		this.usePlatform = usePlatform;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.server_role_name_group")
	public String getServerRoleNameGroup() {
		return serverRoleNameGroup;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.server_role_name_group")
	public void setServerRoleNameGroup(String serverRoleNameGroup) {
		this.serverRoleNameGroup = serverRoleNameGroup == null ? null : serverRoleNameGroup.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.des")
	public String getDes() {
		return des;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-28T10:08:54.246+08:00", comments = "Source field: uds_system.des")
	public void setDes(String des) {
		this.des = des == null ? null : des.trim();
	}

	public Integer get(String key) {
		if (!StringUtils.isBlank(selectsPro)) {
			if (selectProMap.isEmpty()) {
				String[] pros = selectsPro.split(Symbol.DOU_HAO_REG);
				Arrays.stream(pros).forEach(pro -> {
					String[] kv = pro.split(Symbol.MAO_HAO_REG);
					if (kv.length < 2) {
						return;
					}
					if (!kv[1].matches("[0-9]+")) {
						return;
					}
					selectProMap.put(kv[0], Integer.valueOf(kv[1]));
				});
			} else {
				return selectProMap.get(key);
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "UdsSystem [id=" + id + ", platform=" + platform + ", systems=" + systems + "]";
	}
	
	
	
}