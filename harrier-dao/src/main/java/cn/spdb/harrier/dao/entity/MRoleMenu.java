package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class MRoleMenu {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.role_id")
    private Integer roleId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.menu_id")
    private Integer menuId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.role_id")
    public Integer getRoleId() {
        return roleId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.role_id")
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.menu_id")
    public Integer getMenuId() {
        return menuId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-25T14:18:49.965+08:00", comments="Source field: m_role_menu.menu_id")
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}