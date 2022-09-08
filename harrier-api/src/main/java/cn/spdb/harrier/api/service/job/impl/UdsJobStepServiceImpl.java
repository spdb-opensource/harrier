package cn.spdb.harrier.api.service.job.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.service.job.UdsJobStepService;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.entity.UdsJobStep;
import cn.spdb.harrier.dao.mapper.UdsJobStepMapper;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.rpc.transport.File.FileManager;
import cn.spdb.harrier.rpc.transport.File.FileMessage;
import cn.spdb.harrier.rpc.transport.File.FileStatus;
import cn.spdb.harrier.rpc.transport.File.IRpcFileService;
import cn.spdb.harrier.service.aws.AwsUtils;

@Service
public class UdsJobStepServiceImpl implements UdsJobStepService {
	@Autowired
	private UdsJobStepMapper mapper;

	/**
	 * 新增
	 */
	@Override
	public int add(UdsJobStep record) {
		return mapper.insertSelective(record);
	}

	/**
	 * id删除
	 */
	@Override
	public int delete(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改
	 */
	@Override
	public int update(UdsJobStep record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 查看详情
	 */
	@Override
	public UdsJobStep getDetail(Long id) {
		return mapper.selectByPrimaryKey(id).orElse(null);
	}

	/**
	 * 列表查询
	 */
	@Override
	public List<UdsJobStep> listQuery() {
		return mapper.select(null);
	}

    @Override
    public Page<UdsJobStep> selectJobStepList(Page<UdsJobStep> page, String platform, String system, String job) {
    	return mapper.selectJobStepPage(page, platform, system, job);
    }
    
    @Override
    public UdsJobStep downloadJobStep(String platform, String system, String job,Byte index) {
    	return mapper.selectJobStep(platform, system, job,index).orElse(null);
    }
   
    @Override
	public FileMessage downloadResource(URI stepURI, Long streamId, Long pos, Long size) {
		FileMessage fileMessage = new FileMessage();
		fileMessage.setStatus(FileStatus.FAIL_FILE.getType());
		fileMessage.setStreamId(streamId);
		try {
			if (ObjectUtils.isEmpty(stepURI) || StringUtils.isBlank(stepURI.getPath())) {
				return fileMessage;
			}
			switch (stepURI.getProtocol()) {
			case "spdb":
				return FileManager.getInstance().loadFile(stepURI);
			case "scp":
				break;
			case "http":
			case "https":
				try {
					URL url = new URL(stepURI.toString());
					HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
					conn.setConnectTimeout(3 * 1000);
					conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
					byte[] buffer = new byte[1024];
					int len = 0;
					try (InputStream inputStream = conn.getInputStream();
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
						while ((len = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, len);
						}
						outputStream.flush();
						buffer = outputStream.toByteArray();
					} catch (Exception e) {
						e.printStackTrace();
					}
					fileMessage.setData(buffer);
					fileMessage.setStatus(FileStatus.SUCC_FILE.getType());
					return fileMessage;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return fileMessage;
			case "aws":
				byte[] bytes = AwsUtils.load(stepURI, pos, size);
				if (bytes.length < size) {
					fileMessage.setStatus(FileStatus.SUCC_FILE.getType());
				} else {
					fileMessage.setStatus(FileStatus.LOAD_FILE.getType());
				}
				fileMessage.setPos(pos + bytes.length);
				fileMessage.setData(bytes);
				return fileMessage;
			default:
				return loadFile(stepURI.getPath(), streamId, pos, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileMessage;
	}

	public FileMessage loadFile(String loadPath, Long streamId, Long pos, Long bufferSize) {
		File file = new File(loadPath);
		FileMessage fileMessage = new FileMessage();
		fileMessage.setStreamId(streamId);
		if (!file.exists()) {
			fileMessage.setStatus(FileStatus.FAIL_FILE.status());
			return fileMessage;
		}
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");) {
			accessFile.seek(pos);
			long remainingLenth = accessFile.length() - pos;
			bufferSize = (remainingLenth > bufferSize ? bufferSize : remainingLenth);
			byte[] bytes = new byte[bufferSize.intValue()];
			int red = accessFile.read(bytes);
			if (red <= 0 || remainingLenth == 0) {
				fileMessage.setStatus(FileStatus.SUCC_FILE.status());
			} else {
				fileMessage.setStatus(FileStatus.LOAD_FILE.status());
			}
			fileMessage.setPos(accessFile.getFilePointer());
			fileMessage.setData(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileMessage;
	}

}