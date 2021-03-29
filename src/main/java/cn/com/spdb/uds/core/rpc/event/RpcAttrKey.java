package cn.com.spdb.uds.core.rpc.event;

public interface RpcAttrKey {

	/** * 返回参数 */
	String CODE = "code";
	/*** 数据 */
	String DATA = "data";
	/** IP */
	String IP = "ip";
	/** prot */
	String PORT = "prot";
	/** 作业名 */
	String JOB_NAME = "job_name";
	/** 重调 */
	String AGAIN_DISTRIBUT_NUM = "again_distribut_num";
	/** 子节点信息 */
	String CHILD_SERVER_MSG = "child_server_msg";
	/** 脚本命令 */
	String COMMAND = "command";
	/** 地域 */
	String LOCATION = "location";
	/**命令返回消息*/
	String CMD_MSG = "cmd_msg";
}
