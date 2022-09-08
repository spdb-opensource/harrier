
package cn.spdb.harrier.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPUtils {

	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	private static String IP_REGEX = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	private static String LOCAL_HOST = "unknown";

	static {
		String host = System.getenv("HOSTNAME");
		if (isNotEmpty(host)) {
			LOCAL_HOST = host;
		} else {

			try {
				String hostName = InetAddress.getLocalHost().getHostName();
				if (isNotEmpty(hostName)) {
					LOCAL_HOST = hostName;
				}
			} catch (UnknownHostException e) {
				logger.error("get hostName error!", e);
			}
		}
	}


	public static String getLocalHost() {
		return LOCAL_HOST;
	}

	public static String getFirstNoLoopbackIP4Address() {
		Collection<String> allNoLoopbackIP4Addresses = getNoLoopbackIP4Addresses();
		if (allNoLoopbackIP4Addresses.isEmpty()) {
			return null;
		}
		return allNoLoopbackIP4Addresses.iterator().next();
	}

	public static Collection<String> getNoLoopbackIP4Addresses() {
		Collection<String> noLoopbackIP4Addresses = new ArrayList<>();
		Collection<InetAddress> allInetAddresses = getAllHostAddress();

		for (InetAddress address : allInetAddresses) {
			if (!address.isLoopbackAddress() && !address.isSiteLocalAddress()
					&& !Inet6Address.class.isInstance(address)) {
				noLoopbackIP4Addresses.add(address.getHostAddress());
			}
		}
		if (noLoopbackIP4Addresses.isEmpty()) {
			for (InetAddress address : allInetAddresses) {
				if (!address.isLoopbackAddress() && !Inet6Address.class.isInstance(address)) {
					noLoopbackIP4Addresses.add(address.getHostAddress());
				}
			}
		}
		return noLoopbackIP4Addresses;
	}

	public static Collection<InetAddress> getAllHostAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			Collection<InetAddress> addresses = new ArrayList<>();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}

			return addresses;
		} catch (SocketException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String getIpByHostName(String host) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			logger.error("get IP error", e);
		}
		if (address == null) {
			return "";
		}
		return address.getHostAddress();

	}

	private static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	private static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}

	public static boolean isIp(String addr) {
		if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}

		Pattern pat = Pattern.compile(IP_REGEX);

		Matcher mat = pat.matcher(addr);

		boolean ipAddress = mat.find();

		return ipAddress;
	}
	


	public static boolean internalIp(String ip) {
		byte[] addr = textToNumericFormatV4(ip);
		return internalIp(addr) || "127.0.0.1".equals(ip);
	}

	private static boolean internalIp(byte[] addr) {
		if (null==addr || addr.length < 2) {
			return true;
		}
		final byte b0 = addr[0];
		final byte b1 = addr[1];

		final byte SECTION_1 = 0x0A;

		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * 将IPv4地址转换成字节
	 * 
	 * @param text IPv4地址
	 * @return byte 字节
	 */
	public static byte[] textToNumericFormatV4(String text) {
		if (text.length() == 0) {
			return null;
		}

		byte[] bytes = new byte[4];
		String[] elements = text.split("\\.", -1);
		try {
			long l;
			int i;
			switch (elements.length) {
			case 1:
				l = Long.parseLong(elements[0]);
				if ((l < 0L) || (l > 4294967295L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l >> 24 & 0xFF);
				bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 2:
				l = Integer.parseInt(elements[0]);
				if ((l < 0L) || (l > 255L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l & 0xFF);
				l = Integer.parseInt(elements[1]);
				if ((l < 0L) || (l > 16777215L)) {
					return null;
				}
				bytes[1] = (byte) (int) (l >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 3:
				for (i = 0; i < 2; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				l = Integer.parseInt(elements[2]);
				if ((l < 0L) || (l > 65535L)) {
					return null;
				}
				bytes[2] = (byte) (int) (l >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 4:
				for (i = 0; i < 4; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				break;
			default:
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return bytes;
	}

	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}
		return "127.0.0.1";
	}

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		return "未知";
	}
	
}
