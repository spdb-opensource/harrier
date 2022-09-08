package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MUser {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_code")
    private String userCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_pwd")
    private String userPwd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_email")
    private String userEmail;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_phone")
    private String userPhone;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.emp_id")
    private String empId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_user")
    private String createUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_time")
    private LocalDateTime updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_user")
    private String updateUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_expired")
    private Boolean isExpired;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_locked")
    private Boolean isLocked;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_enable")
    private Boolean isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_single")
    private Boolean isSingle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_modify_pwd")
    private Boolean isModifyPwd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.pwd_wrong_count")
    private Integer pwdWrongCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.remark")
    private String remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_code")
    public String getUserCode() {
        return userCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_code")
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_name")
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_pwd")
    public String getUserPwd() {
        return userPwd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_pwd")
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_email")
    public String getUserEmail() {
        return userEmail;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_email")
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_phone")
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.emp_id")
    public String getEmpId() {
        return empId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.emp_id")
    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_user")
    public String getCreateUser() {
        return createUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_user")
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_user")
    public String getUpdateUser() {
        return updateUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.update_user")
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_expired")
    public Boolean getIsExpired() {
        return isExpired;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_expired")
    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_locked")
    public Boolean getIsLocked() {
        return isLocked;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_locked")
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_enable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_enable")
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_single")
    public Boolean getIsSingle() {
        return isSingle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_single")
    public void setIsSingle(Boolean isSingle) {
        this.isSingle = isSingle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_modify_pwd")
    public Boolean getIsModifyPwd() {
        return isModifyPwd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.is_modify_pwd")
    public void setIsModifyPwd(Boolean isModifyPwd) {
        this.isModifyPwd = isModifyPwd;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.pwd_wrong_count")
    public Integer getPwdWrongCount() {
        return pwdWrongCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.pwd_wrong_count")
    public void setPwdWrongCount(Integer pwdWrongCount) {
        this.pwdWrongCount = pwdWrongCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.remark")
    public String getRemark() {
        return remark;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.remark")
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}