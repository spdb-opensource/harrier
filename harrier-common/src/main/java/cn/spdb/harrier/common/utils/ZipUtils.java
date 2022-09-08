package cn.spdb.harrier.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.ArrayUtils;

public class ZipUtils {

	private static final int BUFFER_SIZE = 2 * 1024;

	public static void zip(String zipFile, String... srcDir) {
		zip(Arrays.stream(srcDir).map(mapper -> new File(mapper)).toArray(File[]::new), new File(zipFile), false, true);
	}

	public static void zip(File[] sourceFileList, File zipFile, boolean append, boolean KeepDirStructure) {
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile, append))) {
			Arrays.stream(sourceFileList)
					.forEach(sourceFile -> compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure));
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		}
	}

	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) {
		try {
			byte[] buf = new byte[BUFFER_SIZE];
			if (sourceFile.isFile()) {
				zos.putNextEntry(new ZipEntry(name));
				try (FileInputStream in = new FileInputStream(sourceFile)) {
					int len;
					while ((len = in.read(buf)) > 0) {
						zos.write(buf, 0, len);
					}
					zos.flush();
					zos.closeEntry();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				File[] listFiles = sourceFile.listFiles();
				if (ArrayUtils.isEmpty(listFiles)) {
					if (KeepDirStructure) {
						zos.putNextEntry(new ZipEntry(name + "/"));
						zos.flush();
						zos.closeEntry();
					}
				} else {
					for (File file : listFiles) {
						if (KeepDirStructure) {
							compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
						} else {
							compress(file, zos, file.getName(), KeepDirStructure);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void unZip(File srcFile, String destDirPath) {
		try {
			// 判断源文件是否存在
			if (!srcFile.exists()) {
				throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
			}
			// 开始解压
			try (ZipFile zipFile = new ZipFile(srcFile, StandardCharsets.UTF_8);) {
				Enumeration<?> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = (ZipEntry) entries.nextElement();
					if (entry.isDirectory()) {
						String dirPath = destDirPath + "/" + entry.getName();
						File dir = new File(dirPath);
						dir.mkdirs();
					} else {
						File targetFile = new File(destDirPath + "/" + entry.getName());
						if (!targetFile.getParentFile().exists()) {
							targetFile.getParentFile().mkdirs();
						}
						try (InputStream is = zipFile.getInputStream(entry);
								FileOutputStream fos = new FileOutputStream(targetFile);) {
							int len;
							byte[] buf = new byte[BUFFER_SIZE];
							while ((len = is.read(buf)) != -1) {
								fos.write(buf, 0, len);
							}
							fos.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			} catch (Exception e) {
				throw new RuntimeException("unzip error from ZipUtils", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		zip("D:\\test\\openjre\\openjdk-jre", "D:/test/openjre/openjre.zip");
//		unZip(new File("C:\\Users\\aRunner\\Desktop\\20181011.zip"), "C:\\Users\\aRunner\\Desktop");
	}
}
