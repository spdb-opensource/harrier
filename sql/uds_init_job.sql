
#UDS_SYS
insert into uds_data.uds_system (platform,system,max_run_job,des) values ('UDS','*','100','');
insert into uds_data.uds_system (platform,system,max_run_job,des) values ('UDS','SYS','0','');

#UDS_SYS_TEST_D
insert into uds_data.uds_job (platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) values ('UDS','SYS','UDS_SYS_TEST_D','示例日作业','D','Done','20210112',0,'00:01-23:59',0,0,10,0,0,0,0,1,'20201124');
insert into uds_data.uds_job_source  (src_signal,conv_signal,platform,system,job)  values ('UDS_SYS_TEST_D','UDS_SYS_TEST_D','UDS','SYS','UDS_SYS_TEST_D');
insert into uds_data.uds_job_step  (platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  values ('UDS','SYS','UDS_SYS_TEST_D',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');

#UDS_SYS_TEST_D1
insert into uds_data.uds_job (platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) values ('UDS','SYS','UDS_SYS_TEST_D1','示例日作业','D','Done','20210112',0,'00:01-23:59',0,0,10,0,0,0,0,1,'20201124');
insert into uds_data.uds_job_source  (src_signal,conv_signal,platform,system,job)  values ('UDS_SYS_TEST_D1','UDS_SYS_TEST_D1','UDS','SYS','UDS_SYS_TEST_D1');
insert into uds_data.uds_job_step  (platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  values ('UDS','SYS','UDS_SYS_TEST_D1',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');

insert into uds_data.uds_job_stream(platform,system,job,stm_platform,stm_system,stm_job,stm_batch,is_enable,des) VALUES ('UDS', 'SYS', 'UDS_SYS_TEST_D', 'UDS', 'SYS', 'UDS_SYS_TEST_D1', 0, 1, NULL);
INSERT INTO uds_data.uds_job_dependency (platform, system, job, dep_platform, dep_system, dep_job, dep_batch, is_enable, des) VALUES ('UDS', 'SYS', 'UDS_SYS_TEST_D1', 'UDS', 'SYS', 'UDS_SYS_TEST_D', 0, 1, NULL);


#UDS_SYS_TEST_C
insert into uds_data.uds_job (platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) values ('UDS','SYS','UDS_SYS_TEST_C','示例定时作业','C','Done','20210112',0,'00:01-23:59',0,0,10,0,0,0,1,1,'20201124');
insert into uds_data.uds_job_source  (src_signal,conv_signal,platform,system,job)  values ('UDS_SYS_TEST_C','UDS_SYS_TEST_C','UDS','SYS','UDS_SYS_TEST_C');
insert into uds_data.uds_job_step  (platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  values ('UDS','SYS','UDS_SYS_TEST_C',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');
INSERT INTO uds_data.uds_job_date_trigger (`platform`, `system`, `job`, `offset_day`, `start_time`, `end_time`, `second`, `minute`, `hour`, `day`, `month`, `week`, `year`, `is_enable`, `des`) VALUES ('UDS', 'SYS', 'UDS_SYS_TEST_C', 0, '2021-01-12 16:00:00', '2099-01-01 00:00:01', '0', '0', '16', '*', '*', '?', '*', 1, ' ');

#UDS_SYS_TEST_M
insert into uds_data.uds_job (platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) values ('UDS','SYS','UDS_SYS_TEST_M','示例月作业','M','Done','20210112',0,'00:01-23:59',0,0,10,0,0,1,0,1,'20201124');
insert into uds_data.uds_job_source  (src_signal,conv_signal,platform,system,job)  values ('UDS_SYS_TEST_M','UDS_SYS_TEST_M','UDS','SYS','UDS_SYS_TEST_M');
insert into uds_data.uds_job_step  (platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  values ('UDS','SYS','UDS_SYS_TEST_M',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');
insert into uds_data.uds_job_date_frequency (platform,system,job,minute,hour,day,month,week,year,is_enable) values ('UDS','SYS','UDS_SYS_TEST_M',0,0,15,'*','?','*',1);


insert into uds_data.uds_server (server_name,`order`,ip,`port`,agent_port,http_port,server_type,max_job_num,performance_ratio,is_enable) values ('udsmaster01',1,'10.10.10.10',9696,6969,7878,1,0,1000,1);
insert into uds_data.uds_server (server_name,`order`,ip,`port`,agent_port,http_port,server_type,max_job_num,performance_ratio,is_enable) values ('udsslave01',10,'10.10.10.10',9696,6969,7878,0,100,1000,1);