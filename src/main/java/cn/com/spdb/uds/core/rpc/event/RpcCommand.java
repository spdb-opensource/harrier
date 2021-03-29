package cn.com.spdb.uds.core.rpc.event;

public interface RpcCommand {

	/*---------------------系统命令-----------------*/
	
	int SERVER_HEARTBEAT=1;					//心跳
	int SERVER_REGISTER=2;					//注册
	int SERVER_SHOUTDOWN=3;					//注销				
	
	/*---------------------本系统命令-----------------*/
	int SERVER_COMMAND=500;					//运行其他服务器脚本
	int UPDATE_CHILD_INFO=501;					//结束回收
	
	
	
	/*---------------------业务命令-----------------*/
	int DISTRIBUTION_JOB=1000;             //分发作业
	
}
