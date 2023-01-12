package cn.spdb.harrier.service.db;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.enmus.RegistryState;
import cn.spdb.harrier.common.utils.NameThreadFactory;
import cn.spdb.harrier.common.utils.OSUtils;
import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.mapper.UdsServerMapper;
import cn.spdb.harrier.service.registry.HarrierRegistry;
import cn.spdb.harrier.service.registry.SubscribeListener;
import cn.spdb.harrier.service.registry.WatchEvent;

@Component
public class DbRegistryService implements Runnable, HarrierRegistry {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<SubscribeListener> list = new ArrayList<SubscribeListener>();

	private LinkedBlockingQueue<WatchEvent> eventQueue = new LinkedBlockingQueue<WatchEvent>();

	private ScheduledExecutorService executorTask = Executors.newScheduledThreadPool(1,
			new NameThreadFactory(this.getClass().getSimpleName()));

	private UdsServerMapper serverDB;

	@Value("${harrier.registry.db.heart.seccends:180}")
	private int seccends;
	@Value("${harrier.registry.db.watcher.seccends:10}")
	private int watcherTime = 10;

	private HashSet<UdsServer> udsRegistryServerList = new HashSet<UdsServer>();

	private ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new NameThreadFactory(this.getClass().getSimpleName()));

	private DbRegistryWatch dbRegistryWatch = new DbRegistryWatch();

	@Autowired
	public DbRegistryService(UdsServerMapper serverDB) {
		this.serverDB = serverDB;
		executorTask.scheduleAtFixedRate(dbRegistryWatch, watcherTime, watcherTime, TimeUnit.SECONDS);
	}

	@PostConstruct
	public void start() {
		Thread thread = new Thread(this, this.getClass().getSimpleName());
		thread.setDaemon(true);
		thread.start();
	}

	@PreDestroy
	public void destroy() {
		for (UdsServer udsServer : udsRegistryServerList) {
			udsServer.setLastEnd(LocalDateTime.now());
			udsServer.setIsEnable(false);
			serverDB.updateByIpPortSelective(udsServer);
		}
	}

	public Collection<UdsServer> getUdsServerList() {
		return dbRegistryWatch.getudsServers();
	}

	public UdsServer registrydb(UdsServer udsServer, SubscribeListener listener) {
		udsServer.setIsEnable(true);
		udsServer.setLastStart(LocalDateTime.now());
		registrydb(udsServer);
		addListener(listener);
		udsServer = serverDB.selectByIpPortSelective(udsServer.getIp(), udsServer.getPort()).get();
		udsRegistryServerList.add(udsServer);
		dbRegistryWatch.addUdsServerMap(udsServer);
		WatchEvent watchEvent = new WatchEvent();
		watchEvent.setState(RegistryState.CONNECTED);
		watchEvent.setUdsServer(udsServer);
		eventQueue.add(watchEvent);
		return udsServer;
	}

	private void registrydb(UdsServer udsServer) {
		serverDB.deleteByIpPortSelective(udsServer.getIp(), udsServer.getPort());
		serverDB.insertSelective(udsServer);
	}

	public void unregistrydb(String ip, int port) {
		serverDB.deleteByIpPortSelective(ip, port);
	}

	public void addListener(SubscribeListener listener) {
		if (!ObjectUtils.isEmpty(listener)) {
			list.add(listener);
		}
	}

	private class DbRegistryWatch implements Runnable {

		private HashMap<Integer, UdsServer> udsserverMap = new HashMap<Integer, UdsServer>();

		protected void addUdsServerMap(UdsServer udsServer) {
			udsserverMap.put(udsServer.getId(), udsServer);
		}

		protected Collection<UdsServer> getudsServers() {
			return udsserverMap.values();
		}

		@Override
		public void run() {
			try {
				if (ObjectUtils.isEmpty(udsRegistryServerList)) {
					return;
				}
				// 所有服务集合
				List<UdsServer> servers = new ArrayList<UdsServer>();
				WatchEvent watchEvent = null;
				for (UdsServer udsServer : udsRegistryServerList) {
					// 更新本地注册服务的时间
					udsServer.setUpdateTime(LocalDateTime.now());
					try {
						udsServer.addPara(Constants.CPU,
								new Object[] { Runtime.getRuntime().availableProcessors(), OSUtils.cpuUsage() });
						udsServer.addPara(Constants.MEM,
								new Object[] { OSUtils.totalPhysicalMemorySize(), OSUtils.memoryUsage() });
						// 服务心跳
						serverDB.updateByIpPortSelective(udsServer);
					} catch (Exception e) {
						watchEvent = new WatchEvent();
						watchEvent.setState(RegistryState.SUSPENDED);
						watchEvent.setUdsServer(udsServer);
						logger.info("local suspended node {}", udsServer.getServerName());
						e.printStackTrace();
					}
					// 获取所有相关集合的服务
					List<UdsServer> serverList = serverDB.selectListByRole(udsServer.getServerRoleName(),
							udsServer.getServerRoleNameGroup(), true);
					servers.addAll(serverList);
					Iterator<UdsServer> iterator = serverList.iterator();
					// 判断各个服务的状态
					while (iterator.hasNext()) {
						UdsServer us = iterator.next();
						// 本地参数是否更新
						if (us.equals(udsServer)) {
							if (!us.getPara().equals(udsServer.getPara())) {
								watchEvent = new WatchEvent();
								watchEvent.setState(RegistryState.UPDATE_PARA);
								watchEvent.setUdsServer(us);
								udsserverMap.put(us.getId(), us);
							}
							continue;
						}

						UdsServer localUs = udsserverMap.get(us.getId());
						Duration duration = Duration.between(us.getUpdateTime(), LocalDateTime.now());
						Boolean usUse = duration.getSeconds() < seccends && us.getIsEnable();
						if (localUs == null) {
							if (usUse) {
								watchEvent = new WatchEvent();
								watchEvent.setState(RegistryState.CONNECTED);
								watchEvent.setUdsServer(us);
								udsserverMap.put(us.getId(), us);
								logger.info("local {} node connected {}", udsServer.getServerName(),
										us.getServerName());
							}
						} else {
							Duration localDuration = Duration.between(localUs.getUpdateTime(), LocalDateTime.now());
							Boolean localUse = localDuration.getSeconds() < seccends && localUs.getIsEnable();
							if (usUse) {
								if (localUse) {
									if (!us.getPara().equals(localUs.getPara())) {
										watchEvent = new WatchEvent();
										watchEvent.setState(RegistryState.UPDATE_PARA);
										watchEvent.setUdsServer(us);
									}
									udsserverMap.put(us.getId(), us);
								} else {
									watchEvent = new WatchEvent();
									watchEvent.setState(RegistryState.RECONNECTED);
									watchEvent.setUdsServer(us);
									udsserverMap.put(us.getId(), us);
									logger.info("local {} node reconnected {}", udsServer.getServerName(),
											us.getServerName());
								}
							} else {
								if (localUs.getIsEnable()) {
									watchEvent = new WatchEvent();
									watchEvent.setState(RegistryState.SUSPENDED);
									watchEvent.setUdsServer(us);
									localUs.setIsEnable(false);
									logger.info("local {} node suspended {}", udsServer.getServerName(),
											us.getServerName());
								}
							}
						}
						if (watchEvent != null) {
							eventQueue.add(watchEvent);
						}
					}
				}

				Iterator<UdsServer> it = udsserverMap.values().iterator();
				while (it.hasNext()) {
					UdsServer localus = it.next();
					if (!servers.contains(localus)) {
						// 移除
						it.remove();
						watchEvent = new WatchEvent();
						watchEvent.setState(RegistryState.DISCONNECTED);
						watchEvent.setUdsServer(localus);
						eventQueue.add(watchEvent);
						logger.info("node disconnected {}", localus);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				WatchEvent watchEvent = eventQueue.take();
				if (watchEvent != null) {
					for (SubscribeListener listener : list) {
						try {
							exec.execute(() -> listener.notity(watchEvent));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getWatcherTime() {
		return watcherTime;
	}

	public void setWatcherTime(int watcherTime) {
		this.watcherTime = watcherTime;
	}

	public UdsServer getLeader(String nodeClusterType) {
		UdsServer tmp = null;
		for (UdsServer udsServer : getUdsServerList()) {
			if (udsServer.getNodeClusterType().equals(nodeClusterType)) {
				if (!udsServer.getIsEnable()) {
					continue;
				}
				if (ObjectUtils.isEmpty(tmp) || tmp.getId() > udsServer.getId()) {
					tmp = udsServer;
				}
			}
		}
		return tmp;
	}

	public UdsServer getMaster() {
		return getLeader(Constants.THREAD_NAME_MASTER_SERVER);
	}

	public UdsServer getAlarm() {
		return getLeader(Constants.THREAD_NAME_ALARM_SERVER);
	}

}
