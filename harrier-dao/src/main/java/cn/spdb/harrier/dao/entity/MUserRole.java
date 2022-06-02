package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MUserRole {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.role_id")
    private Integer roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.user_id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.role_id")
    public Integer getRoleId() {
        return roleId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.538+08:00", comments="Source field: m_user_role.role_id")
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}