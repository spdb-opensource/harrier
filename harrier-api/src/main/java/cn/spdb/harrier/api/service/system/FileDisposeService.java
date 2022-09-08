package cn.spdb.harrier.api.service.system;

import java.util.List;

import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.rpc.transport.File.FileAtt;
import cn.spdb.harrier.rpc.transport.File.FileMessage;

public interface FileDisposeService {

	Boolean deleteFile(URI uri);

	boolean createDir(URI uri);

	List<FileAtt> selectFile(URI uri,String fileName,String order);

	FileMessage loadFile(URI uri, Long streamId, Long pos, Long size);

	FileMessage uploadFile(URI uri, Long streamId, Long pos, byte[] data);

}