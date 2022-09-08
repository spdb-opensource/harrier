
package cn.spdb.harrier.api.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class FourLetterWordMain {

	private static final int DEFAULT_SOCKET_TIMEOUT = 5000;
	protected static final Logger LOG = LoggerFactory.getLogger(FourLetterWordMain.class);

	private FourLetterWordMain() {
		throw new IllegalStateException("FourLetterWordMain class");
	}

	/**
	 * Send the 4letterword
	 * 
	 * @param host the destination host
	 * @param port the destination port
	 * @param cmd  the 4letterword
	 * @return server response
	 * @throws java.io.IOException io exceptions
	 */
	public static String send4LetterWord(String host, int port, String cmd) throws IOException {
		return send4LetterWord(host, port, cmd, DEFAULT_SOCKET_TIMEOUT);
	}

	/**
	 * Send the 4letterword
	 * 
	 * @param host    the destination host
	 * @param port    the destination port
	 * @param cmd     the 4letterword
	 * @param timeout in milliseconds, maximum time to wait while connecting/reading
	 *                data
	 * @return server response
	 * @throws java.io.IOException io exceptions
	 */
	public static String send4LetterWord(String host, int port, String cmd, int timeout) throws IOException {
		Objects.requireNonNull(cmd, "cmd must not be null");
		LOG.info("connecting to {} {}", host, port);
		InetSocketAddress hostaddress = host != null ? new InetSocketAddress(host, port)
				: new InetSocketAddress(InetAddress.getByName(null), port);
		OutputStream outstream = null;
		try (Socket sock = new Socket()) {
			sock.setSoTimeout(timeout);
			sock.connect(hostaddress, timeout);
			outstream = sock.getOutputStream();
			outstream.write(cmd.getBytes());
			outstream.flush();
			// this replicates NC - close the output stream before reading
			sock.shutdownOutput();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				return sb.toString();
			}
		} catch (SocketTimeoutException e) {
			throw new IOException("Exception while executing four letter word: " + cmd, e);
		} finally {
			if (!ObjectUtils.isEmpty(outstream)) {
				outstream.close();
			}
		}
	}
}
