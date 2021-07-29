package cn.com.spdb.uds.background.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class.getSimpleName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		if (!(msg instanceof FullHttpRequest)) {

			String context = "ERROR";
			send(ctx, context, HttpResponseStatus.BAD_REQUEST);
			return;
		}

		FullHttpRequest httpRequest = (FullHttpRequest) msg;
		Map<String, String> parMap = new HashMap<String, String>();

		try {

			String uri = httpRequest.uri();// 路径
			HttpMethod httpMethod = httpRequest.method();

			if (HttpMethod.GET.equals(httpMethod)) {
				String context = "GET";
				QueryStringDecoder decoder = new QueryStringDecoder(uri);
				decoder.parameters().entrySet().forEach(entry -> {
					parMap.put(entry.getKey(), entry.getValue().get(0));
				});
				System.out.println(JSON.toJSON(parMap));
				send(ctx, context, HttpResponseStatus.BAD_REQUEST);

			} else if (HttpMethod.POST.equals(httpMethod)) {
				String context = "POST";
				// 带入参数
				QueryStringDecoder decoder = new QueryStringDecoder(uri);
				decoder.parameters().entrySet().forEach(entry -> {
					parMap.put(entry.getKey(), entry.getValue().get(0));
				});

				String path = decoder.path();
				InterfaceHttpWorkeHandler handler = HttpServer.getInstance().getHttpMapHandler(path);
				if (handler == null) {
					send(ctx, context, HttpResponseStatus.BAD_REQUEST);
					return;
				}

				// 隐藏参数
				Map<String, Object> objectMap = new HashMap<String, Object>();
				HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), httpRequest);
				httpPostRequestDecoder.offer(httpRequest);
				List<InterfaceHttpData> parmList = httpPostRequestDecoder.getBodyHttpDatas();
				for (InterfaceHttpData data : parmList) {
					Attribute attribute = (Attribute) data;
					Object object = JSON.parse(attribute.getValue());
					objectMap.put(data.getName(), object);
				}
				UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, path, objectMap);
				Object object = handler.handler(parMap, objectMap);
				UdsLogger.logEvent(LogEvent.HTTP_CONSOLE, path, object);
				context = JSON.toJSONString(object);
				httpPostRequestDecoder.destroy();
				send(ctx, context, HttpResponseStatus.OK);
			} else {
				String context = "bad";
				send(ctx, context, HttpResponseStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());	
		} finally {
			httpRequest.release();
		}

	}

	private void send(ChannelHandlerContext ctx, String msg, HttpResponseStatus status) {
		if (msg == null) {
			msg = "error";
			status = HttpResponseStatus.BAD_REQUEST;
		}
		ByteBuf context = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, context);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.info("client adress:" + ctx.channel().remoteAddress() + "- online");
		ctx.writeAndFlush("客户端与服务器：" + InetAddress.getLocalHost().getHostName() + " - 建立连接");
		super.channelActive(ctx);
	}

}
