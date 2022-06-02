package cn.spdb.harrier.api.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.api.enums.Status;
import cn.spdb.harrier.api.utils.Result;
import cn.spdb.harrier.common.uitls.JSONUtils;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		int code =Status.QUERY_UNAUTHORIZED_PROJECT_ERROR.getCode();
		String msg=Status.QUERY_UNAUTHORIZED_PROJECT_ERROR.getMsg();
		Result result=new Result(code, msg);
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JSONUtils.toJson(result));
	}

}
