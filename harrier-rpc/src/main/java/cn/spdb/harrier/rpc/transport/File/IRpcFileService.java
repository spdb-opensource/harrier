package cn.spdb.harrier.rpc.transport.File;

import java.util.List;

import cn.spdb.harrier.rpc.common.RpcHandler;
import cn.spdb.harrier.rpc.common.RpcMethod;
import cn.spdb.harrier.rpc.compress.CompressEnum;

@RpcHandler("IRpcFileService")
public interface IRpcFileService {

	@RpcMethod(compressType = CompressEnum.GIZP, async = true, serviceCallBack = RpcFileLoadCallBack.class)
	public FileMessage loadFile(String loadPath, Long streamId, Long pos, Integer bufferSize);

	@RpcMethod(compressType = CompressEnum.GIZP, async = true, serviceCallBack = RpcFileUploadCallBack.class)
	public FileMessage uploadFile(String remotePath, Long streamId, Long pos, byte[] data);

	@RpcMethod(timeOut = 1000 * 5)
	public Boolean deleteFile(String string);

	@RpcMethod(timeOut = 1000 * 5)
	public Boolean createDirFile(String string);

	@RpcMethod(timeOut = 1000 * 5)
	public List<FileAtt> selectFiles(String path);

}