
package cn.spdb.harrier.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * login interceptor, must login first
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger("Monitor-"+LoginHandlerInterceptor.class.getSimpleName());

	/**
	 * Intercept the execution of a handler. Called after HandlerMapping determined
	 * 
	 * @param request  current HTTP request
	 * @param response current HTTP response
	 * @param handler  chosen handler to execute, for type and/or instance
	 *                 evaluation
	 * @return boolean true or false
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

//    // get token
//    String token = request.getHeader("token");
//    User user = null;
//    if (StringUtils.isEmpty(token)){
//      user = authenticator.getAuthUser(request);
//      // if user is null
//      if (user == null) {
//        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//        logger.info("user does not exist");
//        return false;
//      }
//    }else {
//       user = userMapper.queryUserByToken(token);
//      if (user == null) {
//        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//        logger.info("user token has expired");
//        return false;
//      }
//    }
//
//    // check user state
//    if (user.getState() == Flag.NO.ordinal()) {
//      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//      logger.info(Status.USER_DISABLED.getMsg());
//      return false;
//    }
//
//    request.setAttribute(Constants.SESSION_USER, user);
		return true;
	}

}
