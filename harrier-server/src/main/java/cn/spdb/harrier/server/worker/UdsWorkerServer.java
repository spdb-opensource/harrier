package cn.spdb.harrier.server.worker;

import java.net.URISyntaxException;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = { "cn.spdb.harrier.server.worker", "cn.spdb.harrier.dao",
		"cn.spdb.harrier.service" })
public class UdsWorkerServer   {

	public static void main(String[] args) throws URISyntaxException {
		new SpringApplicationBuilder(UdsWorkerServer.class).web(WebApplicationType.NONE).run(args);
	}
	
	
	
}
