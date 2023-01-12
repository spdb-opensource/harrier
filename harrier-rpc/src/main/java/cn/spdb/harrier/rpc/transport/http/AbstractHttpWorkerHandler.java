package cn.spdb.harrier.rpc.transport.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

public abstract class AbstractHttpWorkerHandler extends AbstractHttpHandler {

	public abstract Object handlerPost(Map<String, String> parMap, Map<String, Object> objectMap);

	public abstract Object handlerGet(Map<String, String> parMap);

	public Object handlerObject(FullHttpRequest httpRequest) {
		Map<String, String> parMap = new HashMap<String, String>();
		Object object = null;
		String uri = httpRequest.uri();// 路径
		try {
			HttpMethod httpMethod = httpRequest.method();
			if (HttpMethod.GET.equals(httpMethod)) {
				QueryStringDecoder decoder = new QueryStringDecoder(uri);
				decoder.parameters().entrySet().forEach(entry -> {
					parMap.put(entry.getKey(), entry.getValue().get(0));
				});
				object = handlerGet(parMap);
			} else if (HttpMethod.POST.equals(httpMethod)) {
				// 带入参数
				QueryStringDecoder decoder = new QueryStringDecoder(uri);
				decoder.parameters().entrySet().forEach(entry -> {
					parMap.put(entry.getKey(), entry.getValue().get(0));
				});
				// 隐藏参数
				Map<String, Object> objectMap = new HashMap<String, Object>();
				HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(
						new DefaultHttpDataFactory(false), httpRequest);
				httpPostRequestDecoder.offer(httpRequest);
				List<InterfaceHttpData> parmList = httpPostRequestDecoder.getBodyHttpDatas();
				for (InterfaceHttpData data : parmList) {
					Attribute attribute = (Attribute) data;
					Object attObj = JSON.parse(attribute.getValue());
					objectMap.put(data.getName(), attObj);
				}
				object = handlerPost(parMap, objectMap);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;

	}
}
