
package cn.spdb.harrier.api.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * file utils
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * copy source file to target file
	 *
	 * @param file         file
	 * @param destFilename destination file name
	 */

	public static void copyFile(MultipartFile file, String destFilename) {
		try {

			File destFile = new File(destFilename);
			File destParentDir = new File(destFile.getParent());

			if (!destParentDir.exists()) {
				destParentDir.mkdirs();
			}

			Files.copy(file.getInputStream(), Paths.get(destFilename));
		} catch (IOException e) {
			logger.error("failed to copy file , {} is empty file", file.getOriginalFilename(), e);
		}
	}

	/**
	 * file to resource
	 *
	 * @param filename file name
	 * @return resource
	 * @throws MalformedURLException io exceptions
	 */
	public static Resource file2Resource(String filename) throws MalformedURLException {
		Path file = Paths.get(filename);

		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
			return resource;
		} else {
			logger.error("file can not read : {}", filename);
		}
		return null;
	}

	/**
	 * file convert String
	 * 
	 * @param file MultipartFile file
	 * @return file content string
	 */
	public static String file2String(MultipartFile file) {
		StringBuilder strBuilder = new StringBuilder();
		try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
				BufferedReader streamReader = new BufferedReader(inputStreamReader);) {
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				strBuilder.append(inputStr);
			}
		} catch (IOException e) {
			logger.error("file convert to string failed: {}", file.getName());
		}
		return strBuilder.toString();
	}

	/**
	 *@param destFileName 压缩后的文件名
	 * @param files 需要压缩的目录名
	 */
	public static void doArchiver(String destFileName, File... files) {
        File destFile = new File(destFileName);
 
        try (FileOutputStream fileOutputStream = new FileOutputStream(destFile);
             BufferedOutputStream bufferedWriter = new BufferedOutputStream(fileOutputStream);
             TarArchiveOutputStream tar = new TarArchiveOutputStream(bufferedWriter)) {
 
            tar.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
 
            for (File file : files) {
                addTarArchiveEntryToTarArchiveOutputStream(file, tar, "");
            }
        } catch(IOException e) {
        	logger.error("destFile compress failed: {}", destFile.getName());
        }
    }

	/**
	 * 添加压缩
	 * @param file
	 * @param tar
	 * @param prefix
	 * @throws IOException
	 */
	private static void addTarArchiveEntryToTarArchiveOutputStream(File file, TarArchiveOutputStream tar, String prefix)
			throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(file, prefix + File.separator + file.getName());

		if (file.isFile()) {
			entry.setSize(file.length());
			tar.putArchiveEntry(entry);
			try (FileInputStream fileInputStream = new FileInputStream(file);
					BufferedInputStream input = new BufferedInputStream(fileInputStream);) {
				IOUtils.copy(input, tar);
			}
			tar.closeArchiveEntry();
		} else {
			tar.putArchiveEntry(entry);
			tar.closeArchiveEntry();
			prefix += File.separator + file.getName();
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					addTarArchiveEntryToTarArchiveOutputStream(f, tar, prefix);
				}
			}
		}
	}

	/**
	 * 解压或解包文件
	 * 
	 * @param srcfile  需要解压或解包的源文件
	 * @param destpath 目标路径
	 * @param password 解压密码, 为null时表示不使用密码
	 * @throws IOException
	 * @throws WrongPassException
	 */
	public static void doUnArchiver(File srcfile, String destpath, String password) {
		byte[] buf = new byte[1024];
		try (TarArchiveInputStream tais = new TarArchiveInputStream(
				new BufferedInputStream(new FileInputStream(srcfile)))) {
			TarArchiveEntry tae = null;
			while ((tae = tais.getNextTarEntry()) != null) {
				File f = new File(destpath + "/" + srcfile.getName().substring(0, srcfile.getName().indexOf(".tar")) + "/" + tae.getName());
				if (tae.isDirectory()) {
					f.mkdirs();
				} else {
					File parent = f.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f))) {
						int len;
						while ((len = tais.read(buf)) != -1) {
							bos.write(buf, 0, len);
						}
						bos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传文件，支持pm,pl,py,txt,excel
	 * 
	 * @param files
	 * @param fileType
	 * @param platform
	 * @param systems
	 * @param job
	 * @return
	 */
	public static Map<String, Object> uploadFile(MultipartFile[] files, String fileType, String platform,
			String systems, String job, Integer version) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (!ArrayUtils.isEmpty(files)) {
			// 判断job工程目录是否存在，不存在则创建
			File uploadFile = new File(JobDeployPath.UPLOAD_SCRIPT_PATH.getValue());
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
			String autoHome = uploadFile + "/" + platform + "/" + systems + "/" + job + "/" + version;
			for (MultipartFile file : files) {
				if (file != null) {
					String path = null;// 文件路径
					String fileName = file.getOriginalFilename();// 原文件名
					if (fileType != null) {
						// 上传路径
						String realPath = "";
						if (("python".equalsIgnoreCase(fileType)) || ("perl".equalsIgnoreCase(fileType)) || ("shell".equalsIgnoreCase(fileType))
								|| ("sql".equalsIgnoreCase(fileType)) || ("python3".equalsIgnoreCase(fileType))) {
							realPath = autoHome + "/APP/" + platform + "/" + systems + "/" + job + "/bin";
						} else {
							realPath = autoHome + "/APP/unknown";
						}
						File existFile = new File(realPath);
						File[] filelist = existFile.listFiles();
						if (null == filelist || filelist.length == 0) {
							existFile.mkdirs();
						}
						// 设置文件存放路径
						path = realPath + "/" + fileName;
						if (null != filelist && filelist.length != 0) {
							for (File f : filelist) {
								if (!f.isDirectory()) {
									if (f.getName().equals(fileName)) {
										res.put("code", "文件覆盖成功_错误代码");
										res.put("msg", fileName + "文件覆盖成功");
									}
								}
							}
						}
						try (OutputStream fout = new FileOutputStream(new File(path));
								InputStream fin = file.getInputStream();) {

							byte[] buf = new byte[2048];
							int len = -1;
							while ((len = fin.read(buf)) != -1) {
								fout.write(buf, 0, len);
							}
							fout.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				res.put("code", "成功代码");
				res.put("msg", "上传成功");
				return res;
			}
		}
		return res;
	}

	/**
	 * 导入工程文件
	 * 
	 * @param file
	 * @param fileType
	 * @param platform
	 * @param systems
	 * @return
	 * @throws Exception
	 */
	public static File uploadTar(MultipartFile file, String fileType)
			throws Exception {
		// 判断job工程目录是否存在，不存在则创建
		File uploadProject = new File(JobDeployPath.UPLOAD_DEPLOY_TEMP_PATH.getValue());
		if (!uploadProject.exists()) {
			uploadProject.mkdirs();
		}
		String autoHome = uploadProject + "/";
		if (file != null) {
			String fileName = file.getOriginalFilename();// 原文件名

			if (fileType != null) {
				// 工程路径如果不存在则创建
				File existFile = new File(autoHome);
				File[] filelist = existFile.listFiles();
				if (null == filelist || filelist.length == 0) {
					existFile.mkdirs();
				}
				// 设置文件存放路径
				File projectFile = new File(autoHome + "/" + fileName);
				// 文件写入磁盘
				OutputStream fout = null;
				InputStream fin = null;
				try {
					fout = new FileOutputStream(projectFile);
					fin = file.getInputStream();
					byte[] buf = new byte[2048];
					int len = -1;
					while ((len = fin.read(buf)) != -1) {
						fout.write(buf, 0, len);
					}
					fout.flush();
				} finally {
					if (fout != null)
						fout.close();
					if (fin != null)
						fin.close();
				}
				return projectFile;
			}
		}
		return null;
	}
}
