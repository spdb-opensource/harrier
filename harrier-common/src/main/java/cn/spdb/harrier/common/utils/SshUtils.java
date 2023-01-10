package cn.spdb.harrier.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.lang3.ObjectUtils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SshUtils {

	public static Connection login(URI uri) throws IOException {
		final Connection connection = uri.getPort() <= 0 ? new Connection(uri.getHost())
				: new Connection(uri.getHost(), uri.getPort());
		connection.connect();
		if (!connection.isAuthenticationComplete()) {
			synchronized (SshUtils.class) {
				if (!connection.authenticateWithPassword(uri.getUsername(), uri.getPassword())) {
					return null;
				}
			}
		}
		return connection;
	}

	public static boolean upload(URI uri, String localFile, String remoteDir) {
		Connection connection = null;
		try {
			connection = login(uri);
			SCPClient scpClient = connection.createSCPClient();
			if (ObjectUtils.isEmpty(scpClient)) {
				return false;
			}
			scpClient.put(localFile, remoteDir);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ObjectUtils.isNotEmpty(connection)) {
				connection.close();
			}
		}
		return true;
	}

	public static boolean load(URI uri, String remoteFile, String localDir) {
		Connection connection = null;
		try {
			connection = login(uri);
			SCPClient scpClient = connection.createSCPClient();
			if (ObjectUtils.isEmpty(scpClient)) {
				return false;
			}
			scpClient.get(remoteFile, localDir);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ObjectUtils.isNotEmpty(connection)) {
				connection.close();
			}
		}
		return true;
	}


	/**
	 * 
	 * @param uri
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static String exe(URI uri, String cmd) throws IOException {
		Connection connection = null;
		Session session = null;
		StringBuffer buffer = new StringBuffer();
		try {
			connection = login(uri);
			session = connection.openSession();
			session.execCommand(cmd);
			InputStream stdout = new StreamGobbler(session.getStdout());
			InputStream stderr = new StreamGobbler(session.getStderr());
			String line = null;
			int conditions = 0;
			try (BufferedReader readerOut = new BufferedReader(new InputStreamReader(stdout));
					BufferedReader readerErr = new BufferedReader(new InputStreamReader(stderr));) {
				if (stdout.available() > 0) {
					while ((line = readerOut.readLine()) != null) {
						buffer.append(line).append(System.lineSeparator());
					}
				} else {
					if (stderr.available() > 0) {
						while ((line = readerErr.readLine()) != null) {
							buffer.append(line).append(System.lineSeparator());
						}
						throw new RuntimeException(buffer.toString());
					}
				}
				conditions = session.waitForCondition(
						ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA | ChannelCondition.EOF, 1000 * 5);
				if (stdout.available() > 0) {
					while ((line = readerOut.readLine()) != null) {
						buffer.append(line).append(System.lineSeparator());
					}
				} else {
					if (stderr.available() > 0) {
						throw new RuntimeException(buffer.toString());
					}
				}
				if ((conditions & ChannelCondition.TIMEOUT) != 0) {
					throw new RuntimeException("RUNING TIME OUT");
				}
				if ((conditions & ChannelCondition.EOF) != 0) {
					if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0) {
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ObjectUtils.isNotEmpty(session)) {
				session.close();
			}
			if (ObjectUtils.isNotEmpty(connection)) {
				connection.close();
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {

		SshUtils.upload(URI.valueOf(""),
				"D:\\gitdata\\harrier\\harrier-standalone-server\\target/lib.zip", "/home/uds");


	}
}
