package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MMenu {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_id")
	private Integer menuId;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_name")
	private String menuName;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.parent_menu_id")
	private Integer parentMenuId;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_no")
	private Integer menuNo;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_url")
	private String menuUrl;

	private String menuIconUrl;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_flag")
	private Byte menuFlag;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_time")
	private LocalDateTime createTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_user")
	private String createUser;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_time")
	private LocalDateTime updateTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_user")
	private String updateUser;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.is_enable")
	private Boolean isEnable;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.remark")
	private String remark;
	
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.component")
	private String component;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_id")
	public Integer getMenuId() {
		return menuId;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_id")
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_name")
	public String getMenuName() {
		return menuName;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_name")
	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.parent_menu_id")
	public Integer getParentMenuId() {
		return parentMenuId;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.parent_menu_id")
	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_no")
	public Integer getMenuNo() {
		return menuNo;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_no")
	public void setMenuNo(Integer menuNo) {
		this.menuNo = menuNo;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_url")
	public String getMenuUrl() {
		return menuUrl;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_url")
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_flag")
	public Byte getMenuFlag() {
		return menuFlag;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.menu_flag")
	public void setMenuFlag(Byte menuFlag) {
		this.menuFlag = menuFlag;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_time")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_time")
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_user")
	public String getCreateUser() {
		return createUser;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.create_user")
	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_time")
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_time")
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.update_user")
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.is_enable")
	public Boolean getIsEnable() {
		return isEnable;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.is_enable")
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.remark")
	public String getRemark() {
		return remark;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.remark")
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getMenuIconUrl() {
		return menuIconUrl;
	}

	public void setMenuIconUrl(String menuIconUrl) {
		this.menuIconUrl = menuIconUrl;
	}
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.Component")
	public String getComponent() {
		return component;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-01-18T11:03:45.543+08:00", comments = "Source field: m_menu.Component")
	public void setComponent(String component) {
		this.component = component == null ? null : component.trim();
	}
	
	
}