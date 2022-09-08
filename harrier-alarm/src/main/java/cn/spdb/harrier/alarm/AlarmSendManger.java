package cn.spdb.harrier.alarm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.alarm.enums.SendType;
import cn.spdb.harrier.alarm.send.modle.AlarmData;
import cn.spdb.harrier.alarm.send.modle.AlarmInfo;
import cn.spdb.harrier.alarm.send.modle.AlarmResult;
import cn.spdb.harrier.common.enmus.alarm.SendStatus;
import cn.spdb.harrier.common.utils.JSONUtils;
import cn.spdb.harrier.common.utils.NameThreadFactory;
import cn.spdb.harrier.common.utils.Stopper;
import cn.spdb.harrier.common.utils.ThreadUtils;
import cn.spdb.harrier.dao.entity.MAlarmSendQueue;
import cn.spdb.harrier.dao.mapper.MAlarmSendQueueMapper;

@Component
public class AlarmSendManger implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(AlarmSendManger.class.getSimpleName());

	private LinkedBlockingQueue<AlarmInfo> sendQueue = new LinkedBlockingQueue<AlarmInfo>();

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new NameThreadFactory(AlarmManger.class.getSimpleName()));

	private ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(
			Runtime.getRuntime().availableProcessors(),
			new NameThreadFactory(this.getClass().getSimpleName() + ":schedule"));

	private final AtomicBoolean STOP = new AtomicBoolean(false);

	@Autowired
	private MAlarmSendQueueMapper alarmSendQueueMapper;
	
	/**
	 * 添加到数据库，设置为待发送
	 * 
	 * @param alarmSendQueue
	 */
	public void inserMalarmSend(MAlarmSendQueue alarmSendQueue) {
		alarmSendQueue.setSendStatus(SendStatus.NEW.name());
		alarmSendQueueMapper.insert(alarmSendQueue);
		logger.info("alarm send queue msg create :{}", alarmSendQueue);
	}

	/**
	 * 定时检测数据库，获取待发送信息
	 */
	public void sendMalarmByDb() {
		List<MAlarmSendQueue> list = alarmSendQueueMapper.selectNeedSend(SendStatus.NEW.name());
		for (MAlarmSendQueue send : list) {
			try {
				AlarmInfo alarmInfo = new AlarmInfo();
				AlarmData alertData = new AlarmData();
				alertData.setTitle(send.getTitle());
				alertData.setContent(send.getContent());
				alarmInfo.setAlarmData(alertData);
				alarmInfo.setSendId(send.getId());
				alarmInfo.setSendType(send.getSendType());
				alarmInfo.setAlertParams(JSONUtils.toMap(send.getSendParams()));
				addSendQueue(alarmInfo);
				logger.debug("alarm info ready send :{}", alarmInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addSendQueue(AlarmInfo alarmInfo) {
		sendQueue.add(alarmInfo);
	}

	/**
	 * 发送信息
	 * 
	 * @param alarmInfo
	 */
	public void send(AlarmInfo alarmInfo) {
		logger.debug("alarm info send starat :{}", alarmInfo);
		AlarmResult alarmResult = SendType.send(alarmInfo);
		logger.debug("alarm info send end :{}", alarmInfo);
		MAlarmSendQueue record = new MAlarmSendQueue();
		record.setId(alarmInfo.getSendId());
		record.setSendStatus(alarmResult.getStatus());
		String msg = alarmResult.getMessage();
		msg = msg.length() < 126 ? msg : msg.substring(0, 126);
		record.setExpcetion(msg);
		record.setSendTime(LocalDateTime.now());
		alarmSendQueueMapper.updateByPrimaryKeySelective(record);
		logger.info("send alarm info {}", record);
	}

	@PostConstruct
	public void init() {
		ThreadUtils.newDaemonSingleThreadExecutor(AlarmSendManger.class.getSimpleName()).execute(this);
		scheduledService.scheduleAtFixedRate(() -> {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					return;
				}
				sendMalarmByDb();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 10, 10, TimeUnit.SECONDS);
		logger.info("start  {}", AlarmSendManger.class);
	}

	@Override
	public void run() {
		while (Stopper.isRunning()) {
			try {
				if (STOP.get()) {
					Thread.sleep(1000 * 10);
					continue;
				}
				AlarmInfo alarmInfo = sendQueue.take();
				if (alarmInfo != null) {
					executor.execute(() -> send(alarmInfo));
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
		sendQueue.clear();
	}
}
