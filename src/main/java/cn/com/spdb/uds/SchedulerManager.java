package cn.com.spdb.uds;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.quartz.CronExpression;
import org.springframework.core.NestedExceptionUtils;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import cn.com.spdb.uds.utils.DateUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;

public class SchedulerManager {

	/** 进行的定时任务 */
	private Map<String, ScheduledFuture<?>> scheduleMap = new HashMap<String, ScheduledFuture<?>>();

	private ScheduledExecutorService scheduledService;

	public static SchedulerManager instance;

	private static final Object KEY = new Object();

	public static SchedulerManager getInstance() {
		synchronized (KEY) {
			if (instance == null) {
				instance = new SchedulerManager();
				instance.init();
			}
		}
		return instance;
	}

	public void init() {
		scheduledService = Executors.newScheduledThreadPool(15, new NameThreadFactory(this.getClass().getSimpleName()));
		scheduleWithFixedDelay("UDS_LOGGER_ERROR_DB", new Runnable() {
			@Override
			public void run() {
				try {
					UdsLogger.updateMapErrorDb();
				} catch (Exception e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}, 30 * DateUtils.TIME_MILLSECOND_OF_SECOND, 2 * DateUtils.TIME_MILLSECOND_OF_MINUTE);
	}

	private void addSchedulerMap(String key, ScheduledFuture<?> future) {
		if (!StringUtils.isBlank(key)) {
			ScheduledFuture<?> scheduledFuture = scheduleMap.get(key);
			if (scheduledFuture != null && scheduledFuture.getDelay(TimeUnit.MILLISECONDS) > 0) {
				scheduledFuture.cancel(false);
			}
		}
		scheduleMap.put(key, future);
	}

	/**
	 * 加载入定时管理器
	 * 
	 * @param key    组名唯一变量 是NULL不加入管理
	 * @param task   任务
	 * @param delay  延时
	 * @param period 间隔循环执行时间
	 * @return
	 */
	public ScheduledFuture<?> scheduleAtFixedRate(String key, Runnable task, long delay, long period) {
		ScheduledFuture<?> future = scheduledService.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
		if (!StringUtils.isBlank(key)) {
			addSchedulerMap(key, future);
		}
		return future;
	}

	public ScheduledFuture<?> scheduleWithFixedDelay(String key, Runnable task, long delay, long period) {
		ScheduledFuture<?> future = scheduledService.scheduleWithFixedDelay(task, delay, period, TimeUnit.MILLISECONDS);
		if (!StringUtils.isBlank(key)) {
			addSchedulerMap(key, future);
		}
		return future;
	}

	/**
	 * 加载入定时管理器
	 * 
	 * @param key   组名唯一变量 是NULL不加入管理
	 * @param task  任务
	 * @param delay 延时时间
	 * @return
	 */
	public ScheduledFuture<?> schedule(String key, Runnable task, long delay) {
		ScheduledFuture<?> future = scheduledService.schedule(task, delay, TimeUnit.MILLISECONDS);
		if (!StringUtils.isBlank(key)) {
			addSchedulerMap(key, future);
		}
		return future;
	}

	/**
	 * 关闭指定组的定时任务
	 * 
	 * @param key
	 */
	public void shutTask(String key) {
		ScheduledFuture<?> scheduledFuture = scheduleMap.get(key);
		if (scheduledFuture != null && scheduledFuture.getDelay(TimeUnit.MILLISECONDS) > 0) {
			scheduledFuture.cancel(false);
		}
	}

	public void shutDown() {
		if (scheduledService != null) {
			scheduledService.shutdownNow();
		}
	}

	public Date getNextInvalidTimeAfter(String Cron) {
		Date date = null;
		try {
			CronExpression cronExpression = new CronExpression(Cron);
			date = cronExpression.getNextInvalidTimeAfter(new Date());
		} catch (ParseException e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		}
		return date;
	}

}
