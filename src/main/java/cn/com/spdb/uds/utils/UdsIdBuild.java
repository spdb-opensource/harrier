package cn.com.spdb.uds.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import cn.com.spdb.uds.core.rpc.handler.UdsHandlerManger;
import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;
import io.netty.util.internal.ConcurrentSet;

/**
 * 0-000000000-0000000000-0000000000-0000000000-0-0000000000-000000000000 long
 * 64 1为符号位 41位毫秒时间 10位机器编码 12位毫秒并发
 * 
 * @author T-luzl
 *
 */
public class UdsIdBuild {

	// 时间标记
	private final static long twepoch = 1588230837059L;
	// 机器表示编码位数
	private final static long wokerIdBits = 10L;
	// 毫秒内自增位数
	private final static long sequenceBits = 12L;
	// 最大机器标示ID
	private final static long wokerIdMax = -1L ^ (-1L << wokerIdBits);
	// 最大自增id
	private final static long sequenceIdMax = -1L ^ (-1L << sequenceBits);
	// 机器标记迁移
	private final static long woerkeIdShift = sequenceBits;
	// 时间戳迁移
	private final static long timestampLeftShift = wokerIdBits + sequenceBits;

	// 时间戳
	private long lastTimestamp = 0L;

	private final long workeid;

	private long sequence = 0;

	public UdsIdBuild(int workeid) {
		if (wokerIdMax <= workeid) {
			throw new RuntimeException(String.format("wokerIdMax is %d ,workeid %d is over", wokerIdMax, workeid));
		}
		this.workeid = workeid;
	}

	public UdsIdBuild() {
		long id = 0;
		StringBuffer buffer = new StringBuffer();
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
			if (networkInterface != null) {
				networkInterface.getDisplayName();
				byte[] mac = networkInterface.getHardwareAddress();
				buffer.append(mac);
			} else {
				buffer.append("loacl");
			}
			String name = ManagementFactory.getRuntimeMXBean().getName();
			if (!name.isEmpty()) {
				buffer.append(name);
			}
			id = (buffer.toString().hashCode() & 0xFFFFF) % (wokerIdMax + 1);
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
		}
		this.workeid = id;
	}

	private long timesTamp() {
		return System.currentTimeMillis();
	}

	public synchronized long getNextId() {
		long timesTamp = timesTamp();
		if (timesTamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"clock moved backwards,refusing to generate id for %d milliseconds", lastTimestamp - timesTamp));
		}
		if (timesTamp == lastTimestamp) {
			sequence = (sequence + 1) & sequenceIdMax;
			if (sequence == 0) {
				timesTamp = timesTampNext(timesTamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timesTamp;
		long nextId = ((timesTamp - twepoch) << timestampLeftShift) | (workeid << woerkeIdShift) | sequence;
		return nextId;
	}

	private long timesTampNext(final long timesTamp) {
		long tmptimesTamp = this.timesTamp();
		while (tmptimesTamp > timesTamp) {
			tmptimesTamp = this.timesTamp();
		}
		return tmptimesTamp;
	}

	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(999, 999, 10000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		int t = 0;
		while (true) {
			if (executor.getActiveCount() == 0) {
				for (int i = 1; i < 999; i++) {
					executor.execute(new udsrunable(i, t));
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
				}
			}
		}

	}

	private static ConcurrentSet<Long> testSet = new ConcurrentSet<Long>();

	private static class udsrunable implements Runnable {

		private int workeid = 0;

		private int t = 0;

		public udsrunable(int workeid, int t) {
			this.workeid = workeid;
			this.t = t;
		}

		@Override
		public void run() {
			try {
				UdsIdBuild udsIdBuild = new UdsIdBuild(workeid);
				for (int i = 0; i < 4000; i++) {
					long id = udsIdBuild.getNextId();
					if (!testSet.add(id)) {
						System.err.println(id);
					}
				}
//				System.out.println("t:"+t+"workeid:" + workeid);
			} catch (Exception e) {
				UdsLogger.logEvent(LogEvent.ERROR, e.getMessage());
			}
		}
	}
}
