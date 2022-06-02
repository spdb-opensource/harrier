package cn.spdb.harrier.common.dynamicjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

	private List<JavaClassObject> javaClassObjectList;

	protected ClassFileManager(StandardJavaFileManager fileManager) {
		super(fileManager);
		this.javaClassObjectList = new ArrayList<JavaClassObject>();
	}

	public JavaClassObject getMainJavaClassObject() {
		if (null != this.javaClassObjectList && this.javaClassObjectList.size() > 0) {
			int size = this.javaClassObjectList.size();
			return this.javaClassObjectList.get(size - 1);
		}
		return null;
	}

	public List<JavaClassObject> getInnerClassJavaClassObjects() {
		if (null != this.javaClassObjectList && this.javaClassObjectList.size() > 0) {
			int size = this.javaClassObjectList.size();
			if (1 == size) {
				return null;
			}
			return this.javaClassObjectList.subList(0, size - 1);
		}
		return null;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
			throws IOException {
		JavaClassObject javaClassObject = JavaClassObject.buildJavaClassObject(className, kind);
		this.javaClassObjectList.add(javaClassObject);
		return javaClassObject;
	}

}
