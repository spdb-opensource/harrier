package cn.spdb.harrier.api.service.develop.impl;

import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IDeployScriptService;
import cn.spdb.harrier.api.service.job.UdsServerService;
import cn.spdb.harrier.api.utils.FileUtils;
import cn.spdb.harrier.common.utils.CollectionUtils;
import cn.spdb.harrier.common.utils.StringUtils;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.rpc.transport.File.FileManager;
import cn.spdb.harrier.service.aws.AwsOperator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DeployScriptServiceImpl implements IDeployScriptService {

	private static final Logger logger = LoggerFactory.getLogger(DeployScriptServiceImpl.class);

	@Autowired
	public Environment env;

	@Autowired
	public AwsOperator awsOperator;

	@Autowired
	public UdsServerService udsServerService;

	@Override
	public boolean deployScript(DyJobArrange dyJobArrange) {
		boolean deployStatus = false;
		if (null != dyJobArrange) {
			// 作业配置信息
			String jobYamlConfig = dyJobArrange.getDeployYaml();
			Yaml yaml = new Yaml();
			JobYamlObject jobYamlObject = yaml.loadAs(jobYamlConfig, JobYamlObject.class);

			// 获取脚本配置信息
			List<JobStepConfig> jobStepList = jobYamlObject.getJobStep();
			if (!CollectionUtils.isEmpty(jobStepList)) {
				for (JobStepConfig jobStep : jobStepList) {
					if (StringUtils.isNotBlank(jobStep.getScriptPath())) {
						String sPath = "";
						String dPath = "";
						try {
							switch (getProtocol(jobStep.getScriptPath())) {
							case "nas":
								sPath = getSourceFilePath(jobYamlObject.getPlatform(), jobYamlObject.getSystems(),
										jobYamlObject.getJob(), jobStep.getScriptPath(), jobYamlObject.getVersion());
								dPath = getDestFilePath(jobStep.getScriptPath());
								boolean isSuccessNas = localDeploy(sPath, dPath);
								if (!isSuccessNas) {
									logger.error("本地部署失败！");
								}
								deployStatus = isSuccessNas;
								break;
							case "local":
								sPath = getSourceFilePath(jobYamlObject.getPlatform(), jobYamlObject.getSystems(),
										jobYamlObject.getJob(), jobStep.getScriptPath(), jobYamlObject.getVersion());
								dPath = getDestFilePath(jobStep.getScriptPath());
								int countW = 0;
								List<UdsServer> availableWorker = udsServerService.getAvailableWorker();
								for (UdsServer server : availableWorker) {
									boolean isDeleteS = FileManager.getInstance()
											.deleteFile(new URI("spdb", server.getIp(), 10286, dPath), dPath);
									boolean isSuccessRpc = FileManager.getInstance()
											.upLoadFile(new URI("spdb", server.getIp(), 10286, dPath), sPath, false);
									if (!isSuccessRpc && !isDeleteS) {
										logger.error("主机：" + server.getIp() + ", 部署文件失败！");
										continue;
									}
									countW++;
								}
								if (countW > 0) {
									deployStatus = true;
								}
								break;
							case "aws":
								sPath = getSourceFilePath(jobYamlObject.getPlatform(), jobYamlObject.getSystems(),
										jobYamlObject.getJob(), jobStep.getScriptPath(), jobYamlObject.getVersion());
								dPath = getDestFilePath(jobStep.getScriptPath());
								Boolean isSuccessAws = awsOperator.upload(dPath, sPath);
								if (!isSuccessAws) {
									logger.error("对象存储上传失败！");
								}
								deployStatus = isSuccessAws;
								break;
							case "spdb":
								sPath = getSourceFilePath(jobYamlObject.getPlatform(), jobYamlObject.getSystems(),
										jobYamlObject.getJob(), jobStep.getScriptPath(), jobYamlObject.getVersion());
								dPath = getDestFilePath(jobStep.getScriptPath());
								int countM = 0;
								List<UdsServer> availableMaster = udsServerService.getAvailableMaster();
								for (UdsServer server : availableMaster) {
									boolean isDeleteM = FileManager.getInstance()
											.deleteFile(new URI("spdb", server.getIp(), 10286, dPath), dPath);
									boolean isSuccessRpc = FileManager.getInstance()
											.upLoadFile(new URI("spdb", server.getIp(), 10286, dPath), sPath, false);
									if (!isSuccessRpc && !isDeleteM) {
										logger.error("主机：" + server.getIp() + ", 部署文件失败！");
										continue;
									}
									countM++;
								}
								if (countM > 0) {
									deployStatus = true;
								}
								break;
							case "scp":
								System.out.println("远程拷贝逻辑");
								break;
							default:
								deployStatus = false;
								System.out.println("不支持的存储方式！！");
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						deployStatus = false;
						logger.error(jobYamlObject.getJob() + "未搜索到相关脚本，请检查作业配置信息！");
					}
				}
			}
		}
		return deployStatus;
	}

	/**
	 * 从脚本路径中获取协议名称
	 * 
	 * @param scriptPath
	 * @return
	 */
	public String getProtocol(String scriptPath) {
		String protocol = "";
		if (StringUtils.isNotBlank(scriptPath)) {
			if (scriptPath.contains("local")) {
				protocol = "local";
			} else if (scriptPath.contains("nas")) {
				protocol = "nas";
			} else if (scriptPath.contains("aws")) {
				protocol = "aws";
			} else if (scriptPath.contains("spdb")) {
				protocol = "spdb";
			} else if (scriptPath.contains("scp")) {
				protocol = "scp";
			}
		}
		return protocol;
	}

	/**
	 * 源路径
	 * 
	 * @param platform
	 * @param systems
	 * @param job
	 * @param scriptPath
	 * @param version
	 * @return
	 */
	public String getSourceFilePath(String platform, String systems, String job, String scriptPath, Integer version) {
		String sPath = "";
		// 根路径
		String rootDevPath = env.getProperty("UPLOAD_SCRIPT_PATH");
		String rootDeployPath = env.getProperty("UPLOAD_DEPLOY_TEMP_PATH");
		String[] split = scriptPath.split("/");
		// 脚本文件名
		String fileName = split[split.length - 1];
		if (!fileName.contains(".")) {
			logger.error("脚本文件名不符合规范，请确认！");
			return "";
		}
		// 判断文件是否存在
		sPath = rootDevPath + "/" + platform + "/" + systems + "/" + job + "/" + version + "/" + "APP" + "/" + platform
				+ "/" + systems + "/" + job + "/bin/" + fileName;
		File file = new File(sPath);
		if (file.exists()) {
			return sPath;
		}
		return rootDeployPath + "/" + job + "/" + version + "/" + "APP" + "/" + platform + "/" + systems + "/" + job
				+ "/bin/" + fileName;

	}

	public String getDestFilePath(String filePath) {
		if (filePath.contains("local")) {
			return filePath.replace("local:///", "");
		} else if (filePath.contains("aws:///")) {
			return filePath.replace("aws:///", "");
		} else if (filePath.contains("spdb:///")) {
			return filePath.replace("spdb:///", "");
		} else if (filePath.contains("nas:///")) {
			return filePath.replace("nas:///", "");
		}
		return filePath;
	}

	/**
	 * 本地文件部署
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 * @return
	 */
	public boolean localDeploy(String sourceFilePath, String destFilePath) {
		// 判断文件是否存在,不存在则直接拷贝，存在则比较md5值，如不相同则执行拷贝覆盖
		File sourceFile = new File(sourceFilePath);
		File destFile = new File(destFilePath);
		if (sourceFile.exists() && !destFile.exists()) {
			FileUtils.copyFile(sourceFile, destFilePath);
			return true;
		} else if (sourceFile.exists() && destFile.exists()) {
			Boolean notLike=false;
			try (FileInputStream sourceFile1 = new FileInputStream(sourceFile);
					FileInputStream destFilePath1 = new FileInputStream(destFilePath)) {
				notLike=!DigestUtils.md5Hex(sourceFile1).equals(DigestUtils.md5Hex(destFilePath1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (notLike) {
				FileUtils.copyFile(sourceFile, destFilePath);
				return true;
			}
			logger.info("目标文件与需部署的文件一致，无需部署！");
			return true;
		} else {
			logger.error("源文件不存在，无法执行拷贝任务！");
			return false;
		}
	}
}
