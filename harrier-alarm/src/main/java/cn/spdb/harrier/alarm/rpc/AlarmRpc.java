package cn.spdb.harrier.alarm.rpc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cn.spdb.harrier.alarm.config.AlarmConfig;
import cn.spdb.harrier.rpc.config.NettyServerConfig;
import cn.spdb.harrier.rpc.remote.NettyServer;
import cn.spdb.harrier.rpc.transport.RpcServiceTransportBean;

@Configuration
public class AlarmRpc {

	private NettyServer nettyServer;


	@Autowired
	public AlarmRpc(AlarmConfig alarmConfig) {
		// 服务器启动
		NettyServerConfig serverConfig = new NettyServerConfig();
		serverConfig.setListenPort(alarmConfig.getListenPort());
		this.nettyServer = new NettyServer(serverConfig);
		RpcServiceTransportBean.scanServiceClassScan(AlarmRpc.class.getPackage());
	}

	@PostConstruct
	public void start() {
		nettyServer.start();
	}

	@PreDestroy
	public void shutdown() {
		nettyServer.shutdown();
	}

}
