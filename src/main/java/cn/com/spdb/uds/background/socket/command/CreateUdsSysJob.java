package cn.com.spdb.uds.background.socket.command;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.background.http.HttpResultCode;
import cn.com.spdb.uds.background.socket.InterfaceConsoleCommand;
import cn.com.spdb.uds.db.DBManager;
import cn.com.spdb.uds.db.dao.BaseDao;

public class CreateUdsSysJob implements InterfaceConsoleCommand {

	/**
	 * 删除的SQL DELETE FROM uds_job WHERE job IN
	 * ('UDS_SYS_CLEAR_BACK_DB','UDS_SYS_CLEAR_LOG'); DELETE FROM uds_job_source
	 * WHERE job IN ('UDS_SYS_CLEAR_BACK_DB','UDS_SYS_CLEAR_LOG'); DELETE FROM
	 * uds_job_step WHERE job IN ('UDS_SYS_CLEAR_BACK_DB','UDS_SYS_CLEAR_LOG');
	 * DELETE FROM uds_job_date_trigger WHERE job IN
	 * ('UDS_SYS_CLEAR_BACK_DB','UDS_SYS_CLEAR_LOG');
	 */
	@Override
	public String hanlder(String param, PrintWriter pw) {
		String dirPath = UdsConstant.AUTO_APP_DIR_PATH + File.separator + "UDS" + File.separator + "SYS";
		String fileDirClearDb = dirPath + File.separator + "UDS_SYS_CLEAR_BACK_DB" + File.separator + "bin";
		String fileDirClearLog = dirPath + File.separator + "UDS_SYS_CLEAR_LOG" + File.separator + "bin";
		String fileDirKillJOB = dirPath + File.separator + "UDS_SYS_KILL_JOB" + File.separator + "bin";
		File dir = new File(fileDirClearDb);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		dir = new File(fileDirClearLog);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		dir = new File(fileDirKillJOB);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String sqluds_system = "insert into  uds_system(platform,system) values('UDS','SYS');";
		String sqluds_platform = "insert into  uds_system(platform,system) values('UDS','*');";
		String sqlsourcedDB = "insert into  uds_job_source(src_signal,conv_signal,platform,system,job) Values('UDS_SYS_CLEAR_BACK_DB','UDS_SYS_CLEAR_BACK_DB','UDS','SYS','UDS_SYS_CLEAR_BACK_DB');";
		String sqlsourcedLog = "insert into  uds_job_source(src_signal,conv_signal,platform,system,job) Values('UDS_SYS_CLEAR_LOG','UDS_SYS_CLEAR_LOG','UDS','SYS','UDS_SYS_CLEAR_LOG');";

		String jobdb = "insert into uds_job(platform,system,job,job_type,job_date,check_time_trigger) Values('UDS','SYS','UDS_SYS_CLEAR_BACK_DB','C',DATE_FORMAT(NOW(),'%Y%m%d'),1);";
		String joblog = "insert into uds_job(platform,system,job,job_type,job_date,check_time_trigger) Values('UDS','SYS','UDS_SYS_CLEAR_LOG','C',DATE_FORMAT(NOW(),'%Y%m%d'),1);";

		String jobstepdb = "INSERT INTO uds_job_step(platform,system,job,setp_num,oper_cmd,script_dir,script_name) VALUES('UDS','SYS','UDS_SYS_CLEAR_BACK_DB',1,'sh','$AUTO_HOME/APP/UDS/SYS/UDS_SYS_CLEAR_BACK_DB/bin','uds_sys_clear_back_db.sh');";
		String jobsteplog = "INSERT INTO uds_job_step(platform,system,job,setp_num,oper_cmd,script_dir,script_name) VALUES('UDS','SYS','UDS_SYS_CLEAR_LOG',1,'sh','$AUTO_HOME/APP/UDS/SYS/UDS_SYS_CLEAR_LOG/bin','uds_sys_clear_log.sh');";

		String jobtriggerDB = "INSERT INTO uds_job_date_trigger (platform,system,job,start_time, MINUTE, HOUR, DAY, MONTH, WEEK) VALUES ('UDS','SYS','UDS_SYS_CLEAR_BACK_DB','2019-07-06 00:00:01','0','10','*','*','?');";
		String jobtriggerlog = "INSERT INTO uds_job_date_trigger (platform,system,job,start_time, MINUTE, HOUR, DAY, MONTH, WEEK) VALUES ('UDS','SYS','UDS_SYS_CLEAR_LOG','2019-07-06 00:00:01','0','10','*','*','?');";

		extractedDBManage(sqluds_system, sqluds_platform, sqlsourcedDB, sqlsourcedLog, jobdb, joblog, jobstepdb, jobsteplog, jobtriggerDB, jobtriggerlog);
		if (pw != null) {
			pw.print("创建完成请放入脚本");
		}
		return HttpResultCode.SUCCESS;
	}
	/*
	Extract Method
	 */
	private void extractedDBManage(String sqluds_system, String sqluds_platform, String sqlsourcedDB, String sqlsourcedLog, String jobdb, String joblog, String jobstepdb, String jobsteplog, String jobtriggerDB, String jobtriggerlog) {
		BaseDao baseDao = DBManager.getInstance().getDao(BaseDao.class);
		List<String> sqlList = new ArrayList<String>();
		sqlList.add(sqluds_system);
		sqlList.add(sqluds_platform);
		sqlList.add(sqlsourcedDB);
		sqlList.add(sqlsourcedLog);
		sqlList.add(jobdb);
		sqlList.add(joblog);
		sqlList.add(jobstepdb);
		sqlList.add(jobsteplog);
		sqlList.add(jobtriggerDB);
		sqlList.add(jobtriggerlog);
		baseDao.insert(sqlList);
	}

}
