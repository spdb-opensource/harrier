
package cn.spdb.harrier.api.interceptor;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import cn.spdb.harrier.api.config.MonitorConfig;
import cn.spdb.harrier.api.utils.HTMLFilter;
import cn.spdb.harrier.api.utils.ServletUtils;
import cn.spdb.harrier.common.uitls.StringUtils;

/**
 * This interceptor is used to control the traffic, consists with global traffic
 * control and tenant-leve traffic control. If the current coming tenant reaches
 * his tenant-level request quota, his request will be reject fast. If the
 * current system request number reaches the global request quota, all coming
 * request will be reject fast.
 */
public class RateLimitInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

    private RateLimiter globalRateLimiter;

	private MonitorConfig monitorConfig;
    
    private LoadingCache<String, RateLimiter> tenantRateLimiterCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String token) {
                    // use tenant customize rate limit
                    Map<String, Integer> customizeTenantQpsRate = monitorConfig.getCustomizeTenantQpsRate();
                    int tenantQuota = monitorConfig.getDefaultTenantQpsRate();
                    if (MapUtils.isNotEmpty(customizeTenantQpsRate)) {
                        tenantQuota = customizeTenantQpsRate.getOrDefault(token, monitorConfig.getDefaultTenantQpsRate());
                    }
                    // use tenant default rate limit
                    return RateLimiter.create(tenantQuota, 1, TimeUnit.SECONDS);
                }
            });

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ExecutionException {
        // tenant-level rate limit
        if (monitorConfig.isTrafficTenantControlSwitch()) {
            String ip = getIpAddr(ServletUtils.getRequest());
            if (StringUtils.isNotEmpty(ip)) {
                RateLimiter tenantRateLimiter = tenantRateLimiterCache.get(ip);
                if (!tenantRateLimiter.tryAcquire()) {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    logger.warn("Too many request, reach tenant rate limit, current ip:{} qps is {}", ip, tenantRateLimiter.getRate());
                    return false;
                }
            }
        }
        // global rate limit
        if (monitorConfig.isTrafficGlobalControlSwitch()) {
            if (!globalRateLimiter.tryAcquire()) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                logger.warn("Too many request, reach global rate limit, current qps is {}", globalRateLimiter.getRate());
                return false;
            }
        }
		return true;
	}

    public RateLimitInterceptor(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
        if (monitorConfig.isTrafficGlobalControlSwitch()) {
            this.globalRateLimiter = RateLimiter.create(monitorConfig.getMaxGlobalQpsRate(), 1, TimeUnit.SECONDS);
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : new HTMLFilter().filter(ip);
	}
}
