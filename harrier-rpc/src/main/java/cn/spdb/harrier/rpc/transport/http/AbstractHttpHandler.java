package cn.spdb.harrier.rpc.transport.http;

import cn.spdb.harrier.common.utils.JSONUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public abstract class AbstractHttpHandler implements InterfaceHttpHandler {

	public FullHttpResponse handler(FullHttpRequest fullHttpRequest) {
		Object object = handlerObject(fullHttpRequest);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
		if (object != null) {
			if (object instanceof FullHttpResponse) {
				FullHttpResponse fullHttpResponse = (FullHttpResponse) object;
				return fullHttpResponse;
			} else if (object instanceof String) {
				String msg = object.toString();
				msg = StringUtils.isEmpty(msg) ? "" : msg;
				ByteBuf context = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
				response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);
			} else {
				String msg = JSONUtils.toJsonString(object);
				ByteBuf context = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
				response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);
			}
		}
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		return response;
	}

	public abstract Object handlerObject(FullHttpRequest fullHttpRequest);

}
