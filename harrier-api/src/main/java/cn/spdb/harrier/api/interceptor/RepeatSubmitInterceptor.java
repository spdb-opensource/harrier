package cn.spdb.harrier.api.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;

import cn.spdb.harrier.api.service.system.SysTokenService;
import cn.spdb.harrier.api.utils.HttpHelper;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.api.utils.ServletUtils;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.uitls.StringUtils;


@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {

	public final String REPEAT_PARAMS = "repeatParams";

	public final String REPEAT_TIME = "repeatTime";

	@Autowired
	private SysTokenService tokenService;

	@Autowired
	private HarrierCache cache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
			if (annotation != null) {
				if (this.isRepeatSubmit(request, annotation)) {
					Result<?> result = Result.error(annotation.message());
					ServletUtils.renderString(response, JSONObject.toJSONString(result));
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 验证是否重复提交由子类实现具体的防重复提交的规则
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
		String nowParams = "";
		if (request instanceof HttpServletRequest
				&& StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
			nowParams = HttpHelper.getBodyString(request);
		}

		// body参数为空，获取Parameter的数据
		if (StringUtils.isEmpty(nowParams)) {
			nowParams = JSONObject.toJSONString(request.getParameterMap());
		}
		Map<String, Object> nowDataMap = new HashMap<String, Object>();
		nowDataMap.put(REPEAT_PARAMS, nowParams);
		nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

		// 请求地址（作为存放cache的key值）
		String url = request.getRequestURI();

		String header = tokenService.getHeader();

		// 唯一值（没有消息头则使用请求地址）
		String submitKey = request.getHeader(header).trim();

		// 唯一标识（指定key + url + 消息头）
		String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + url + submitKey;

		Map<String, Object> sessionMap = cache.get(cacheRepeatKey);
		if (sessionMap != null) {
			if (sessionMap.containsKey(url)) {
				Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
				if (compareParams(nowDataMap, preDataMap)
						&& compareTime(nowDataMap, preDataMap, annotation.interval())) {
					return true;
				}
			}
		}

		Map<String, Object> cacheMap = new HashMap<String, Object>();
		cacheMap.put(url, nowDataMap);
		cache.put(cacheRepeatKey, cacheMap);
		return false;
	}

	/**
	 * 判断参数是否相同
	 */
	private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
		String nowParams = (String) nowMap.get(REPEAT_PARAMS);
		String preParams = (String) preMap.get(REPEAT_PARAMS);
		return nowParams.equals(preParams);
	}

	/**
	 * 判断两次间隔时间
	 */
	private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
		long time1 = (Long) nowMap.get(REPEAT_TIME);
		long time2 = (Long) preMap.get(REPEAT_TIME);
		if ((time1 - time2) < interval) {
			return true;
		}
		return false;
	}
}
