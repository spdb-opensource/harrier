package cn.spdb.harrier.api.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.api.enums.Status;
import cn.spdb.harrier.api.model.LoginUser;
import cn.spdb.harrier.api.service.system.SysTokenService;
import cn.spdb.harrier.api.utils.Result;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	private SysTokenService tokenService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		LoginUser loginUser = tokenService.getLoginUser(request);
		if (null != loginUser) {
//			String userName = loginUser.getUsername();
			tokenService.delLoginUser(loginUser.getToken());
		}

		Result result = new Result(Status.EXIT_SUCCESS.getCode(), Status.EXIT_SUCCESS.getMsg());
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(result);
	}
}
