package cn.spdb.harrier.rpc.transport.File;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;

import cn.spdb.harrier.common.uitls.FileUtils;
import cn.spdb.harrier.rpc.common.RpcServiceHandler;

@RpcServiceHandler("IRpcFileService")
public class RpcFileService implements IRpcFileService {

	public FileMessage loadFile(String loadPath, Long streamId, Long pos, Integer bufferSize) {
		File file = new File(loadPath);
		FileMessage fileMessage = new FileMessage();
		fileMessage.setStreamId(streamId);
		if (!file.exists()) {
			return fileMessage;
		}
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");) {
			accessFile.seek(pos);
			long remainingLenth = accessFile.length() - pos;
			bufferSize = (int) (remainingLenth > bufferSize ? bufferSize : remainingLenth);
			byte[] bytes = new byte[bufferSize];
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
		if (pos == 0 && file.exists()) {
			return null;
		}
		FileMessage fileUploadResp = new FileMessage();
		fileUploadResp.setStreamId(streamId);
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw");) {
			accessFile.seek(pos);
			accessFile.write(data);
			fileUploadResp.setPos(accessFile.getFilePointer());
			fileUploadResp.setStatus(FileStatus.UPLOAD_FILE.status());
			return fileUploadResp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return FileUtils.deleteQuietly(file);
		}
		return false;
	}

	@Override
	public Boolean createDirFile(String path) {
		File directory = new File(path);
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				if (directory.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<FileAtt> selectFiles(String path) {
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
}
