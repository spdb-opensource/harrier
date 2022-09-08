package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MUserSystems {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.systems_id")
    private Integer systemsId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.permissions")
    private String permissions;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.user_id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.systems_id")
    public Integer getSystemsId() {
        return systemsId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.systems_id")
    public void setSystemsId(Integer systemsId) {
        this.systemsId = systemsId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.permissions")
    public String getPermissions() {
        return permissions;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user_systems.permissions")
    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }
}