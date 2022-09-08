package cn.spdb.harrier.dao.entity;

import javax.annotation.Generated;

public class UdsJobWeightLimit {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.335+08:00", comments = "Source field: uds_job_weight_limit.id")
	private Long id;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_type")
	private Integer limitType;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_value")
	private Integer limitValue;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.server_ids")
	private String serverIds;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.time_winds")
	private String timeWinds;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.des")
	private String des;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.34+08:00", comments = "Source field: uds_job_weight_limit.id")
	public Long getId() {
		return id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.id")
	public void setId(Long id) {
		this.id = id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_type")
	public Integer getLimitType() {
		return limitType;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_type")
	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_value")
	public Integer getLimitValue() {
		return limitValue;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.limit_value")
	public void setLimitValue(Integer limitValue) {
		this.limitValue = limitValue;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.server_ids")
	public String getServerIds() {
		return serverIds;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.server_ids")
	public void setServerIds(String serverIds) {
		this.serverIds = serverIds == null ? null : serverIds.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.time_winds")
	public String getTimeWinds() {
		return timeWinds;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.341+08:00", comments = "Source field: uds_job_weight_limit.time_winds")
	public void setTimeWinds(String timeWinds) {
		this.timeWinds = timeWinds == null ? null : timeWinds.trim();
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.342+08:00", comments = "Source field: uds_job_weight_limit.des")
	public String getDes() {
		return des;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2021-12-29T09:50:02.342+08:00", comments = "Source field: uds_job_weight_limit.des")
	public void setDes(String des) {
		this.des = des == null ? null : des.trim();
	}

	@Override
	public String toString() {
		return "UdsJobWeightLimit [limitType=" + limitType + ", limitValue=" + limitValue + ", serverIds=" + serverIds
				+ ", timeWinds=" + timeWinds + "]";
	}
}