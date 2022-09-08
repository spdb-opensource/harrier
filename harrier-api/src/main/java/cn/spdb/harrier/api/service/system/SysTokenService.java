package cn.spdb.harrier.api.service.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.utils.HTMLFilter;
import cn.spdb.harrier.api.utils.ServletUtils;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.UUID;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SysTokenService {
	// 令牌自定义标识
	@Value("${token.header:token}")
	private String header;

	// 令牌秘钥
	@Value("${token.secret:555ffff5fsdfsd4}")
	private String secret;

	// 令牌有效期（默认30分钟）
	@Value("${token.expireTime:30}")
	private int expireTime;

	protected static final long MILLIS_SECOND = 1000;

	protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;


	@Autowired
	private HarrierCache cache;

	/**
	 * 获取用户身份信息
	 *
	 * @return 用户信息
	 */
	public LoginUser getLoginUser(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = parseToken(token);
				// 解析对应的权限以及用户信息
				String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
				String userKey = getTokenKey(uuid);
				LoginUser user = cache.get(userKey);
				return user;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 设置用户身份信息
	 */
	public void setLoginUser(LoginUser loginUser) {
		if (null != loginUser && StringUtils.isNotEmpty(loginUser.getToken())) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 删除用户身份信息
	 */
	public void delLoginUser(String token) {
		if (StringUtils.isNotEmpty(token)) {
			String userKey = getTokenKey(token);
			cache.delete(userKey);
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param loginUser 用户信息
	 * @return 令牌
	 */
	public String createToken(LoginUser loginUser) {
		String token = UUID.fastUUID().toString();
		loginUser.setToken(token);
		setUserAgent(loginUser);
		refreshToken(loginUser);

		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.LOGIN_USER_KEY, token);
		return createToken(claims);
	}

	/**
	 * 验证令牌有效期，相差不足30分钟，自动刷新缓存
	 *
	 * @param loginUser
	 * @return 令牌
	 */
	public void verifyToken(LoginUser loginUser) {
		long expireTime = loginUser.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= expireTime * MILLIS_MINUTE) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 刷新令牌有效期
	 *
	 * @param loginUser 登录信息
	 */
	public void refreshToken(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
		String userKey = getTokenKey(loginUser.getToken());
		cache.put(userKey, loginUser);
	}

	/**
	 * 设置用户代理信息
	 *
	 * @param loginUser 登录信息
	 */
	public void setUserAgent(LoginUser loginUser) {
		UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		String ip = getIpAddr(ServletUtils.getRequest());
		loginUser.setIpaddr(ip);
		loginUser.setBrowser(userAgent.getBrowser().getName());
		loginUser.setOs(userAgent.getOperatingSystem().getName());
	}

	private static String getIpAddr(HttpServletRequest request) {
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

	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	private String createToken(Map<String, Object> claims) {
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = parseToken(token);
		return claims.getSubject();
	}

	/**
	 * 获取请求token
	 *
	 * @param request
	 * @return token
	 */
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader(header);
		if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
			token = token.replace(Constants.TOKEN_PREFIX, "");
		}
		return token;
	}

	private String getTokenKey(String uuid) {
		return Constants.LOGIN_TOKEN_KEY + uuid;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
