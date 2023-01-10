package cn.spdb.harrier.rpc.transport.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface InterfaceHttpHandler {

	 FullHttpResponse handler(FullHttpRequest fullHttpRequest);

	 
}
