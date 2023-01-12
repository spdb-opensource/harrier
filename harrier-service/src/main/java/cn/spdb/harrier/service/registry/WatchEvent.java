package cn.spdb.harrier.service.registry;

import java.time.LocalDateTime;

import cn.spdb.harrier.common.enmus.RegistryState;
import cn.spdb.harrier.dao.entity.UdsServer;

public class WatchEvent {

	private UdsServer udsServer;
	private RegistryState state;

	public RegistryState state() {
		return state;
	}

	public Integer getId() {
		return udsServer.getId();
	}

	public String getServerRoleName() {
		return udsServer.getServerRoleName();
	}

	public String getServerRoleNameGroup() {
		return udsServer.getServerRoleNameGroup();
	}

	public String getNodeClusterType() {
		return udsServer.getNodeClusterType();
	}

	public String getServerName() {
		return udsServer.getServerName();
	}

	public String getIp() {
		return udsServer.getIp();
	}

	public Integer getPort() {
		return udsServer.getPort();
	}

	public LocalDateTime getLastStart() {
		return udsServer.getLastStart();
	}

	public LocalDateTime getUpdateTime() {
		return udsServer.getUpdateTime();
	}

	public LocalDateTime getLastEnd() {
		return udsServer.getLastEnd();
	}

	public Boolean getEnable() {
		return udsServer.getIsEnable();
	}

	public String getPara() {
		return udsServer.getPara();
	}

	public UdsServer getUdsServer() {
		return udsServer;
	}

	public void setUdsServer(UdsServer udsServer) {
		this.udsServer = udsServer;
	}

	public RegistryState getState() {
		return state;
	}

	public void setState(RegistryState state) {
		this.state = state;
	}

}
