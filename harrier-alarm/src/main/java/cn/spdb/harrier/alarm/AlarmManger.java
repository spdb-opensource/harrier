package cn.spdb.harrier.alarm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cn.spdb.harrier.alarm.config.AlarmConfig;
import cn.spdb.harrier.alarm.enums.AlarmCode;
import cn.spdb.harrier.alarm.enums.AlarmStatus;
import cn.spdb.harrier.alarm.enums.SendStatus;
import cn.spdb.harrier.alarm.utils.AlarmUtils;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.cache.HarrierCache;
import cn.spdb.harrier.common.enmus.RegistryState;
import cn.spdb.harrier.common.uitls.IPUtils;
import cn.spdb.harrier.common.uitls.NameThreadFactory;
import cn.spdb.harrier.common.uitls.Stopper;
import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.common.uitls.ThreadUtils;
import cn.spdb.harrier.dao.entity.MAlarm;
import cn.spdb.harrier.dao.entity.MAlarmConfig;
import cn.spdb.harrier.dao.entity.MAlarmMsg;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import cn.spdb.harrier.dao.entity.MAlarmUserGroup;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.mapper.MAlarmConfigMapper;
import cn.spdb.harrier.dao.mapper.MAlarmMapper;
import cn.spdb.harrier.dao.mapper.MAlarmMsgMapper;
import cn.spdb.harrier.dao.mapper.MAlarmSendQueueMapper;
import cn.spdb.harrier.dao.mapper.MAlarmUserGroupMapper;
import cn.spdb.harrier.service.db.DbRegistryService;

@Component
public class AlarmManger implements Runnable {

	@Autowired
	private HarrierCache harrierCache;
	@Autowired
	private MAlarmConfigMapper alarmConfigMapper;
	@Autowired
	private MAlarmMsgMapper alarmMsgMapper;
	@Autowired
	private MAlarmMapper alarmMapper;
	@Autowired
	private MAlarmSendQueueMapper alarmSendQueueMapper;
	@Autowired
	private MAlarmUserGroupMapper alarmUserGroupMapper;
	@Autowired
	private DbRegistryService dbRegistryService;
	@Autowired
	private AlarmSendManger alarmSendManger;
	@Autowired
	private AlarmConfig alarmConfig;

	private String AlarmConfPer = "ALARM_CONFIG";

	private String AlarmMsgPer = "ALARM_MSG";

	private LinkedBlockingQueue<MAlarm> dealQueue = new LinkedBlockingQueue<MAlarm>();

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new NameThreadFactory(AlarmManger.class.getSimpleName()));

	private ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(
			Runtime.getRuntime().availableProcessors(),
			new NameThreadFactory(this.getClass().getSimpleName() + ":schedule"));

	private static final Logger logger = LoggerFactory.getLogger(AlarmManger.class.getSimpleName());

	private final AtomicBoolean STOP = new AtomicBoolean(false);
	private UdsServer udsServer;

	@PostConstruct
	public void init() {
		dbInit();
//		alarmUserGroupMapper.search(null, "SYSTEM");
		registryInit();

		ThreadUtils.newDaemonSingleThreadExecutor(AlarmSendManger.class.getSimpleName()).execute(this);
		scheduledService.scheduleAtFixedRate(() -> {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					return;
				}
				selectSendAlarmByDb();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 10, 10, TimeUnit.SECONDS);
		logger.info("start  {}", AlarmManger.class);
	}

	private void registryInit() {
		// 服务注册
		UdsServer udsServer = new UdsServer();
		udsServer.setPort(alarmConfig.getListenPort());
		udsServer.setServerName(alarmConfig.getServerName());
		udsServer.setServerRoleName(alarmConfig.getRoleName());
		udsServer.setServerRoleNameGroup(alarmConfig.getRoleGroup());
		udsServer.setNodeClusterType(Constants.THREAD_NAME_ALARM_SERVER);
		udsServer.setIp(IPUtils.getHostIp());
		this.udsServer = udsServer;
		dbRegistryService.registrydb(udsServer, event -> {
			if (event.getState().equals(RegistryState.CONNECTED) || event.getState().equals(RegistryState.DISCONNECTED)
					|| event.getState().equals(RegistryState.SUSPENDED)) {
				UdsServer udsServerTmp = event.getUdsServer();
				if (udsServerTmp.getNodeClusterType().equals(Constants.THREAD_NAME_ALARM_SERVER)) {
					if (udsServerTmp.equals(this.udsServer) && event.getState().equals(RegistryState.SUSPENDED)) {
						alarmSendManger.stop();
						stop();
					} else {
						UdsServer us = dbRegistryService.getAlarm();
						if (us.equals(this.udsServer)) {
							alarmSendManger.start();
							start();
						} else {
							alarmSendManger.stop();
							stop();
						}
					}
				}
				if (event.getState().equals(RegistryState.SUSPENDED) && STOP.get()) {
					addAlarm(AlarmUtils.build("WARN", "*", "*", "*", "系统节点断开", "系统节点失联，" + udsServerTmp.toString()));
				}
			}
			logger.info(event.getIp() + ":" + event.getPort() + " " + event.getState().name());
		});
	}

	private void dbInit() {
		Optional<MAlarmConfig> opt = alarmConfigMapper.selectOne(Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO,
				Symbol.XING_HAO);
		if (!opt.isPresent()) {
			MAlarmConfig record = new MAlarmConfig();
			record.setCode(Symbol.XING_HAO);
			record.setPlatform(Symbol.XING_HAO);
			record.setSystems(Symbol.XING_HAO);
			record.setJob(Symbol.XING_HAO);
			record.setBuild(true);
			record.setIsEnable(true);
			alarmConfigMapper.insertSelective(record);
		}
		opt = alarmConfigMapper.selectOne(AlarmCode.WARN.name(), Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO);
		if (!opt.isPresent()) {
			MAlarmConfig record = new MAlarmConfig();
			record.setCode(AlarmCode.WARN.name());
			record.setPlatform(Symbol.XING_HAO);
			record.setSystems(Symbol.XING_HAO);
			record.setJob(Symbol.XING_HAO);
			record.setBuild(true);
			record.setDefStatus(2);
			record.setIsEnable(true);
			alarmConfigMapper.insertSelective(record);
		}
		opt = alarmConfigMapper.selectOne(AlarmCode.INFO.name(), Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO);
		if (!opt.isPresent()) {
			MAlarmConfig record = new MAlarmConfig();
			record.setCode(AlarmCode.INFO.name());
			record.setPlatform(Symbol.XING_HAO);
			record.setSystems(Symbol.XING_HAO);
			record.setJob(Symbol.XING_HAO);
			record.setBuild(true);
			record.setDefStatus(1);
			record.setIsEnable(true);
			alarmConfigMapper.insertSelective(record);
		}
	}

	/**
	 * 加入等待处理告警队列
	 * 
	 * @param alarm
	 */
	public Boolean addAlarm(MAlarm alarm) {
		boolean b = dealQueue.add(alarm);
		logger.info("create Malarm :{}", alarm);
		return b;
	}

	/**
	 * 获取告警配置
	 * 
	 * @param code
	 * @param platfrom
	 * @param systems
	 * @param job
	 * @return
	 */
	public MAlarmConfig getAlarmConfByCache(String code, String platfrom, String systems, String job) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(AlarmConfPer).append(Symbol.XIA_HUA_XIAN).append(code).append(Symbol.XIA_HUA_XIAN)
				.append(platfrom).append(Symbol.XIA_HUA_XIAN).append(systems).append(Symbol.XIA_HUA_XIAN).append(job);
		return harrierCache.get(buffer.toString(), () -> {
			return alarmConfigMapper.selectOne(code, platfrom, systems, job).orElse(null);
		});
	}

	/**
	 * 获取预设告警信息
	 * 
	 * @param code
	 * @param platfrom
	 * @param systems
	 * @return
	 */
	public MAlarmMsg getAlarmMsgByCache(String code, String platfrom, String systems) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(AlarmMsgPer).append(Symbol.XIA_HUA_XIAN).append(code).append(Symbol.XIA_HUA_XIAN).append(platfrom)
				.append(Symbol.XIA_HUA_XIAN).append(systems);
		return harrierCache.get(buffer.toString(), () -> {
			return alarmMsgMapper.selectOne(code, platfrom, systems).orElse(null);
		});
	}

	/**
	 * 检测告警是否开启
	 * 
	 * @param alarm
	 * @return
	 */
	public boolean checkMasterControlOpen(MAlarm alarm) {
		MAlarmConfig config = getAlarmConfByCache(Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO);
		if (config != null && !config.getBuild()) {
			return false;
		}
		config = getAlarmConfByCache(alarm.getCode(), Symbol.XING_HAO, Symbol.XING_HAO, Symbol.XING_HAO);

		if (config != null && !config.getBuild()) {
			return false;
		}
		config = getAlarmConfByCache(Symbol.XING_HAO, alarm.getPlatform(), Symbol.XING_HAO, Symbol.XING_HAO);
		if (config != null && !config.getBuild()) {
			return false;
		}
		config = getAlarmConfByCache(alarm.getCode(), alarm.getPlatform(), Symbol.XING_HAO, Symbol.XING_HAO);
		if (config == null || !config.getBuild()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取告警信息
	 * 
	 * @param alarm
	 * @return
	 */
	public MAlarmMsg getMAlarmMsgConfigOfUse(MAlarm alarm) {
		MAlarmMsg alarmMsg = getAlarmMsgByCache(alarm.getCode(), alarm.getPlatform(), alarm.getSystems());
		if (ObjectUtils.isEmpty(alarmMsg)) {
			alarmMsg = getAlarmMsgByCache(alarm.getCode(), alarm.getPlatform(), Symbol.XING_HAO);
		}
		if (ObjectUtils.isEmpty(alarmMsg)) {
			alarmMsg = getAlarmMsgByCache(alarm.getCode(), Symbol.XING_HAO, Symbol.XING_HAO);
		}
		return alarmMsg;
	}

	/**
	 * 获取告警配置
	 * 
	 * @param alarm
	 * @return
	 */
	public MAlarmConfig getMAlarmConfigOfUse(MAlarm alarm) {
		MAlarmConfig config = getAlarmConfByCache(alarm.getCode(), alarm.getPlatform(), alarm.getSystems(),
				alarm.getJob());
		if (ObjectUtils.isEmpty(config)) {
			config = getAlarmConfByCache(alarm.getCode(), alarm.getPlatform(), alarm.getSystems(), Symbol.XING_HAO);
		}
		if (ObjectUtils.isEmpty(config)) {
			config = getAlarmConfByCache(alarm.getCode(), alarm.getPlatform(), Symbol.XING_HAO, Symbol.XING_HAO);
		}
		if (ObjectUtils.isEmpty(config)) {
			config = getAlarmConfByCache(Symbol.XING_HAO, alarm.getPlatform(), Symbol.XING_HAO, Symbol.XING_HAO);
		}
		return config;
	}

	/**
	 * 处理告警信息，并入库
	 * 
	 * @param alarm
	 */
	public void deal(MAlarm alarm) {
		try {
			if (!checkMasterControlOpen(alarm)) {
				logger.info("malarm not open insert db malarm:{}", alarm);
				return;
			}
			MAlarmConfig alarmConfig = getMAlarmConfigOfUse(alarm);
			if (alarmConfig == null || !alarmConfig.getBuild()) {
				logger.info("alarmConfig is null ,alarm:{}", alarm);
				return;
			}
			AlarmStatus status = AlarmStatus.indexOf(alarmConfig.getDefStatus());
			if (ObjectUtils.isEmpty(status)) {
				status = AlarmStatus.INFO;
			}
			alarm.setAlarmStatus(status.getName());
			alarm.setNoticeCyce(alarmConfig.getNoticeCycle());
			alarm.setNoticeTimes(alarmConfig.getNoticeTimes());
			alarm.setNoticeGroupName(alarmConfig.getNoticeGroupName());
			MAlarmMsg alarmMsg = getMAlarmMsgConfigOfUse(alarm);
			if (ObjectUtils.isNotEmpty(alarmMsg)) {
				if (ObjectUtils.isEmpty(alarm.getTitle())) {
					alarm.setTitle(alarmMsg.getTitle());
				}
				if (ObjectUtils.isEmpty(alarm.getSrcContent())) {
					alarm.setContent(alarmMsg.getContent());
				}

				if (ObjectUtils.isEmpty(alarm.getAlarmType())) {
					alarm.setAlarmType(alarmMsg.getAlarmType());
				}
				if (ObjectUtils.isEmpty(alarm.getAlarmLevel())) {
					alarm.setAlarmType(alarmMsg.getAlarmLevel());
				}
			}
			String content = alarm.getContent();
			if (!ObjectUtils.isEmpty(alarm.getSrcContent())) {
				content = alarm.getSrcContent();
			}
			content = content == null ? "" : content;
			if (!ObjectUtils.isEmpty(alarm.getSrcParam())) {
				content = String.format(content, alarm.getSrcParam().split(Symbol.DOU_HAO));
			}
			content = content.replace("${job}", alarm.getJob());
			content = content.replace("${systems}", alarm.getSystems());
			content = content.replace("${platform}", alarm.getPlatform());
			content = content.replace("${code}", alarm.getCode());
			content = content.replace("${level}", alarm.getAlarmLevel());
			content = content.replace("${type}", alarm.getAlarmType());
			alarm.setContent(content);
			alarmMapper.insertSelective(alarm);
			logger.info("create malarm insert db malarm:{}", alarm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从数据库获取待发送告警信息，加入发送队列
	 */
	public void selectSendAlarmByDb() {
		List<MAlarm> mAlarmList = alarmMapper.selectSendAlarmByDb(AlarmStatus.INFO.getName(),
				AlarmStatus.WARN.getName());
		for (MAlarm mAlarm : mAlarmList) {
			inserMalarmSend(mAlarm);
		}
	}

	public void inserMalarmSend(MAlarm alarm) {
		List<MAlarmSendQueue> updateList = new ArrayList<MAlarmSendQueue>();
		MAlarmSendQueue alarmSendQueue = new MAlarmSendQueue();
		alarmSendQueue.setAlarmId(alarm.getId());
		alarmSendQueue.setContent(alarm.getContent());
		alarmSendQueue.setTitle(alarm.getTitle());
		alarmSendQueue.setGroupName(alarm.getNoticeGroupName());
		alarmSendQueue.setSendStatus(SendStatus.NEW.name());
		List<MAlarmUserGroup> groupList = alarmUserGroupMapper.selectListByGroupName(alarm.getNoticeGroupName());
		for (MAlarmUserGroup group : groupList) {
			alarmSendQueue.setSendParams(group.getSendParams());
			alarmSendQueue.setSendType(group.getSendType());
			updateList.add(alarmSendQueue);
		}
		if (!CollectionUtils.isEmpty(updateList)) {
			alarmSendQueueMapper.insertMultipleByAlarm(updateList);
			logger.info("alarm send queue msg list create :{}", updateList);
			alarm.setId(alarm.getId());
			alarm.setNoticeSendTime(LocalDateTime.now());
			alarm.setNoticeCount(alarm.getNoticeCount() + 1);
			alarmMapper.updateByPrimaryKeySelective(alarm);
			logger.info("update malarm :{}", alarm);
		}
	}

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					continue;
				}
				MAlarm alarm = dealQueue.take();
				if (alarm != null) {
					executor.execute(() -> deal(alarm));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		STOP.set(false);
	}

	public void stop() {
		STOP.set(true);
		dealQueue.clear();
	}
}
