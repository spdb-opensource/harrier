package cn.spdb.harrier.rpc.transport.script;

import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;

@RpcHandler("IRpcScriptService")
public interface IRpcScriptService {
	
	@RpcMethod
	public String script(String clazz,String ... parms);
}
