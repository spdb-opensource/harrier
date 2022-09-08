package cn.spdb.harrier.dao.mapper;

import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UdsJobRecordDynamicSqlSupport {
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.796+08:00", comments = "Source Table: uds_job_record")
	public static final UdsJobRecord udsJobRecord = new UdsJobRecord();

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.id")
	public static final SqlColumn<Long> id = udsJobRecord.id;
	public static final SqlColumn<Long> complementId = udsJobRecord.complementId;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.platform")
	public static final SqlColumn<String> platform = udsJobRecord.platform;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.systems")
	public static final SqlColumn<String> systems = udsJobRecord.systems;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.job")
	public static final SqlColumn<String> job = udsJobRecord.job;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.job_type")
	public static final SqlColumn<String> jobType = udsJobRecord.jobType;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.server_name")
	public static final SqlColumn<String> serverName = udsJobRecord.serverName;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.job_date")
	public static final SqlColumn<LocalDate> jobDate = udsJobRecord.jobDate;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.last_status")
	public static final SqlColumn<String> lastStatus = udsJobRecord.lastStatus;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.797+08:00", comments = "Source field: uds_job_record.pending_time")
	public static final SqlColumn<LocalDateTime> pendingTime = udsJobRecord.pendingTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.dispatcher_time")
	public static final SqlColumn<LocalDateTime> dispatcherTime = udsJobRecord.dispatcherTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.start_time")
	public static final SqlColumn<LocalDateTime> startTime = udsJobRecord.startTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.end_time")
	public static final SqlColumn<LocalDateTime> endTime = udsJobRecord.endTime;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.stream_type")
	public static final SqlColumn<Byte> streamType = udsJobRecord.streamType;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.virtual_enable")
	public static final SqlColumn<Boolean> virtualEnable = udsJobRecord.virtualEnable;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.multi_batch")
	public static final SqlColumn<Integer> multiBatch = udsJobRecord.multiBatch;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.798+08:00", comments = "Source field: uds_job_record.num_times")
	public static final SqlColumn<Long> numTimes = udsJobRecord.numTimes;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", date = "2022-03-08T15:50:14.796+08:00", comments = "Source Table: uds_job_record")
	public static final class UdsJobRecord extends SqlTable {

		public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
		public final SqlColumn<Long> complementId = column("complement_id", JDBCType.BIGINT);

		public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);

		public final SqlColumn<String> systems = column("systems", JDBCType.VARCHAR);

		public final SqlColumn<String> job = column("job", JDBCType.VARCHAR);

		public final SqlColumn<String> jobType = column("job_type", JDBCType.VARCHAR);

		public final SqlColumn<String> serverName = column("server_name", JDBCType.VARCHAR);

		public final SqlColumn<LocalDate> jobDate = column("job_date", JDBCType.DATE);

		public final SqlColumn<String> lastStatus = column("last_status", JDBCType.VARCHAR);

		public final SqlColumn<LocalDateTime> pendingTime = column("pending_time", JDBCType.TIMESTAMP);

		public final SqlColumn<LocalDateTime> dispatcherTime = column("dispatcher_time", JDBCType.TIMESTAMP);

		public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

		public final SqlColumn<LocalDateTime> endTime = column("end_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Byte> streamType = column("stream_type", JDBCType.TINYINT);

		public final SqlColumn<Boolean> virtualEnable = column("virtual_enable", JDBCType.BIT);

		public final SqlColumn<Integer> multiBatch = column("multi_batch", JDBCType.INTEGER);

		public final SqlColumn<Long> numTimes = column("num_times", JDBCType.BIGINT);

		public UdsJobRecord() {
			super("uds_job_record");
		}
	}
}