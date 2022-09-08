package cn.spdb.harrier.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.spdb.harrier.server.master.UdsMasterServer;
import cn.spdb.harrier.server.worker.UdsWorkerServer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(UdsWorkerServer.class, UdsMasterServer.class).web(WebApplicationType.NONE).run(args);
	}
}
