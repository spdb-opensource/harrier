package cn.spdb.harrier.common.dynamicjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassLoader extends URLClassLoader {

	public DynamicClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}

	public Class<?> loadClass(String fullName, JavaClassObject javaClassObject) {
		byte[] classData = javaClassObject.getBytes();
		Class<?> clazz = this.defineClass(fullName, classData, 0, classData.length);
		return clazz;
	}

	public Class<?> loadClass(JavaClassObject javaClassObject) {
		byte[] classData = javaClassObject.getBytes();
		Class<?> clazz = this.defineClass(null, classData, 0, classData.length);
		return clazz;
	}

	/**
	 * 加载.class文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Class<?> loadClass(File file) throws IOException {
		byte[] bytes = new byte[(int) file.length()];
		Class<?> clazz = null;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			int j = 0;
			while (true) {
				int i = inputStream.read(bytes);
				if (i == -1) {
					break;
				}
				j += i;
			}
			clazz = super.defineClass(null, bytes, 0, j);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return clazz;
	}
}
