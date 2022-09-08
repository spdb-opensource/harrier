package cn.spdb.harrier.standalone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.spdb.harrier.alarm.AlarmServer;
import cn.spdb.harrier.api.MonitorApplication;
import cn.spdb.harrier.server.master.UdsMasterServer;
import cn.spdb.harrier.server.worker.UdsWorkerServer;

@SpringBootApplication
public class StandaloneServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlarmServer.class, MonitorApplication.class, UdsWorkerServer.class,
				UdsMasterServer.class).run(args);
	}
}
