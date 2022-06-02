package cn.spdb.harrier.alarm.config;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import cn.spdb.harrier.common.uitls.IPUtils;
import cn.spdb.harrier.common.uitls.Symbol;

@Configuration
public class AlarmConfig {
	
	@Value("${alarm.server.name:}")
	private String serverName;

	@Value("${alarm.role.name:harrier}")
	private String roleName;

	@Value("${alarm.role.group:dev}")
	private String roleGroup;

	@Value("${alarm.listen.port:9373}")
	private int listenPort;

	public int getListenPort() {
		return listenPort;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public String getServerName() {
		return ObjectUtils.isEmpty(serverName) ? IPUtils.getHostIp() + Symbol.MAO_HAO + String.valueOf(listenPort)
				: serverName;
	}

}
