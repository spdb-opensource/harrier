package cn.spdb.harrier.server.master;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "cn.spdb.harrier.server.master", "cn.spdb.harrier.dao",
		"cn.spdb.harrier.service" })
@EnableTransactionManagement
public class UdsMasterServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(UdsMasterServer.class).web(WebApplicationType.NONE).run(args);
	}

}
