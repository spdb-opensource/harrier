package cn.spdb.harrier.common.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;


/**
 * 0-000000000-0000000000-0000000000-0000000000-0-0000000000-000000000000 long
 * 64 1为符号位 41位毫秒时间 10位机器编码 12位毫秒并发
 * 
 * @author T-luzl
 *
 */
public class SnowFlakeBuidID {

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

	public SnowFlakeBuidID(int workeid) {
		if (wokerIdMax <= workeid) {
			throw new RuntimeException(String.format("wokerIdMax is %d ,workeid %d is over", wokerIdMax, workeid));
		}
		this.workeid = workeid;
	}

	public SnowFlakeBuidID() {
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
			e.printStackTrace();
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
}
