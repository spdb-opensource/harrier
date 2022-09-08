
package cn.spdb.harrier.common.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * server address
 */
public class Host implements Serializable {

	private static final long WARM_UP_TIME = 10 * 60 * 1000;

	private String ip;

	private int port;

	private String name;

	public Host() {
	}

	public Host(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public Host(String ip, Integer port, String serverName) {
		this.ip = ip;
		this.port = port;
		this.name = serverName;
	}

	public String getAddress() {
		return ip + ":" + port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Host host = (Host) o;
		return Objects.equals(getAddress(), host.getAddress());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAddress());
	}

	@Override
	public String toString() {
		return "Host{" + "address='" + getAddress() + '\'' + '}';
	}

	/**
	 * warm up
	 */
	private int getWarmUpWeight(int weight, long startTime) {
		long uptime = System.currentTimeMillis() - startTime;
		if (uptime > 0 && uptime < WARM_UP_TIME) {
			return (int) (weight * ((float) uptime / WARM_UP_TIME));
		}
		return weight;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
