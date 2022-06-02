package cn.spdb.harrier.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.cache.HarrierCacheImpl;

@SpringBootApplication(scanBasePackages = { "cn.spdb.harrier.api", "cn.spdb.harrier.service", "cn.spdb.harrier.dao" })
@ServletComponentScan
public class ApiServer {


	@Primary
	@Bean("api-cache")
	public HarrierCache addHarrierCache() {
		return new HarrierCacheImpl();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiServer.class).run(args);
	}
}
