package cn.spdb.harrier.common.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

import cn.spdb.harrier.common.dynamicjava.DynamicClassManager;

public class DynamicTest {

	@Test
	public void loadJava() throws NoSuchMethodException {

		try {
			try {
				String pathname = "d:/test/HelloWorld.java";
				File file = new File(pathname);
				Class<?> clazz = DynamicClassManager.getInstance().loadFromJavaFile(file);
				Object object = clazz.newInstance();
				Method[] mlist = clazz.getMethods();
				Arrays.stream(mlist).forEach(action -> {
					if (action.getParameterCount() == 0 && action.getName().equals("getName"))
						try {
							System.out.println(action.invoke(object));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					
				});
			} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
		System.out.println(this.getClass().getName());
	}

	@Test
	public void loadClass() {
		System.out.println("\0dd");
	}

}
