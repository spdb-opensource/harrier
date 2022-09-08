package cn.spdb.harrier.api.service.system.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.spdb.harrier.api.service.system.FileDisposeService;
import cn.spdb.harrier.common.utils.FileUtils;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.rpc.client.RpcClient;
import cn.spdb.harrier.rpc.transport.File.FileAtt;
import cn.spdb.harrier.rpc.transport.File.FileManager;
import cn.spdb.harrier.rpc.transport.File.FileMessage;
import cn.spdb.harrier.rpc.transport.File.FileStatus;
import cn.spdb.harrier.rpc.transport.File.IRpcFileService;

@Service
public class FileDisposeServiceImpl implements FileDisposeService {

	@Override
	public Boolean deleteFile(URI uri) {
		if (StringUtils.isEmpty(uri.getProtocol()) || "local".equals(uri.getProtocol())) {
			return FileUtils.deleteQuietly(new File(uri.getPath()));
		} else if ("spdb".equals(uri.getProtocol())) {
			return FileManager.getInstance().deleteFile(uri, uri.getPath());
		} else {
			return false;
		}
	}

	@Override
	public boolean createDir(URI uri) {
		if (StringUtils.isEmpty(uri.getProtocol()) || "local".equals(uri.getProtocol())) {
			return FileUtils.createDirectory(uri.getPath());
		} else if ("spdb".equals(uri.getProtocol())) {
			return FileManager.getInstance().createDir(uri, uri.getPath());
		} else {
			return false;
		}
	}

	@Override
	public List<FileAtt> selectFile(URI uri,String fileName,String order) {
		List<FileAtt> allFileAtt = null;
		if (StringUtils.isEmpty(uri.getProtocol()) || "local".equals(uri.getProtocol())) {
			allFileAtt = selectFiles(uri.getPath());
		} else if ("spdb".equals(uri.getProtocol())) {
			allFileAtt = FileManager.getInstance().selectFile(uri, uri.getPath());
		}
		if(allFileAtt != null) {
			List<FileAtt> fileAtt = new ArrayList<FileAtt>();
			if(StringUtils.isNotBlank(fileName)){
				for(FileAtt file : allFileAtt) {
					if(StringUtils.contains(file.getFileName(), fileName)) {
						fileAtt.add(file);
					}
				}
				return fileAtt;
			}
		}
		return allFileAtt;
	}

	@Override
	public FileMessage loadFile(URI uri, Long streamId, Long pos, Long size) {
		FileMessage fileMessage = null;
		if ("local".equals(uri.getProtocol()) || StringUtils.isEmpty(uri.getProtocol())) {
			return loadFile(uri.getPath(), streamId, pos, size);
		} else if ("spdb".equals(uri.getProtocol())) {
			try {
				fileMessage = FileManager.getInstance().loadFile(uri);
			} catch (Exception e) {
				e.printStackTrace();
				fileMessage = new FileMessage();
				fileMessage.setStreamId(streamId);
				fileMessage.setStatus(FileStatus.FAIL_FILE.status());
			}
			return fileMessage;
		} else {
			return fileMessage;
		}

	}

	@Override
	public FileMessage uploadFile(URI uri, Long streamId, Long pos, byte[] data) {

		FileMessage fileMessage = null;

		if (StringUtils.isEmpty(uri.getProtocol()) || "local".equals(uri.getProtocol())) {
			return uploadFile(uri.getPath(), streamId, pos, data);
		} else if ("spdb".equals(uri.getProtocol())) {
			FileManager.getInstance().upLoad(uri, data);
			return fileMessage;
		} else {
			return fileMessage;
		}
	}

	private List<FileAtt> selectFiles(String path) {
		List<FileAtt> fileList = new ArrayList<FileAtt>();
		File file = new File(path);
		if (!file.exists()) {
			FileAtt att = selectFile(file);
			att.setExist(false);
			fileList.add(att);
			return fileList;
		}
		if (file.isDirectory()) {
			for (File pathFile : file.listFiles()) {
				FileAtt att = selectFile(pathFile);
				fileList.add(att);
			}
		} else {
			FileAtt att = selectFile(file);
			fileList.add(att);
		}
		return fileList;
	}

	private FileAtt selectFile(File file) {
		FileAtt fileAtt = new FileAtt(file.getPath());
		if (!file.exists()) {
			fileAtt.setExist(false);
		} else {
			fileAtt.setExist(true);
			fileAtt.setDir(file.isDirectory());
			fileAtt.setSize(file.length());
			fileAtt.setLastModTime(file.lastModified());
			fileAtt.setFileName(file.getName());
			fileAtt.setFilePath(file.getParent());
			fileAtt.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
			try {
				PosixFileAttributeView view = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class);
				if (view != null) {
					fileAtt.setUser(view.readAttributes().owner().getName());
					fileAtt.setGroup(view.readAttributes().group().getName());
					fileAtt.setCreatTime(view.readAttributes().creationTime().toMillis());
					String authority = PosixFilePermissions.toString(view.readAttributes().permissions());
					fileAtt.setAuthority(fileAtt.getDir() ? "d" + authority : "-" + authority);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileAtt;
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

	public FileMessage uploadFile(String remotePath, Long streamId, Long pos, byte[] data) {
		File file = new File(remotePath);
		FileMessage fileUploadResp = new FileMessage();
		fileUploadResp.setStreamId(streamId);
		if (pos == 0 && file.exists()) {
			fileUploadResp.setStatus(FileStatus.FAIL_FILE.status());
			return fileUploadResp;
		}
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw");) {
			accessFile.seek(pos);
			accessFile.write(data);
			fileUploadResp.setPos(accessFile.getFilePointer());
			fileUploadResp.setStatus(FileStatus.UPLOAD_FILE.status());
			return fileUploadResp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileUploadResp.setStatus(FileStatus.FAIL_FILE.status());
		return fileUploadResp;
	}
}
