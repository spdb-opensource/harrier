package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MUserDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source Table: m_user")
    public static final MUser MUser = new MUser();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_id")
    public static final SqlColumn<Long> userId = MUser.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_code")
    public static final SqlColumn<String> userCode = MUser.userCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_name")
    public static final SqlColumn<String> userName = MUser.userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_pwd")
    public static final SqlColumn<String> userPwd = MUser.userPwd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_email")
    public static final SqlColumn<String> userEmail = MUser.userEmail;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.user_phone")
    public static final SqlColumn<String> userPhone = MUser.userPhone;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.emp_id")
    public static final SqlColumn<String> empId = MUser.empId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source field: m_user.create_time")
    public static final SqlColumn<LocalDateTime> createTime = MUser.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.create_user")
    public static final SqlColumn<String> createUser = MUser.createUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = MUser.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.update_user")
    public static final SqlColumn<String> updateUser = MUser.updateUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.is_expired")
    public static final SqlColumn<Boolean> isExpired = MUser.isExpired;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.is_locked")
    public static final SqlColumn<Boolean> isLocked = MUser.isLocked;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.is_enable")
    public static final SqlColumn<Boolean> isEnable = MUser.isEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.is_single")
    public static final SqlColumn<Boolean> isSingle = MUser.isSingle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.is_modify_pwd")
    public static final SqlColumn<Boolean> isModifyPwd = MUser.isModifyPwd;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.pwd_wrong_count")
    public static final SqlColumn<Integer> pwdWrongCount = MUser.pwdWrongCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.534+08:00", comments="Source field: m_user.remark")
    public static final SqlColumn<String> remark = MUser.remark;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-01-18T11:03:45.528+08:00", comments="Source Table: m_user")
    public static final class MUser extends SqlTable {
        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<String> userCode = column("user_code", JDBCType.VARCHAR);

        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> userPwd = column("user_pwd", JDBCType.VARCHAR);

        public final SqlColumn<String> userEmail = column("user_email", JDBCType.VARCHAR);

        public final SqlColumn<String> userPhone = column("user_phone", JDBCType.VARCHAR);

        public final SqlColumn<String> empId = column("emp_id", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> createUser = column("create_user", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> updateUser = column("update_user", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> isExpired = column("is_expired", JDBCType.BIT);

        public final SqlColumn<Boolean> isLocked = column("is_locked", JDBCType.BIT);

        public final SqlColumn<Boolean> isEnable = column("is_enable", JDBCType.BIT);

        public final SqlColumn<Boolean> isSingle = column("is_single", JDBCType.BIT);

        public final SqlColumn<Boolean> isModifyPwd = column("is_modify_pwd", JDBCType.BIT);

        public final SqlColumn<Integer> pwdWrongCount = column("pwd_wrong_count", JDBCType.INTEGER);

        public final SqlColumn<String> remark = column("remark", JDBCType.VARCHAR);

        public MUser() {
            super("m_user");
        }
    }
}