package cn.spdb.harrier.server.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ethz.ssh2.SCPClient;
import cn.spdb.harrier.common.enmus.DownLoadResourceProtocol;
import cn.spdb.harrier.common.utils.DateUtils;
import cn.spdb.harrier.common.utils.FileUtils;
import cn.spdb.harrier.common.utils.HttpUtils;
import cn.spdb.harrier.common.utils.IPUtils;
import cn.spdb.harrier.common.utils.SshUtils;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsServer;
import cn.spdb.harrier.dao.utils.BeanContext;
import cn.spdb.harrier.rpc.transport.File.FileManager;
import cn.spdb.harrier.server.entity.JobStepBean;
import cn.spdb.harrier.server.worker.WorkerManagerThread;
import cn.spdb.harrier.service.aws.AwsUtils;

public class ResourceDownUtils {

	public static Boolean downloadResource(JobStepBean step) {
		try {
			if (ObjectUtils.isEmpty(step.getStepUri())
					|| "local".equals(step.getStepUri().getProtocol()) && StringUtils.isBlank(step.getStepPath())) {
				return true;
			}
			if (!"local".equals(step.getStepUri().getProtocol())) {
				File file = new File(step.getStepPath());
				if (file.exists()) {
					long ms = file.lastModified();
					LocalDateTime fixLd = DateUtils.date2LocalDateTime(new Date(ms));
					LocalDateTime updateTime = step.getUpdateTime();
					if (updateTime.compareTo(fixLd) < 0) {
						return true;
					} else {
						File destFile = new File(file.getPath() + "_old");
						if (destFile.exists()) {
							destFile.delete();
						}
						FileUtils.copyFile(file, destFile);
						file.delete();
					}
				}
			}
			return downloadResource(step.getStepUri());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * download resource f
	 *
	 * @param execLocalPath execLocalPath
	 * @param projectRes    projectRes
	 * @param logger        logger
	 */
	public static Boolean downloadResource(URI stepURI) {
		try {
			if (ObjectUtils.isEmpty(stepURI) || StringUtils.isBlank(stepURI.getPath())) {
				return false;
			}
			switch (DownLoadResourceProtocol.valueNaeOf(stepURI.getProtocol())) {
			case SPDB:
				if (StringUtils.isEmpty(stepURI.getAddress())) {
					UdsServer server = BeanContext.getBean(WorkerManagerThread.class).getLeaderMaster();
					if (ObjectUtils.isEmpty(server)) {
						return false;
					}
					stepURI = stepURI.setHost(server.getIp());
					stepURI = stepURI.setPort(server.getPort());
				}
				if (IPUtils.getHostIp().equals(stepURI.getHost())) {
					return true;
				}
				return FileManager.getInstance().loadFile(stepURI, stepURI.getPath(), true);
			case SCP:
				return SshUtils.load(stepURI, stepURI.getPath(), stepURI.getPath());
			case HTTP:
			case HTTPS:
				return HttpUtils.downLoadFormUrl(stepURI.toString(), stepURI.getPath());
			case AWS:
				return AwsUtils.load(stepURI, stepURI.getPath());
			default:
				return FileUtils.isFileAndExists(stepURI.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
