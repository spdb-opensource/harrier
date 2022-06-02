package cn.spdb.harrier.common.uitls;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * protocol://username:password@host:port/path?key=value&key=value#anchor
 *
 */
public class URI implements Serializable {

	private static final long serialVersionUID = -184548770219874134L;

	protected final String protocol;

	protected final String username;

	protected final String password;

	protected final String host;

	protected final int port;

	protected final String path;

	private final Map<String, String> parameters;

	protected final String anchor;

	protected URI() {
		this.anchor = "";
		this.protocol = null;
		this.username = null;
		this.password = null;
		this.host = null;
		this.port = 0;
		this.path = null;
		this.parameters = null;
	}

	public URI(String protocol, String host, int port) {
		this(protocol, null, null, host, port, null, (Map<String, String>) null, null);
	}

	public URI(String protocol, String host, int port, String[] pairs) { // varargs ... conflict with the following path
																			// argument, use array instead.
		this(protocol, null, null, host, port, null, toStringMap(pairs), null);
	}

	public URI(String protocol, String host, int port, Map<String, String> parameters) {
		this(protocol, null, null, host, port, null, parameters, null);
	}

	public URI(String protocol, String host, int port, String path) {
		this(protocol, null, null, host, port, path, (Map<String, String>) null, null);
	}

	public URI(String protocol, String host, int port, String path, String... pairs) {
		this(protocol, null, null, host, port, path, toStringMap(pairs), null);
	}

	public URI(String protocol, String host, int port, String path, Map<String, String> parameters) {
		this(protocol, null, null, host, port, path, parameters, null);
	}

	public URI(String protocol, String username, String password, String host, int port, String path) {
		this(protocol, username, password, host, port, path, (Map<String, String>) null, null);
	}

	public URI(String protocol, String username, String password, String host, int port, String path, String... pairs) {
		this(protocol, username, password, host, port, path, toStringMap(pairs), null);
	}

	public static Map<String, String> toStringMap(String... pairs) {
		Map<String, String> parameters = new HashMap<>();
		if (null == pairs || 0 > pairs.length) {
			return parameters;
		}

		if (pairs.length > 0) {
			if (pairs.length % 2 != 0) {
				throw new IllegalArgumentException("pairs must be even.");
			}
			for (int i = 0; i < pairs.length; i = i + 2) {
				parameters.put(pairs[i], pairs[i + 1]);
			}
		}
		return parameters;
	}

	public URI(String protocol, String username, String password, String host, int port, String path,
			Map<String, String> parameters, String anchor) {
		this.protocol = protocol;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = Math.max(port, 0);
		while (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		this.path = path;
		if (parameters == null) {
			parameters = new HashMap<>();
		} else {
			parameters = new HashMap<>(parameters);
		}
		this.parameters = Collections.unmodifiableMap(parameters);
		this.anchor = anchor;
	}

	public static String getAddress(String host, int port) {
		return port <= 0 ? host : host + ':' + port;
	}

	public static URI valueOf(String URI) {
		if (URI == null || (URI = URI.trim()).length() == 0) {
			throw new IllegalArgumentException("URI == null");
		}
		String protocol = null;
		String username = null;
		String password = null;
		String host = null;
		int port = 0;
		String path = null;
		Map<String, String> parameters = null;
		String anchor = null;
		int i = URI.indexOf("://");
		if (i >= 0) {
			if (i == 0) {
				throw new IllegalStateException("URI missing protocol: \"" + URI + "\"");
			}
			protocol = URI.substring(0, i);
			URI = URI.substring(i + 3);
		} else {
			i = URI.indexOf(":/");
			if (i >= 0) {
				if (i == 0) {
					throw new IllegalStateException("URI missing protocol: \"" + URI + "\"");
				}
				protocol = URI.substring(0, i);
				URI = URI.substring(i + 1);
			}
		}

		int poundIndex = URI.indexOf('#');
		if (poundIndex != -1) {
			anchor = URI.substring(i + 1);
			URI = URI.substring(0, poundIndex);
		}

		i = URI.indexOf('?'); // separator between body and parameters
		if (i >= 0) {
			String[] parts = URI.substring(i + 1).split("[&;]");
			parameters = new HashMap<>();
			for (String part : parts) {
				part = part.trim();
				if (part.length() > 0) {
					int j = part.indexOf('=');
					if (j >= 0) {
						String key = part.substring(0, j);
						String value = part.substring(j + 1);
						parameters.put(key, value);
					} else {
						parameters.put(part, part);
					}
				}
			}
			URI = URI.substring(0, i);
		}

		i = URI.indexOf('/');
		if (i >= 0) {
			path = URI.substring(i + 1);
			URI = URI.substring(0, i);
		}

		i = URI.lastIndexOf('@');
		if (i >= 0) {
			String tmp = URI.substring(0, i);
			int j = tmp.indexOf(':');
			if (j >= 0) {
				password = tmp.substring(j + 1);
				username = tmp.substring(0, j);
			} else {
				username = tmp;
			}
			URI = URI.substring(i + 1);
		}
		i = URI.lastIndexOf(':');
		if (i >= 0 && i < URI.length() - 1) {
			port = Integer.parseInt(URI.substring(i + 1));
			URI = URI.substring(0, i);
		}
		if (URI.length() > 0) {
			host = URI;
		}
		return new URI(protocol, username, password, host, port, path, parameters, anchor);
	}

	public static String encode(String value) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String decode(String value) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String appendDefaultPort(String address, int defaultPort) {
		if (address != null && address.length() > 0 && defaultPort > 0) {
			int i = address.indexOf(':');
			if (i < 0) {
				return address + ":" + defaultPort;
			} else if (Integer.parseInt(address.substring(i + 1)) == 0) {
				return address.substring(0, i + 1) + defaultPort;
			}
		}
		return address;
	}

	public String getProtocol() {
		return protocol;
	}

	public URI setProtocol(String protocol) {
		return new URI(protocol, username, password, host, port, path, getParameters(), anchor);
	}

	public String getUsername() {
		return username;
	}

	public URI setUsername(String username) {
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public String getPassword() {
		return password;
	}

	public URI setPassword(String password) {
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public String getAuthority() {
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			return null;
		}
		return (username == null ? "" : username) + ":" + (password == null ? "" : password);
	}

	public String getHost() {
		return host;
	}

	public URI setHost(String host) {
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public String getIp() {
		try {
			return InetAddress.getByName(host).getHostAddress();
		} catch (UnknownHostException e) {
			return host;
		}
	}

	public int getPort() {
		return port;
	}

	public URI setPort(int port) {
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public int getPort(int defaultPort) {
		return port <= 0 ? defaultPort : port;
	}

	public String getAddress() {
		return getAddress(host, port);
	}

	public URI setAddress(String address) {
		int i = address.lastIndexOf(':');
		String host;
		int port = this.port;
		if (i >= 0) {
			host = address.substring(0, i);
			port = Integer.parseInt(address.substring(i + 1));
		} else {
			host = address;
		}
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public String getPath() {
		return path;
	}

	public URI setPath(String path) {
		return new URI(getProtocol(), username, password, host, port, path, getParameters(), anchor);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + port;
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URI other = (URI) obj;
		if (anchor == null) {
			if (other.anchor != null)
				return false;
		} else if (!anchor.equals(other.anchor))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port != other.port)
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public char getParameter(String key, char defaultValue) {
		String value = getParameter(key);
		return StringUtils.isEmpty(value) ? defaultValue : value.charAt(0);
	}

	public boolean getParameter(String key, boolean defaultValue) {
		String value = getParameter(key);
		return StringUtils.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
	}

	public byte getPositiveParameter(String key, byte defaultValue) {
		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Byte.parseByte(value);
	}

	public short getParameter(String key, short defaultValue) {

		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Short.parseShort(value);
	}

	public int getParameter(String key, int defaultValue) {
		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	public double getParameter(String key, double defaultValue) {

		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Double.parseDouble(value);

	}

	public float getParameter(String key, float defaultValue) {

		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Float.parseFloat(value);

	}

	public long getParameter(String key, long defaultValue) {
		String value = getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return Long.parseLong(value);

	}

	public String getParameter(String key, String defaultValue) {
		String value = getParameter(key);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	public String[] getParameter(String key, String[] defaultValue) {
		String value = getParameter(key);
		return StringUtils.isEmpty(value) ? defaultValue : Pattern.compile("\\s*[,]+\\s*").split(value);
	}

	public InetSocketAddress toInetSocketAddress() {
		return new InetSocketAddress(host, port);
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(protocol);
		stringBuffer.append("://");
		if (StringUtils.isNotBlank(username)) {
			stringBuffer.append(username);
			if (StringUtils.isNotBlank(password)) {
				stringBuffer.append(":");
				stringBuffer.append(username);
			}
			stringBuffer.append("@");
		}
		if (StringUtils.isNotBlank(host)) {
			stringBuffer.append(host);
			if (port != 0) {
				stringBuffer.append(":");
				stringBuffer.append(port);
			}
		}
		if (StringUtils.isNotBlank(path)) {
			stringBuffer.append("/");
			stringBuffer.append(path);
		}
		if (ObjectUtils.isNotEmpty(parameters)) {
			stringBuffer.append("?");
			for (Entry<String, String> en : parameters.entrySet()) {
				if (StringUtils.isNotBlank(en.getKey()) && StringUtils.isNotBlank(en.getValue())) {
					stringBuffer.append(en.getKey()).append("=").append(en.getValue()).append("&");
				}
			}
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		if(StringUtils.isNotBlank(anchor)) {
			stringBuffer.append("#").append(anchor);
		}
		return stringBuffer.toString();
	}

}