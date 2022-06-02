package cn.spdb.harrier.api.rpc;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.api.rpc.transport.MasterTransportServerInterfasce;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.enmus.RegistryState;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.service.db.DbRegistryService;

@Component
public class MoniterRpc {

	@Autowired
	private DbRegistryService dbRegistryService;

	private ConcurrentHashMap<String, MasterTransportServerInterfasce> clientMasterList = new ConcurrentHashMap<String, MasterTransportServerInterfasce>();

	@PostConstruct
	public void start() {
		dbRegistryService.addListener(event -> {
			if (event.getState().equals(RegistryState.DISCONNECTED)
					|| event.getState().equals(RegistryState.SUSPENDED)) {
				UdsServer udsServerTmp = event.getUdsServer();
				if (udsServerTmp.getNodeClusterType().equals(Constants.THREAD_NAME_MASTER_SERVER)) {
					clientMasterList.remove(udsServerTmp.getIp() + Symbol.MAO_HAO + udsServerTmp.getPort());
				}
			}
		});

	}


	public MasterTransportServerInterfasce getMasterClient(String ip, int port) {
		return clientMasterList.get(ip + Symbol.MAO_HAO + String.valueOf(port));
	}

	public void addMasterClient(String ip, int port) {
		try {
			MasterTransportServerInterfasce client = RpcClient.getInstance()
					.create(MasterTransportServerInterfasce.class, new URI("spdb", ip, port));
			clientMasterList.put(ip + Symbol.MAO_HAO + String.valueOf(port), client);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

}
