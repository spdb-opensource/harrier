#!/usr/bin/perl
#/**********************************************************************************/
#/*脚本功能:脚本中文名或脚本实现的功能简述                                         */
#/*创建日期:YYYY-MM-DD                                                             */
#/*创建人:  某某某                                                                 */
#/*复核人:                                                                         */
#/*依赖 1:  依赖的上游作业名称或信号名                该作业的中文名               */
#/*依赖 2:  依赖的上游作业名称或信号名                该作业的中文名               */
#/*依赖 3:  依赖的上游作业名称或信号名                该作业的中文名               */
#/*目标表:  目标表英文名   目标表的中文名                                          */
#/*加载策略: 全量/增量                              							   */
#/*加载频率: 日/周/旬/月/季/半年/年												   */
#/*加载算法: I/F1/F2/F3/F4/F5/F6/F7/F8/其他										   */
#/*修改纪录: 修改人员#修改日期#修改内容简述                                        */
#/**********************************************************************************/

use strict; 	# Declare using Perl strict syntax

#####################################################################################
# ------------ Variable Section ------------
my ${AUTO_HOME} = $ENV{"AUTO_HOME"};
unshift (@INC, "${AUTO_HOME}/bin");

#模板参数修改区
#模板参数修改区-start


#模板参数修改区-end


#参数非修改区
#参数非修改区-start
#模板通用参数
my $CONV_SIGNAL_FILE 		= "";	#控制文件
my $STEP_NUM				= "";	#脚本执行编号
my $SLEEP_TIME_AVG			= "";	#作业平均睡眠时间
my $SLEEP_TIME              = "";	#作业实际睡眠时间
my $UDS_PLATFORM 			= "";	#平台
my $UDS_SYSTEM 				= "";	#系统
my $UDS_JOB 				= "";	#作业
my $JOB_DATE 				= "";	#日期
my $BATCH_NO				= "0";	#批次号
my $SCRIPT_NAME_4			= "";	#脚本后四位
my $id						= 0; 	#执行步骤编号
my $glob_dbh				  ;     #本程序全局连接

#参数非修改区-end

#参数自定义修改区-start

#参数自定义修改区-end

# ------------ Script function ------------
sub getTimeConsuming
{
   my ( $startdt,$enddt )=@_;
   my $stad= int(substr($startdt,6,2));
   my $stah= int(substr($startdt,9,2));
   my $stami=int(substr($startdt,12,2));
   my $stas= int(substr($startdt,15,2));

   my $endd= int(substr($enddt,6,2));
   my $endh= int(substr($enddt,9,2));
   my $endmi=int(substr($enddt,12,2));
   my $ends= int(substr($enddt,15,2));

   my $hConsum=(int($endd)-int($stad))*24+(int($endh)-int($stah));
   my $miConsum=int($endmi)-int($stami);
   my $sConsum=int($ends)-int($stas);
   #若秒为负数，则从分钟借一位
   if($sConsum < 0 ){
      $miConsum = $miConsum -1;
      $sConsum  = $sConsum + 60;
   }
   #若分钟为负数，则从时借一位
   if($miConsum < 0 ){
      $hConsum  = $hConsum -1;
      $miConsum = $miConsum + 60;
   }
   return "$hConsum:$miConsum:$sConsum";
}
sub getCurrTime
{
   my $cur_date;
   my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time());
   $year += 1900;
   $mon  = sprintf("%02d", $mon + 1);
   $mday = sprintf("%02d", $mday);
   $hour = sprintf("%02d", $hour);
   $min  = sprintf("%02d", $min);
   $sec  = sprintf("%02d", $sec);
   $cur_date=$year.$mon.$mday." ".$hour."\:".$min."\:".$sec;
   return $cur_date;
}
sub getLogInfo
{
	my ($output,$puttype,$outtype) = @_;
	
   my $cur_date;
   my $log_info;
   my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time());
   my $mil = `date +%T.%3N`;
   $mil = substr($mil,0,8);
   $year += 1900;
   $mon  = sprintf("%02d", $mon + 1);
   $mday = sprintf("%02d", $mday);
   $hour = sprintf("%02d", $hour);
   $min  = sprintf("%02d", $min);
   $sec  = sprintf("%02d", $sec);

   $cur_date=$year."-".$mon."-".$mday." ".$mil;
    if ( defined($output) ) {
		 if ( defined($outtype) ) {
			$log_info="[".$cur_date."] [".$puttype."] [".uc($outtype)."] ".$output;
		 }else{
			$log_info="[".$cur_date."] [".$puttype."] [INFO] ".$output;
		 }
      
   }
   else {
     $log_info="[".$cur_date."]";
   }
   print $log_info."\n";
}
sub getJobDate
{
  my ( $CONV_SIGNAL_FILE )=@_;
  my @splitcontrol=split(/@/,${CONV_SIGNAL_FILE});
  if (@splitcontrol > 1){
  my $CONV_SIGNAL_FILE = $splitcontrol[1];
  return substr(${CONV_SIGNAL_FILE},length(${CONV_SIGNAL_FILE})-12, 8);
  }else{
  my $CONV_SIGNAL_FILE = $splitcontrol[0];
  return substr(${CONV_SIGNAL_FILE},length(${CONV_SIGNAL_FILE})-12, 8);
  }
}
sub getBatchNo
{
  my ($CONV_SIGNAL_FILE)=@_;
  my @splitcontrol=split(/@/,$CONV_SIGNAL_FILE);
  if (@splitcontrol > 1)
  {
     return $splitcontrol[0]; 
  }
  else{
     return 0;
  }
}

sub getUdsJob
{
  my ( $CONV_SIGNAL_FILE )=@_;
  my @splitcontrol=split(/@/,${CONV_SIGNAL_FILE});
  if (@splitcontrol > 1){
  my $bn = $splitcontrol[0];
  my $CONV_SIGNAL_FILE = $splitcontrol[1];
	return substr($CONV_SIGNAL_FILE,0,length($CONV_SIGNAL_FILE)-13);

  }else{
  my $CONV_SIGNAL_FILE = $splitcontrol[0];
		return substr($CONV_SIGNAL_FILE,0,length($CONV_SIGNAL_FILE)-13);
  }
}
   
sub run_script_command
{
##########以下为执行脚本内容、命令行调度相关脚本、自定义的处理逻辑
	if($SLEEP_TIME_AVG <= 3){
		sleep(3);
		$SLEEP_TIME = 3;
	}else{
		
		$SLEEP_TIME = int(rand($SLEEP_TIME_AVG*0.4)+$SLEEP_TIME_AVG*0.8);
		sleep($SLEEP_TIME);
	}
	
	
	getLogInfo("jobName:".${UDS_JOB}."SLEEP_TIME_AVG:".${SLEEP_TIME_AVG}."SLEEP_TIME:".${SLEEP_TIME},"UDS");
	
	print "\n"; ##换行
	





##########以上为执行脚本内容、命令行调度相关脚本、自定义的处理逻辑
	my $RET_CODE = $? >> 8;

	
	if ( ${RET_CODE} != 0 ) {
		 ${RET_CODE} = 1;
	}
	else {
		${RET_CODE} = 0;
	}
	return ${RET_CODE};
}



# ------------ main function ------------
sub main
{
	my $ret = 0;
	
	my $startdt=getCurrTime();

	#运行业务逻辑方法
	getLogInfo('运行业务逻辑方法',"UDS");
	$ret = run_script_command();

	my $enddt=getCurrTime();
	my $timeconsuming=getTimeConsuming(${startdt},${enddt});
   
	getLogInfo("starttime         = ${startdt}","UDS");
	getLogInfo("endtime           = ${enddt}","UDS");
	getLogInfo("time consuming    = ${timeconsuming}\n","UDS");
   
	return $ret;
}

# ------------ program section ------------
# To see if there is one parameter,
# if there is no parameter, exit program
getLogInfo('开始运行脚本',"UDS");
if ( $#ARGV < 0 ) {
   getLogInfo("请输入一个  【批次号@作业名_年月日.dir N】  的信号文件","UDS","ERROR");
   exit(1);
}

# Get the first argument
$CONV_SIGNAL_FILE = ${ARGV}[0];

# 脚本执行编号
$STEP_NUM = ${ARGV}[1];

if($#ARGV < 2){
	$SLEEP_TIME_AVG = 5
}else{
	$SLEEP_TIME_AVG = ${ARGV}[2];
}





#判断脚本执行编号是否为数字
if($STEP_NUM eq $STEP_NUM+0){
	print "";
}
else{
	getLogInfo("请输入一个数字的脚本执行编号，例如：1、2、3、N ","UDS","ERROR");
	exit(1);
}

if($STEP_NUM ne 1){
	getLogInfo("sleep execute:".${STEP_NUM},"UDS");
	exit(0);
}

if ( substr(${CONV_SIGNAL_FILE}, length(${CONV_SIGNAL_FILE})-3, 3) eq 'dir' ) {

   	${BATCH_NO} = getBatchNo(${CONV_SIGNAL_FILE});
	${UDS_JOB} = getUdsJob(${CONV_SIGNAL_FILE});
	${JOB_DATE} = getJobDate(${CONV_SIGNAL_FILE});
	
	getLogInfo("BATCH_NO       = ".${BATCH_NO},"UDS");
	getLogInfo("UDS_JOB        = ".${UDS_JOB},"UDS");
	getLogInfo("JOB_DATE       = ".${JOB_DATE},"UDS");

}else{
	getLogInfo("请输入正确参数：【批次号@作业名_年月日.dir N】 的信号文件","UDS","ERROR");
	exit(1);
}

open(STDERR, ">&STDOUT");
my $ret = main();

if($ret){
	getLogInfo("程序出错 main() = ${ret} \n","UDS","ERROR");
}else{
	getLogInfo("程序正常 main() = ${ret} \n","UDS");
}

#返回值规范
if($ret ne 0){$ret = 1;}

exit($ret);

__END__


