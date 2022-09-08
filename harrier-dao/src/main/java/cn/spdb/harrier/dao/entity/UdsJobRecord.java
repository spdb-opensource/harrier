package cn.spdb.harrier.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class UdsJobRecord {
	@JsonSerialize(using = ToStringSerializer.class)
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.id")
    private Long id;

    private Long complementId;
    
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.platform")
    private String platform;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.systems")
    private String systems;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job")
    private String job;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_type")
    private String jobType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.server_name")
    private String serverName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_date")
    private LocalDate jobDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.last_status")
    private String lastStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.pending_time")
    private LocalDateTime pendingTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.dispatcher_time")
    private LocalDateTime dispatcherTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.start_time")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.end_time")
    private LocalDateTime endTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.stream_type")
    private Byte streamType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.virtual_enable")
    private Boolean virtualEnable;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.multi_batch")
    private Integer multiBatch;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.num_times")
    private Long numTimes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.id")
    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getComplementId() {
		return complementId;
	}

	public void setComplementId(Long complementId) {
		this.complementId = complementId;
	}

	@Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.platform")
    public String getPlatform() {
        return platform;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.794+08:00", comments="Source field: uds_job_record.platform")
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.systems")
    public String getSystems() {
        return systems;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.systems")
    public void setSystems(String systems) {
        this.systems = systems == null ? null : systems.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job")
    public String getJob() {
        return job;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job")
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_type")
    public String getJobType() {
        return jobType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_type")
    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.server_name")
    public String getServerName() {
        return serverName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.server_name")
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_date")
    public LocalDate getJobDate() {
        return jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.job_date")
    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.last_status")
    public String getLastStatus() {
        return lastStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.last_status")
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus == null ? null : lastStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.pending_time")
    public LocalDateTime getPendingTime() {
        return pendingTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.pending_time")
    public void setPendingTime(LocalDateTime pendingTime) {
        this.pendingTime = pendingTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.dispatcher_time")
    public LocalDateTime getDispatcherTime() {
        return dispatcherTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.dispatcher_time")
    public void setDispatcherTime(LocalDateTime dispatcherTime) {
        this.dispatcherTime = dispatcherTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.795+08:00", comments="Source field: uds_job_record.start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.start_time")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.end_time")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.stream_type")
    public Byte getStreamType() {
        return streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.stream_type")
    public void setStreamType(Byte streamType) {
        this.streamType = streamType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.virtual_enable")
    public Boolean getVirtualEnable() {
        return virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.virtual_enable")
    public void setVirtualEnable(Boolean virtualEnable) {
        this.virtualEnable = virtualEnable;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.multi_batch")
    public Integer getMultiBatch() {
        return multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.multi_batch")
    public void setMultiBatch(Integer multiBatch) {
        this.multiBatch = multiBatch;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.num_times")
    public Long getNumTimes() {
        return numTimes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2022-03-08T15:50:14.796+08:00", comments="Source field: uds_job_record.num_times")
    public void setNumTimes(Long numTimes) {
        this.numTimes = numTimes;
    }
}