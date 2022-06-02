package cn.spdb.harrier.server.worker.rpc;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.uitls.Host;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.rpc.config.NettyServerConfig;
import cn.spdb.harrier.rpc.remote.NettyServer;
import cn.spdb.harrier.rpc.transport.RpcServiceTransportBean;
import cn.spdb.harrier.server.master.rpc.transport.MasterTransportServerInterfasce;
import cn.spdb.harrier.server.worker.conf.WorkerConfig;
import cn.spdb.harrier.server.worker.rpc.transport.WorkTransportServerInterface;

@Component
public class WorkerRpc {

	private ConcurrentHashMap<String, WorkTransportServerInterface> clientWorkList = new ConcurrentHashMap<String, WorkTransportServerInterface>();
	
	private ConcurrentHashMap<String, MasterTransportServerInterfasce> clientMasterList = new ConcurrentHashMap<String, MasterTransportServerInterfasce>();

	private NettyServer nettyServer;

	private Logger logger=LoggerFactory.getLogger(WorkerRpc.class.getSimpleName());
	
	@Autowired
	public WorkerRpc( WorkerConfig workerConfig) {
		NettyServerConfig serverConfig = new NettyServerConfig();
		serverConfig.setListenPort(workerConfig.getListenPort());
		this.nettyServer = new NettyServer(serverConfig);
		RpcServiceTransportBean.scanServiceClassScan(WorkerRpc.class.getPackage());
	}

	@PostConstruct
	public void start() {
		logger.info("worker rpc start");
		nettyServer.start();
		logger.info("worker rpc start ok");
	}
	
	@PreDestroy
	public void shutdown() {
		logger.info("worker rpc shutdown");
		nettyServer.shutdown();
		logger.info("worker rpc shutdown ok");
	}

	public NettyServer getNettyServer() {
		return nettyServer;
	}

	public void setNettyServer(NettyServer nettyServer) {
		this.nettyServer = nettyServer;
	}
	
	public WorkTransportServerInterface getWorkClient(Host host) {
		WorkTransportServerInterface work=getWorkClient(host.getIp(), host.getPort());
		if(work==null) {
			addWorkClient(host.getIp(), host.getPort());
		}
		return getWorkClient(host.getIp(), host.getPort());
	}
	
	public MasterTransportServerInterfasce getMasterClient(Host host) {
		MasterTransportServerInterfasce master= getMasterClient(host.getIp(), host.getPort());
		if(master==null) {
			addMasterClient(host.getIp(), host.getPort());
		}
		return getMasterClient(host.getIp(), host.getPort());
	}
	
	
	
	public void addWorkClient(String ip,int port) {
		try {
			WorkTransportServerInterface client = RpcClient.getInstance().create(WorkTransportServerInterface.class, new URI("spdb", ip, port));
			clientWorkList.put(ip+Symbol.MAO_HAO+String.valueOf(port), client);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void addMasterClient(String ip,int port) {
		try {
			MasterTransportServerInterfasce client=RpcClient.getInstance().create(MasterTransportServerInterfasce.class, new URI("spdb", ip, port));
			clientMasterList.put(ip+Symbol.MAO_HAO+String.valueOf(port), client);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	public WorkTransportServerInterface getWorkClient(String ip,int port) {
		return clientWorkList.get(ip+Symbol.MAO_HAO+String.valueOf(port));
	}
	
	public MasterTransportServerInterfasce getMasterClient(String ip,int port) {
		return clientMasterList.get(ip+Symbol.MAO_HAO+String.valueOf(port));
	}
	
}
