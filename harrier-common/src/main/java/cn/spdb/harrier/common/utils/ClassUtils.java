package cn.spdb.harrier.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ClassUtils {

	private static final String DOT = ".";
	private static final String ZIP_SLASH = "/";
	private static final String CLASS_EXT = ".class";
	private static final String JAR_FILE_EXT = ".jar";
	private static final CharSequence BLACK = "";

	/**
	 * 获取所有class
	 * 
	 * @return
	 */
	private static String[] getClassPathArray() {
		return System.getProperty("java.class.path").concat(System.getProperty("path.separator"))
				.concat(System.getProperty("java.home")).split(System.getProperty("path.separator"));
	}

	public static List<Class<?>> scanPackage(Package packagea, ClassFilter classFilter) {
		return scanPackage(packagea.getName(), classFilter);
	}

	/**
	 * @param packageName
	 * @param classFilter null 默认为符合
	 * @return
	 */
	public static List<Class<?>> scanPackage(String packageName, ClassFilter classFilter) {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!StringUtils.isBlank(packageName)) {
			packageName = packageName.lastIndexOf(DOT) != packageName.length() - 1 ? packageName + DOT : packageName;
		}

		String[] classPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
		for (String classPath : classPaths) {
			fillClasses(new File(classPath), packageName, classFilter, classes);
		}
		return classes;
	}

	private static void fillClasses(File file, String packageName, ClassFilter classFilter, List<Class<?>> classes) {
		if (file.isDirectory()) {
			processDirectory(file, packageName, classFilter, classes);
		} else if (file.getName().endsWith(CLASS_EXT)) {
			processClassFile(file, packageName, classFilter, classes);
		} else if (file.getName().endsWith(JAR_FILE_EXT)) {
			processJarFile(file, packageName, classFilter, classes);
		}
	}

	private static void processJarFile(File file, String packageName, ClassFilter classFilter, List<Class<?>> classes) {

		try {
			for (ZipEntry entry : Collections.list(new ZipFile(file).entries())) {
				if (entry.getName().endsWith(CLASS_EXT)) {
					final String className = entry.getName().replace(ZIP_SLASH, DOT).replace(CLASS_EXT, BLACK);
					fillClass(className, packageName, classFilter, classes);
				}
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void processClassFile(File file, String packageName, ClassFilter classFilter,
			List<Class<?>> classes) {
		final String filePathWithDot = file.getAbsolutePath().replace(File.separator, DOT);
		int subIndex = -1;
		if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
			final String calssName = filePathWithDot.substring(subIndex).replace(CLASS_EXT, BLACK);
			fillClass(calssName, packageName, classFilter, classes);
		}
	}

	private static void fillClass(String calssName, String packageName, ClassFilter classFilter,
			List<Class<?>> classes) {
		if (calssName.indexOf(packageName) == 0) {
			try {
				final Class<?> clazz = Class.forName(calssName, false, ClassUtils.class.getClassLoader());
				if (checkClassFilter(classFilter, clazz)) {
					classes.add(clazz);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	private static boolean checkClassFilter(ClassFilter classFilter, Class<?> clazz) {
		return classFilter == null || classFilter.actionFilter(clazz);
	}

	/**
	 * 文件夹处理
	 * 
	 * @param dir
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 */
	private static void processDirectory(File dir, String packageName, ClassFilter classFilter,
			List<Class<?>> classes) {
		for (File file : dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith(CLASS_EXT)
						|| pathname.getName().endsWith(CLASS_EXT);
			}
		})) {
			fillClasses(file, packageName, classFilter, classes);
		}
	}

	public interface ClassFilter {
		public boolean actionFilter(Class<?> clazz);
	}

}
