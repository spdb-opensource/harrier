package cn.spdb.harrier.api.controller.system;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.system.FileDisposeService;
import cn.spdb.harrier.common.Constants;
import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.rpc.transport.File.FileAtt;
import cn.spdb.harrier.rpc.transport.File.FileMessage;

@RestController
@RequestMapping("/file")
public class SysFileController {

	@Autowired
	private FileDisposeService fileDisposeService;

	@AccessLogAnnotation(isDbInstall = true, ignoreResponse = false)
	@RequestMapping(path = "deleteFile", method = RequestMethod.GET)
	public Boolean deleteFile(String uri) {
		return fileDisposeService.deleteFile(URI.valueOf(uri));
	}

	@AccessLogAnnotation(isDbInstall = true, ignoreResponse = false)
	@RequestMapping(path = "createDir", method = RequestMethod.GET)
	public Boolean createDir(String uri) {
		return fileDisposeService.createDir(URI.valueOf(uri));
	}

	@RequestMapping(path = "selectFile", method = RequestMethod.GET)
	public List<FileAtt> selectFile(String uri, String fileName, String order) {
		return fileDisposeService.selectFile(URI.valueOf(uri), fileName, order);
	}

	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "request", "response" })
	@RequestMapping(path = "loadFile", method = RequestMethod.GET)
	public void loadFile(HttpServletRequest request, HttpServletResponse response, String uri, Long streamId, Long pos,
			Long size, String filename) {

		streamId = new AtomicLong(1).incrementAndGet();
		pos = 0L;
		size = Constants.LOAD_BUFFER_SIZE;
		FileMessage fileMessage = fileDisposeService.loadFile(URI.valueOf(uri), streamId, pos, size);

		try {
			response.reset();
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try (OutputStream outputStream4Client = new BufferedOutputStream(response.getOutputStream())) {
			outputStream4Client.write(fileMessage.getData());
			outputStream4Client.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AccessLogAnnotation(isDbInstall = true, ignoreRequestArgs = { "files" })
	@RequestMapping(path = "uploadFile", method = RequestMethod.POST)
	public FileMessage uploadFile(String uri, String path, Long streamId, Long pos,
			@RequestParam("file") MultipartFile[] files) {
		streamId = new AtomicLong(1).incrementAndGet();
		pos = 0L;
		byte[] data = null;
		for (MultipartFile file : files) {
			try {
				data = file.getBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileDisposeService.uploadFile(URI.valueOf(uri), streamId, pos, data);
	}

}
