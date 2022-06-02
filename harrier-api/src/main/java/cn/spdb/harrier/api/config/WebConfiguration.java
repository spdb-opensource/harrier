package cn.spdb.harrier.api.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.spdb.harrier.api.interceptor.LocaleChangeInterceptor;
import cn.spdb.harrier.api.interceptor.RateLimitInterceptor;
import cn.spdb.harrier.api.interceptor.RepeatSubmitInterceptor;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	public static final String LOGIN_INTERCEPTOR_PATH_PATTERN = "/**/*";
	public static final String LOGIN_PATH_PATTERN = "/login";
	public static final String REGISTER_PATH_PATTERN = "/users/registry";
	public static final String PATH_PATTERN = "/**";
	public static final String LOCALE_LANGUAGE_COOKIE = "language";

	@Autowired
	private MonitorConfig monitorConfig;

	@Autowired
	private RepeatSubmitInterceptor repeatSubmitInterceptor;

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("*");
		config.addAllowedOriginPattern("*");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration(PATH_PATTERN, config);
		return new CorsFilter(configSource);
	}

	/**
	 * Cookie
	 * 
	 * @return local resolver
	 */
	@Bean(name = "localeResolver")
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName(LOCALE_LANGUAGE_COOKIE);
		// set default locale
		localeResolver.setDefaultLocale(Locale.US);
		// set language tag compliant
		localeResolver.setLanguageTagCompliant(false);
		return localeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// i18n
		registry.addInterceptor(new LocaleChangeInterceptor());
		registry.addInterceptor(new RateLimitInterceptor(monitorConfig));
		registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		/** swagger配置 */
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
	}

}
