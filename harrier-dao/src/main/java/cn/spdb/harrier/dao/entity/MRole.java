package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MRole {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.9+08:00", comments="Source field: m_role.role_id")
    private Integer roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.role_name")
    private String roleName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_user")
    private String createUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_time")
    private LocalDateTime updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_user")
    private String updateUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.905+08:00", comments="Source field: m_role.role_id")
    public Integer getRoleId() {
        return roleId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.role_id")
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.role_name")
    public String getRoleName() {
        return roleName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.role_name")
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_user")
    public String getCreateUser() {
        return createUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.create_user")
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_user")
    public String getUpdateUser() {
        return updateUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.update_user")
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.is_enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.is_enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.91+08:00", comments="Source field: m_role.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}