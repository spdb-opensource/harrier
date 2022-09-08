package cn.spdb.harrier.common.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.utils.SshUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.common.utils.ZipUtils;

public class InstallScript {

	private List<URI> hostList = new ArrayList<URI>();

	private final Scanner scan = new Scanner(System.in);

	private String installPtah = "";

	private static final String yes = "y";
	private static final String no = "n";
	private static final List<String> yesOrNo = Arrays.asList(new String[] { yes, no });
	private static final String SERVER_TYPE = "SERVER_TYPE";

	private AtomicInteger ain = new AtomicInteger(0);

	public void install(String localFile, String remoteDir, Boolean start) {
		System.out.println("reomve install start");
		ain.set(hostList.size());
		hostList.forEach(uri -> {
			new Thread(() -> {
				try {
					String dir = remoteDir;
					if (ObjectUtils.isEmpty(dir)) {
						dir = "/home/" + uri.getUsername() + "/tmp/";
					}
					println(uri.getHost(), "upload state:" + SshUtils.upload(uri, localFile, dir));
					String fileName = new File(localFile).getName();
					String removeFileName = dir + "/" + fileName;
					String str = SshUtils.exe(uri, "ls -l " + dir + "| grep " + fileName);
					if (StringUtils.isEmpty(str)) {
						println(uri.getHost(), "not find file:" + removeFileName);
						return;
					}
					println(uri.getHost(),
							SshUtils.exe(uri, "unzip -d " + uri.getPath() + "/harrier/ " + removeFileName));
					if (start) {
						println(uri.getHost(), SshUtils.exe(uri, "bash " + uri.getPath()
								+ "/harrier/bin/harrier-daemon.sh" + " start " + uri.getParameter(SERVER_TYPE)));
						Thread.sleep(2 * 1000);
						println(uri.getHost(), SshUtils.exe(uri, "bash " + uri.getPath()
								+ "/harrier/bin/harrier-daemon.sh" + " status " + uri.getParameter(SERVER_TYPE)));

					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ain.decrementAndGet();
				}
			}).start();
		});
		System.out.println("reomve install end");

	}

	public void println(String prefix, String msg) {
		System.out.println(prefix + ":" + msg);
	}

	public void confrimHostList() {
		String tartgetStr = null;
		while (hostList.isEmpty() || yes.equals(tartgetStr)) {
			confrimHost();
			tartgetStr = scannerRun(yesOrNo, "please confrim whether it is added? please input y/n.");
		}
	}

	public void confrimHost() {
		String tartgetStr = null;
		URI uri = null;
		while (!yes.equals(tartgetStr)) {
			System.out.println("please enter the information in the following format:");
			System.out.println("user:password@ip:[port]/[installPath]");
			String message = scan.nextLine();
			uri = URI.valueOf("ssh://" + message);
			if (StringUtils.isEmpty(uri.getPath())) {
				installPtah = StringUtils.isBlank(installPtah) ? "home/" + uri.getUsername() : installPtah;
				uri = uri.setPath(installPtah);
			}
			tartgetStr = scannerRun(yesOrNo,
					"please confrim this information: \r\n" + getHostMessage(uri) + "please input y/n.");
		}
		String message = scannerRun(
				Arrays.asList(new String[] { "api-server", "master-server", "worker-server", "alarm-server",
						"standalone-server", "monitor-server", "scheduler-server" }),
				"please enter start service. \r\nenter choose <api-server|master-server|worker-server|alarm-server|standalone-server|monitor-server|scheduler-server>");
		uri = uri.addParameter(SERVER_TYPE, message);
		if (ObjectUtils.isNotEmpty(uri)) {
			hostList.add(uri);
		}
	}

	public void printLogo() {
		System.out.println("*******************************");
		System.out.println("welcome to harrier install");
		System.out.println("*******************************");
		System.out.println();
	}

	public void confrimInstallPath() {
		String tartgetStr = null;
		while (!yes.equals(tartgetStr)) {
			tartgetStr = scannerRun(yesOrNo,
					"please confrim this installation path:'" + installPtah + "'? please input y/n.");
			if (no.equals(tartgetStr)) {
				System.out.println("please input installation path.");
				installPtah = scan.nextLine();
			} else {
				System.out.println("this installation path:" + installPtah);
			}
		}
	}

	public String scannerRun(List<String> strList, String print) {
		String tartgetStr = null;
		while (ObjectUtils.isEmpty(tartgetStr)) {
			System.out.println(print);
			String str = scan.nextLine();
			if (strList.contains(str)) {
				return tartgetStr = str;
			}
		}
		return null;
	}

	private String getHostMessage(URI uri) {
		StringBuffer buffer = new StringBuffer();
		if (uri.getPort() > 0) {
			buffer.append("port:").append(uri.getPort()).append("\r\n");
		}
		buffer.append("user:").append(uri.getUsername()).append("\r\n");
		buffer.append("host:").append(uri.getHost()).append("\r\n");
		buffer.append("password:").append(uri.getPassword()).append("\r\n");
		buffer.append("installPath:").append(uri.getPath()).append("\r\n");
		if (!MapUtils.isEmpty(uri.getParameters())) {
			System.out.println("Parameter:");
			buffer.append("Parameter:\n").append(JSON.toJSONString(uri.getParameters())).append("\r\n");
		}
		return buffer.toString();
	}

	public void dBInit(String dbConfig, String sqlDir) {
		URI uri = null;
		String tartgetStr = null;
		String url = "";
		while (!yes.equals(tartgetStr)) {
			System.out.println("pleas enter mysql database information in the following format:");
			System.out.println("user:password@ip:port");
			String message = scan.nextLine();
			uri = URI.valueOf("myqsl://" + message);
			System.out.println("host:" + uri.getHost());
			if (StringUtils.isBlank(uri.getPath())) {
				uri = uri.setPath("harrier");
			}
			if (uri.getPort() > 0) {
				url = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + "/" + "?autoReconnect=true";
				System.out.println("prot:" + uri.getPort());
			} else {
				url = "jdbc:mysql://" + uri.getHost() + "/" + "?autoReconnect=true";
			}
			System.out.println("url:" + url);
			tartgetStr = scannerRun(yesOrNo, "please confrim this installation, please input y/n.");
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		try (Connection connection = DriverManager.getConnection(url, uri.getUsername(), uri.getPassword());) {
			connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS `" + uri.getPath() + "` ");
			url = URI.valueOf(url).setPath(uri.getPath()).toString();
			System.out.println("url:" + url);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("database do not connection");
			System.exit(1);
		}

		File file = new File(dbConfig);
		if (!file.exists()) {
			try (FileWriter fileWriter = new FileWriter(file);) {
				fileWriter.write("spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver\r\n");
				fileWriter.write("spring.datasource.url=" + url + "\r\n");
				fileWriter.write("spring.datasource.username=" + uri.getUsername() + "\r\n");
				fileWriter.write("spring.datasource.password=" + uri.getPassword() + "\r\n");
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (yes.equals(scannerRun(yesOrNo, "please confrim initialize database, please input y/n."))) {
			try (Connection connection = DriverManager.getConnection(url, uri.getUsername(), uri.getPassword());) {
				UdsSqlScriptRunner scriptRunner = new UdsSqlScriptRunner(connection, null);
				scriptRunner.setSendFullScript(false);
				scriptRunner.setStopOnError(true);
				scriptRunner.setThrowWarning(true);
				Stream.of(new File(sqlDir).listFiles()).filter(predicate -> predicate.getName().endsWith(".sql"))
						.sorted((o1, o2) -> {
							if (o1.getName().startsWith("ddl.sql") && o2.getName().startsWith("dml.sql")) {
								return -1;
							} else if (o1.getName().startsWith("dml.sql") && o2.getName().startsWith("ddl.sql")) {
								return 1;
							}
							return 0;
						}).forEach(action -> {
							try {
								scriptRunner.runScript(new FileReader(action));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						});
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean isRun() {
		return ain.get() > 0;
	}

	public static void main(String[] args) {
		String harrierDir = "";
		if (args.length < 1) {
			System.err.println("harrierDir:[" + harrierDir + "] is null");
			System.exit(0);
		}
		harrierDir = args[0];
		System.out.println("harrierDir:[" + harrierDir + "]");

		String localZip = harrierDir + "/tmp/harrier.zip";
		if (args.length > 1 && StringUtils.isBlank(args[1])) {
			localZip = args[1];
		}
		System.out.println("localZip :[" + localZip + "]");

		String remoteDir = "/tmp";
		if (args.length > 2 && StringUtils.isBlank(args[2])) {
			remoteDir = args[2];
		}
		System.out.println("upload dir :[" + remoteDir + "]");

		File file = new File(localZip);
		if (!file.exists() || !file.isFile()) {
			try {
				System.out.println("start zip");
				ZipUtils.zip(localZip, new String[] { harrierDir + "/bin", harrierDir + "/conf", harrierDir + "/jre",
						harrierDir + "/lib" });
				System.out.println("end zip");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		System.out.println("local installation zip file path:" + file.getAbsolutePath());
		InstallScript installScript = new InstallScript();
		installScript.printLogo();
		installScript.dBInit(harrierDir + "/conf/datasource.properties", harrierDir + "/sql");
		installScript.confrimInstallPath();
		installScript.confrimHostList();
		String tartgetStr = installScript.scannerRun(yesOrNo,
				"whether to start immediately after installation ? please input y/n.");
		Boolean start = yes.equals(tartgetStr);
		tartgetStr = installScript.scannerRun(yesOrNo,
				"please confrim whether to install,enter 'n' to exit? please input y/n.");
		if (no.equals(tartgetStr)) {
			System.exit(0);
		}
		installScript.install(localZip, remoteDir, start);
		System.out.println("The installation is comolete.Exit after 3 sesconds");
		while (installScript.isRun()) {
			try {
				System.out.println("runing...");
				Thread.sleep(3 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
