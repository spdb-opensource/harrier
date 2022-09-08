package cn.spdb.harrier.alarm;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.cache.HarrierCacheImpl;


@SpringBootApplication(scanBasePackages = { "cn.spdb.harrier.alarm", "cn.spdb.harrier.dao",
		"cn.spdb.harrier.service" })
public class AlarmServer {

	@Bean("alarm_cache")
	public HarrierCache addHarrierCache() {
		return new HarrierCacheImpl();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlarmServer.class).web(WebApplicationType.NONE).run(args);
	}
}
