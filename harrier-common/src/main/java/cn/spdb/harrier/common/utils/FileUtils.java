package cn.spdb.harrier.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils extends org.apache.commons.io.FileUtils {

	public static Boolean isExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static Boolean isFile(String path) {
		File file = new File(path);
		return file.isFile();
	}

	public static Boolean isFileAndExists(String path) {
		File file = new File(path);
		return file.exists() && file.isFile();
	}
	
	public static Boolean createDirectory(String path) {
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
	
}
