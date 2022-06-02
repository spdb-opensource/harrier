package cn.spdb.harrier.api.controller.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.spdb.harrier.common.uitls.StringUtils;

@RestController
public class SysIndexController {


	@RequestMapping(value = { "/" }, method = { RequestMethod.POST, RequestMethod.GET })
	public void springroot(HttpServletRequest request, HttpServletResponse response, String remoteUser, String ssouser)
			throws ServletException, IOException, Exception {
		if (StringUtils.isNotBlank(remoteUser)) {
			response.sendRedirect("index.html?remoteUser=" + remoteUser);
		} else if (StringUtils.isNotBlank(ssouser)) {
			response.sendRedirect("index.html?ssouser=" + ssouser);

		} else {
			response.sendRedirect("index.html");
		}
	}
}