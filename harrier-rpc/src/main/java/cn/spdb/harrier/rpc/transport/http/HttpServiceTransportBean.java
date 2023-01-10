package cn.spdb.harrier.rpc.transport.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import cn.spdb.harrier.common.utils.ClassUtils;
import cn.spdb.harrier.common.utils.PropertyUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.rpc.common.HttpMapProtocol;
import cn.spdb.harrier.rpc.transport.RpcServiceTransportBean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

public class HttpServiceTransportBean {

	private static final Logger logger = LoggerFactory.getLogger(HttpServiceTransportBean.class);
	private static HashMap<String, HttpServiceHandlerBeanIner> HTTP_HANDER = new HashMap<String, HttpServiceHandlerBeanIner>();
	private static boolean enable = PropertyUtils.getBoolean("harrier.http.mvc.enable", false);
	private static MvcHttpHandler mvcHandler;

	static {
		Package packagea = RpcServiceTransportBean.class.getPackage();
		scanServiceClassScan(packagea);
	}

	public static void setMvcHandler(MvcHttpHandler handler) {
		HttpServiceTransportBean.mvcHandler = handler;
	}

	public static void scanServiceClassScan(Package packagea) {
		ClassUtils.scanPackage(packagea, clazz -> {
			return clazz.isAnnotationPresent(HttpMapProtocol.class);
		}).forEach(handlerClass -> {
			HttpMapProtocol handler = handlerClass.getAnnotation(HttpMapProtocol.class);
			String handlerName = handler.value();
			if (InterfaceHttpHandler.class.isAssignableFrom(handlerClass)) {
				if (HTTP_HANDER.containsKey(handlerName)) {
					logger.error("load rpc service class name is exist {} -> {} ", handlerName, handlerClass);
				}
				HTTP_HANDER.put(handlerName, new HttpServiceHandlerBeanIner(handlerClass));
				logger.info("load rpc service class name {} -> {} ", handlerName, handlerClass);
			}
		});
	}

	public static InterfaceHttpHandler getHttpHandler(String handlerName) {
		HttpServiceHandlerBeanIner beanIner = HTTP_HANDER.get(handlerName);
		InterfaceHttpHandler handler = beanIner == null ? null : (InterfaceHttpHandler) beanIner.getClassObject();
		return enable && handler == null ? mvcHandler : null;
	}

	public static Class<?> getServiceClass(String handlerName) {
		HttpServiceHandlerBeanIner beanIner = HTTP_HANDER.get(handlerName);
		return beanIner == null ? null : beanIner.getClazz();
	}

	private static class HttpServiceHandlerBeanIner {
		private Class<?> clazz;
		private Object object;

		public Object getClassObject() {
			if (object == null) {
				try {
					object = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
			return object;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public HttpServiceHandlerBeanIner(Class<?> clazz) {
			this.clazz = clazz;
		}
	}

	public class MvcHttpHandler implements InterfaceHttpHandler {

		public HttpServlet httpServlet;

		public MvcHttpHandler(HttpServlet httpServlet) {
			this.httpServlet = httpServlet;
		}

		@Override
		public FullHttpResponse handler(FullHttpRequest fullHttpRequest) {
			FullHttpResponse response = null;
			try {
				MockHttpServletRequest servletRequest = new MockHttpServletRequest(
						httpServlet.getServletConfig().getServletContext());
				MockHttpServletResponse servletResponse = new MockHttpServletResponse();
				for (String name : fullHttpRequest.headers().names()) {
					for (String value : fullHttpRequest.headers().getAll(name)) {
						servletRequest.addHeader(name, value);
					}
				}
				String uri = fullHttpRequest.uri();
				QueryStringDecoder decoder = new QueryStringDecoder(uri);
				servletRequest.setRequestURI(decoder.path());
				servletRequest.setServletPath(decoder.path());
				servletRequest.setMethod(fullHttpRequest.method().name());
				this.httpServlet.service(servletRequest, servletResponse);
				String msg = servletResponse.getContentAsString();
				msg = StringUtils.isEmpty(msg) ? "" : msg;
				ByteBuf context = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
				response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.valueOf(servletResponse.getStatus()), context);
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
				response.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
				for (Object objectKey : servletResponse.getHeaderNames()) {
					String name = objectKey.toString();
					response.headers().add(name, servletResponse.getHeader(name));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}

	}
}
