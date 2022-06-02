package cn.spdb.harrier.rpc.transport.File;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import cn.spdb.harrier.common.uitls.FileUtils;
import cn.spdb.harrier.common.uitls.StringUtils;
import cn.spdb.harrier.rpc.common.InterfaceRpcCallBack;
import cn.spdb.harrier.rpc.common.ThreadPoolManager;

public class RpcFileLoadCallBack implements InterfaceRpcCallBack {

	@Override
	public void run(Object object) {
		if (!FileMessage.class.isInstance(object)) {
			return;
		}
		FileMessage fileMessage = (FileMessage) object;
		ThreadPoolManager.getInstance().addExecute(() -> deal(fileMessage));
	}

	public void deal(FileMessage fileMessage) {
		FileFuture fileFuture = FileManager.getInstance().getFileLoadFuture(fileMessage.getStreamId());
		if (fileFuture == null) {
			return;
		}

		if (fileMessage.getStatus() == FileStatus.SUCC_FILE.status()) {
			if (StringUtils.isBlank(fileFuture.getTmpPath())) {
				try (RandomAccessFile accessFile = new RandomAccessFile(fileFuture.getLocalPath(), "rw");) {
					accessFile.seek(fileFuture.getPos());
					accessFile.write(fileMessage.getData());
					fileFuture.setPos(accessFile.getFilePointer());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try (RandomAccessFile accessFile = new RandomAccessFile(fileFuture.getTmpPath(), "rw");) {
					accessFile.seek(fileFuture.getPos());
					accessFile.write(fileMessage.getData());
					fileFuture.setPos(accessFile.getFilePointer());
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					FileUtils.moveFile(new File(fileFuture.getTmpPath()), new File(fileFuture.getLocalPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (fileMessage.getStatus() == FileStatus.LOAD_FILE.status()) {
			if (StringUtils.isBlank(fileFuture.getTmpPath())) {
				fileFuture.setTmpPath(fileFuture.getLocalPath() + UUID.randomUUID().toString());
			}
			try (RandomAccessFile accessFile = new RandomAccessFile(fileFuture.getTmpPath(), "rw");) {
				accessFile.seek(fileFuture.getPos());
				accessFile.write(fileMessage.getData());
				fileFuture.setPos(accessFile.getFilePointer());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileFuture.getPos() != fileMessage.getPos()) {
			fileMessage.setStatus(FileStatus.FAIL_FILE.status());
		}
		fileFuture.done(fileMessage);
	}

}
