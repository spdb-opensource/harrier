package cn.spdb.harrier.rpc.transport.http.handler;

import java.util.Map;

import cn.spdb.harrier.rpc.common.HttpMapProtocol;
import cn.spdb.harrier.rpc.transport.http.AbstractHttpGetWorkerHandler;

@HttpMapProtocol("/")
public class TestHandler extends AbstractHttpGetWorkerHandler {

	@Override
	public String handlerGet(Map<String, String> parMap) {
		return "hello world!!!";
	}

}
