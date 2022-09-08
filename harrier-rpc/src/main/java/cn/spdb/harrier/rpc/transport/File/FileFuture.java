package cn.spdb.harrier.rpc.transport.File;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.ObjectUtils;

public class FileFuture implements Future<Object> {

	private CountDownLatch latch = new CountDownLatch(1);

	private FileMessage fileMessage;

	private IRpcFileService iRpcFileService;

	private long streamId;

	private String remotePath;

	private String localPath;

	private String tmpPath;

	private long pos;

	public FileFuture(long streamId) {
		super();
		this.streamId = streamId;
	}

	public FileFuture(IRpcFileService iRpcFileService, long streamId, String remotePath, String localPath) {
		super();
		this.streamId = streamId;
		this.remotePath = remotePath;
		this.localPath = localPath;
		this.iRpcFileService = iRpcFileService;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public FileMessage get() throws InterruptedException, ExecutionException {
		try {
			return get(5, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FileMessage get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		long tmpPos = pos;
		latch.await(timeout, TimeUnit.SECONDS);
		while (pos > tmpPos) {
			latch = new CountDownLatch(1);
			tmpPos = pos;
			latch.await(timeout, TimeUnit.SECONDS);
		}
		return fileMessage;
	}

	public void done(FileMessage fileMessage) {
		this.fileMessage = fileMessage;
		if (ObjectUtils.isEmpty(iRpcFileService)) {
			latch.countDown();
			FileManager.getInstance().removeLoad(streamId);
			return;
		}
		switch (FileStatus.getFileStatus(fileMessage.getStatus())) {
		case FAIL_FILE:
		case SUCC_FILE: {
			latch.countDown();
			FileManager.getInstance().removeLoad(streamId);
		}
			break;
		case UPLOAD_FILE:
			iRpcFileService.uploadFile(remotePath, streamId, fileMessage.getPos(), fileMessage.getData());
			break;
		case LOAD_FILE:
			iRpcFileService.loadFile(remotePath, streamId, pos, FileManager.LOAD_BUFFER_SIZE);
			break;
		default:
			break;
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

	public FileMessage getFileMessage() {
		return fileMessage;
	}

	public void setFileMessage(FileMessage fileMessage) {
		this.fileMessage = fileMessage;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String loadPath) {
		this.remotePath = loadPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public long getPos() {
		return pos;
	}

	public void setPos(long pos) {
		this.pos = pos;
	}

	public String getTmpPath() {
		return tmpPath;
	}

	public void setTmpPath(String tmpPath) {
		this.tmpPath = tmpPath;
	}

	public IRpcFileService getiRpcFileService() {
		return iRpcFileService;
	}

	public void setiRpcFileService(IRpcFileService iRpcFileService) {
		this.iRpcFileService = iRpcFileService;
	}

}
