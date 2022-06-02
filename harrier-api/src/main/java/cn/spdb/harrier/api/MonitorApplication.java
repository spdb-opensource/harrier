
package cn.spdb.harrier.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.spdb.harrier.alarm.AlarmServer;

@SpringBootApplication
public class MonitorApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiServer.class,AlarmServer.class).run(args);
	}

}
