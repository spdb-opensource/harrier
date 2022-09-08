package cn.spdb.harrier.api.config;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;

@Configuration
public class DruidMonitorConfig {

	@Bean
	public ServletRegistrationBean<Servlet> duridServletRegistrationBean(){
		ServletRegistrationBean<Servlet> servletRegistrationBean
		=new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");
		servletRegistrationBean.addInitParameter("allow", "");
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}
	
//	public FilterRegistrationBean<Filter> duridFilterRegistrationBean(){
//		FilterRegistrationBean<Filter> filterRegistrationBean=new FilterRegistrationBean<Filter>(new WebStatFilter());
//	}
}
