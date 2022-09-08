
package cn.spdb.harrier.server.worker.conf;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.IPUtils;
import cn.spdb.harrier.common.utils.Symbol;

@Configuration
public class WorkerConfig {

	@Value("${worker.server.name:}")
	private String serverName;

	@Value("${worker.role.name:harrier}")
	private String roleName;

	@Value("${worker.role.group:dev}")
	private String roleGroup;

	@Value("${worker.listen.port:9393}")
	private int listenPort;

	@Value("${worker.exec.threads:100}")
	private int workerExecThreads;

	@Value("${worker.heartbeat.interval:10}")
	private int workerHeartbeatInterval;

	@Value("${worker.host.weight:100}")
	private int hostWeight;

	@Value("${worker.tenant.auto.create:false}")
	private boolean workerTenantAutoCreate;

	@Value("${worker.max.cpuload.avg:-1}")
	private int workerMaxCpuloadAvg;

	@Value("${worker.reserved.memory:0.3}")
	private double workerReservedMemory;

	@Value("${alert.listen.host:localhost}")
	private String alertListenHost;

	@Value("${harrier.logs.step.prex:}")
	private String logStepPrex;
	
	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public int getWorkerExecThreads() {
		return workerExecThreads;
	}

	public void setWorkerExecThreads(int workerExecThreads) {
		this.workerExecThreads = workerExecThreads;
	}

	public int getWorkerHeartbeatInterval() {
		return workerHeartbeatInterval;
	}

	public void setWorkerHeartbeatInterval(int workerHeartbeatInterval) {
		this.workerHeartbeatInterval = workerHeartbeatInterval;
	}

	public boolean getWorkerTenantAutoCreate() {
		return workerTenantAutoCreate;
	}

	public void setWorkerTenantAutoCreate(boolean workerTenantAutoCreate) {
		this.workerTenantAutoCreate = workerTenantAutoCreate;
	}

	public double getWorkerReservedMemory() {
		return workerReservedMemory;
	}

	public void setWorkerReservedMemory(double workerReservedMemory) {
		this.workerReservedMemory = workerReservedMemory;
	}

	public int getWorkerMaxCpuloadAvg() {
		if (workerMaxCpuloadAvg == -1) {
			return Constants.DEFAULT_WORKER_CPU_LOAD;
		}
		return workerMaxCpuloadAvg;
	}

	public void setWorkerMaxCpuloadAvg(int workerMaxCpuloadAvg) {
		this.workerMaxCpuloadAvg = workerMaxCpuloadAvg;
	}

	public int getHostWeight() {
		return hostWeight;
	}

	public void setHostWeight(int hostWeight) {
		this.hostWeight = hostWeight;
	}

	public String getAlertListenHost() {
		return alertListenHost;
	}

	public void setAlertListenHost(String alertListenHost) {
		this.alertListenHost = alertListenHost;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String name) {
		this.roleName = name;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String group) {
		this.roleGroup = group;
	}

	public String getServerName() {
		return ObjectUtils.isEmpty(serverName) ? IPUtils.getHostIp() + Symbol.MAO_HAO + String.valueOf(listenPort)
				: serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLogStepPrex() {
		return logStepPrex;
	}

	public void setLogStepPrex(String stepLogPrex) {
		this.logStepPrex = stepLogPrex;
	}

	
}