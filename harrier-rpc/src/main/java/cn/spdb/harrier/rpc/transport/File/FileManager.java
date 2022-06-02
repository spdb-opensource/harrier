package cn.spdb.harrier.rpc.transport.File;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.rpc.client.RpcClient;

public class FileManager {

	public static final int LOAD_BUFFER_SIZE = 1024 * 1024 * 5;
	public static final int UPLOAD_BUFFER_SIZE = 1024 * 1024 * 5;

	private static class FileMangerIner {
		private static FileManager instance = new FileManager();
	}

	public static FileManager getInstance() {
		return FileMangerIner.instance;
	}

	private AtomicLong atomicLong = new AtomicLong(1);

	private ConcurrentHashMap<Long, FileFuture> loadMap = new ConcurrentHashMap<Long, FileFuture>();

	public boolean loadFile(URI uri, String loadPath, String localPath, boolean isAsyn) {
		try {
			File file = new File(localPath);
			File parFile = file.getParentFile();
			if (!parFile.exists()) {
				parFile.createNewFile();
			}
			long streamId = atomicLong.incrementAndGet();
			IRpcFileService iRpcFileService = RpcClient.getInstance().create(IRpcFileService.class, uri);
			FileFuture fileFuture = new FileFuture(iRpcFileService, streamId, loadPath, localPath);
			fileFuture.setPos(0L);
			loadMap.put(streamId, fileFuture);
			iRpcFileService.loadFile(loadPath, streamId, 0L, LOAD_BUFFER_SIZE);
			if (isAsyn) {
				return true;
			}
			FileMessage fileMessage = fileFuture.get();
			return fileMessage.getStatus() == FileStatus.SUCC_FILE.status();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | IOException | InterruptedException
				| ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upLoad(URI uri, String remotePath, String localPath, boolean isAsyn) {
		File file = new File(localPath);
		if (!file.exists()) {
			return false;
		}
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");) {
			long length = accessFile.length();
			length = length > UPLOAD_BUFFER_SIZE ? UPLOAD_BUFFER_SIZE : length;
			byte[] data = new byte[(int) length];
			accessFile.seek(0);
			accessFile.read(data);
			long streamId = atomicLong.incrementAndGet();
			IRpcFileService iRpcFileService = RpcClient.getInstance().create(IRpcFileService.class, uri);
			FileFuture fileUploadFuture = new FileFuture(iRpcFileService, streamId, remotePath, localPath);
			fileUploadFuture.setPos(accessFile.getFilePointer());
			loadMap.put(streamId, fileUploadFuture);
			iRpcFileService.uploadFile(remotePath, streamId, 0L, data);
			if (isAsyn) {
				return true;
			}
			FileMessage fileUploadResp = fileUploadFuture.get();
			return fileUploadResp.getStatus() == 0;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | IOException | InterruptedException
				| ExecutionException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteFile(URI uri, String remotePath) {
		Boolean bool = null;
		try {
			IRpcFileService iRpcFileService = RpcClient.getInstance().create(IRpcFileService.class, uri);
			bool = iRpcFileService.deleteFile(remotePath);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return (bool == null ? false : bool);
	}

	public boolean createDir(URI uri, String remotePath) {
		Boolean bool = null;
		try {
			IRpcFileService iRpcFileService = RpcClient.getInstance().create(IRpcFileService.class, uri);
			bool = iRpcFileService.createDirFile(remotePath);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return (bool == null ? false : bool);
	}

	public List<FileAtt> selectFile(URI uri, String remotePath) {
		List<FileAtt> list = new ArrayList<FileAtt>();
		try {
			IRpcFileService iRpcFileService = RpcClient.getInstance().create(IRpcFileService.class, uri);
			list = iRpcFileService.selectFiles(remotePath);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ConcurrentHashMap<Long, FileFuture> getLoadMap() {
		return loadMap;
	}

	public void setLoadMap(ConcurrentHashMap<Long, FileFuture> has) {
		this.loadMap = has;
	}

	public FileFuture getFileLoadFuture(Long streamId) {
		return loadMap.get(streamId);
	}

	public FileFuture removeLoad(Long streamId) {
		return loadMap.remove(streamId);
	}

}
