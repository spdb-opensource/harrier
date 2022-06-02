package cn.spdb.harrier.dao.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MOperatLog {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.user_code")
    private String userCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_type")
    private String operatType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat")
    private String operat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.ip")
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_date")
    private LocalDateTime operatDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_content")
    private String operatContent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.user_code")
    public String getUserCode() {
        return userCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.user_code")
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_type")
    public String getOperatType() {
        return operatType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_type")
    public void setOperatType(String operatType) {
        this.operatType = operatType == null ? null : operatType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat")
    public String getOperat() {
        return operat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat")
    public void setOperat(String operat) {
        this.operat = operat == null ? null : operat.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.ip")
    public String getIp() {
        return ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.ip")
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_date")
    public LocalDateTime getOperatDate() {
        return operatDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_date")
    public void setOperatDate(LocalDateTime operatDate) {
        this.operatDate = operatDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_content")
    public String getOperatContent() {
        return operatContent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-20T15:13:15.982+08:00", comments="Source field: m_operat_log.operat_content")
    public void setOperatContent(String operatContent) {
        this.operatContent = operatContent == null ? null : operatContent.trim();
    }
}