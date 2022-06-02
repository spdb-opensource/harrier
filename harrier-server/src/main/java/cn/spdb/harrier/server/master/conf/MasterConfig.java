
package cn.spdb.harrier.server.master.conf;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.uitls.IPUtils;
import cn.spdb.harrier.common.uitls.Symbol;

@Component
public class MasterConfig {

	@Value("${master.server.name:}")
	private String serverName;
	
	@Value("${master.role.name:harrier}")
	private String roleName;
	
	@Value("${master.role.group:dev}")
	private String roleGroup;
	
    @Value("${master.listen.port:9383}")
    private int listenPort;

    @Value("${master.exec.threads:100}")
    private int masterExecThreads;

    @Value("${master.exec.task.num:20}")
    private int masterExecTaskNum;

    @Value("${master.dispatch.task.num:3}")
    private int masterDispatchTaskNumber;

    @Value("${master.host.selector:LowerWeight}")
    private String hostSelector;

    @Value("${master.heartbeat.interval:10}")
    private int masterHeartbeatInterval;

    @Value("${master.state.wheel.interval:5}")
    private int stateWheelInterval;

    @Value("${master.task.commit.retryTimes:5}")
    private int masterTaskCommitRetryTimes;

    @Value("${master.task.commit.interval:1000}")
    private int masterTaskCommitInterval;

    @Value("${master.max.cpuload.avg:-1}")
    private double masterMaxCpuloadAvg;

    @Value("${master.reserved.memory:0.3}")
    private double masterReservedMemory;

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getHostSelector() {
        return hostSelector;
    }

    public void setHostSelector(String hostSelector) {
        this.hostSelector = hostSelector;
    }

    public int getMasterExecThreads() {
        return masterExecThreads;
    }

    public void setMasterExecThreads(int masterExecThreads) {
        this.masterExecThreads = masterExecThreads;
    }

    public int getMasterExecTaskNum() {
        return masterExecTaskNum;
    }

    public void setMasterExecTaskNum(int masterExecTaskNum) {
        this.masterExecTaskNum = masterExecTaskNum;
    }

    public int getMasterHeartbeatInterval() {
        return masterHeartbeatInterval;
    }

    public void setMasterHeartbeatInterval(int masterHeartbeatInterval) {
        this.masterHeartbeatInterval = masterHeartbeatInterval;
    }

    public int getMasterTaskCommitRetryTimes() {
        return masterTaskCommitRetryTimes;
    }

    public void setMasterTaskCommitRetryTimes(int masterTaskCommitRetryTimes) {
        this.masterTaskCommitRetryTimes = masterTaskCommitRetryTimes;
    }

    public int getMasterTaskCommitInterval() {
        return masterTaskCommitInterval;
    }

    public void setMasterTaskCommitInterval(int masterTaskCommitInterval) {
        this.masterTaskCommitInterval = masterTaskCommitInterval;
    }

    public double getMasterMaxCpuloadAvg() {
        if (masterMaxCpuloadAvg == -1) {
            return Constants.DEFAULT_MASTER_CPU_LOAD;
        }
        return masterMaxCpuloadAvg;
    }

    public void setMasterMaxCpuloadAvg(double masterMaxCpuloadAvg) {
        this.masterMaxCpuloadAvg = masterMaxCpuloadAvg;
    }

    public double getMasterReservedMemory() {
        return masterReservedMemory;
    }

    public void setMasterReservedMemory(double masterReservedMemory) {
        this.masterReservedMemory = masterReservedMemory;
    }

    public int getMasterDispatchTaskNumber() {
        return masterDispatchTaskNumber;
    }

    public void setMasterDispatchTaskNumber(int masterDispatchTaskNumber) {
        this.masterDispatchTaskNumber = masterDispatchTaskNumber;
    }

    public int getStateWheelInterval() {
        return this.stateWheelInterval;
    }

    public void setStateWheelInterval(int stateWheelInterval) {
        this.stateWheelInterval = stateWheelInterval;
    }

	public String getServerName() {
		return ObjectUtils.isEmpty(serverName) ? IPUtils.getHostIp() + Symbol.MAO_HAO + String.valueOf(listenPort)
				: serverName;
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
    
    
}