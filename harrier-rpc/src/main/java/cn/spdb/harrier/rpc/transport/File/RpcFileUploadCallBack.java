package cn.spdb.harrier.rpc.transport.File;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import cn.spdb.harrier.rpc.common.InterfaceRpcCallBack;
import cn.spdb.harrier.rpc.common.ThreadPoolManager;

public class RpcFileUploadCallBack implements InterfaceRpcCallBack {

	@Override
	public void run(Object object) {
		if (!FileMessage.class.isInstance(object)) {
			return;
		}
		FileMessage fileUploadResp = (FileMessage) object;
		ThreadPoolManager.getInstance().addExecute(() -> deal(fileUploadResp));
	}

	public void deal(FileMessage fileUploadResp) {
		FileFuture fileLoadFuture = FileManager.getInstance().getFileLoadFuture(fileUploadResp.getStreamId());
		if (fileLoadFuture == null) {
			return;
		}
		File file = new File(fileLoadFuture.getLocalPath());
		if (!file.exists() || fileUploadResp.getPos() != fileLoadFuture.getPos()) {
			fileUploadResp.setStatus(FileStatus.FAIL_FILE.status());
		} else {
			try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
				long length = accessFile.length() - fileLoadFuture.getPos();
				length = length > FileManager.UPLOAD_BUFFER_SIZE ? FileManager.UPLOAD_BUFFER_SIZE : length;
				byte[] data = new byte[(int) length];
				accessFile.seek(fileLoadFuture.getPos());
				int read = accessFile.read(data);
				if (length == 0 || read <= 0) {
					fileUploadResp.setStatus(FileStatus.SUCC_FILE.status());
				}
				fileUploadResp.setData(data);
				fileLoadFuture.setPos(accessFile.getFilePointer());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileLoadFuture.done(fileUploadResp);
	}

}
