package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UdsServer {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.607+08:00", comments="Source field: uds_server.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.607+08:00", comments="Source field: uds_server.server_role_name")
    private String serverRoleName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_role_name_group")
    private String serverRoleNameGroup;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.node_cluster_type")
    private String nodeClusterType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_name")
    private String serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.ip")
    private String ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.port")
    private Integer port;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_start")
    private LocalDateTime lastStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.update_time")
    private LocalDateTime updateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_end")
    private LocalDateTime lastEnd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.para")
    private String para;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.des")
    private String des;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.607+08:00", comments="Source field: uds_server.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.607+08:00", comments="Source field: uds_server.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.607+08:00", comments="Source field: uds_server.server_role_name")
    public String getServerRoleName() {
        return serverRoleName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_role_name")
    public void setServerRoleName(String serverRoleName) {
        this.serverRoleName = serverRoleName == null ? null : serverRoleName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_role_name_group")
    public String getServerRoleNameGroup() {
        return serverRoleNameGroup;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_role_name_group")
    public void setServerRoleNameGroup(String serverRoleNameGroup) {
        this.serverRoleNameGroup = serverRoleNameGroup == null ? null : serverRoleNameGroup.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.node_cluster_type")
    public String getNodeClusterType() {
        return nodeClusterType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.node_cluster_type")
    public void setNodeClusterType(String nodeClusterType) {
        this.nodeClusterType = nodeClusterType == null ? null : nodeClusterType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_name")
    public String getServerName() {
        return serverName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.server_name")
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.ip")
    public String getIp() {
        return ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.ip")
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.port")
    public Integer getPort() {
        return port;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.port")
    public void setPort(Integer port) {
        this.port = port;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_start")
    public LocalDateTime getLastStart() {
        return lastStart;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_start")
    public void setLastStart(LocalDateTime lastStart) {
        this.lastStart = lastStart;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_end")
    public LocalDateTime getLastEnd() {
        return lastEnd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.608+08:00", comments="Source field: uds_server.last_end")
    public void setLastEnd(LocalDateTime lastEnd) {
        this.lastEnd = lastEnd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.is_enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.is_enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.para")
    public String getPara() {
        return para;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.para")
    public void setPara(String para) {
        this.para = para == null ? null : para.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.des")
    public String getDes() {
        return des;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-04T10:53:06.609+08:00", comments="Source field: uds_server.des")
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UdsServer other = (UdsServer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UdsServer [id=" + id + ", serverRoleName=" + serverRoleName + ", serverRoleNameGroup="
				+ serverRoleNameGroup + ", nodeClusterType=" + nodeClusterType + ", serverName=" + serverName + ", ip="
				+ ip + ", port=" + port + "]";
	}
    
	
}