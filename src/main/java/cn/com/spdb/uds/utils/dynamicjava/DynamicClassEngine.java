package cn.com.spdb.uds.utils.dynamicjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DynamicClassEngine {

	private static DynamicClassEngine instance;

	private URLClassLoader parentClassLoader;

	private String classPath;
	
	private static final Object KEY = new Object();
	
	public static DynamicClassEngine getInstance() {
		synchronized (KEY) {
		if (instance == null) {
			instance = new DynamicClassEngine();
		}
		return instance;
	}
	}

	private DynamicClassEngine() {
		this.parentClassLoader = (URLClassLoader) this.getClass()
				.getClassLoader();
		StringBuilder sBuilder = new StringBuilder();
		for (URL url : this.parentClassLoader.getURLs()) {
			String pString = url.getFile();
			if (pString.startsWith("/")) {
				pString = pString.substring(1);
			}
			sBuilder.append(pString).append(File.pathSeparator);
		}
		this.classPath = sBuilder.toString();
	}

	public Class<?> loadFromJavaFile(File file) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			inputStream.read(bytes);
			String javaCode = new String(bytes);
			String absolutePath = file.getPath();
			return javaCodeToObject(absolutePath, javaCode);
		} catch (IOException e) {
			throw e;
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
	}

	public Class<?> javaCodeToObject(String filePath, String javaCode) {
		long start = System.currentTimeMillis();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnosticsr = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager standardJavaFileManager = compiler
				.getStandardFileManager(diagnosticsr, null, null);
		ClassFileManager fileManager = new ClassFileManager(
				standardJavaFileManager);
		List<JavaFileObject> fileObjects = new ArrayList<JavaFileObject>();
		String strUri = "file:///" + filePath.replace("\\", "/");
		URI uri = URI.create(strUri);
		JavaFileObject javaFileObject = CharSequenceJavaFileObject.build(uri,
				javaCode);
		fileObjects.add(javaFileObject);

		List<String> options = new ArrayList<String>();
		options.add("-encoding");
		options.add("UTF-8");
		options.add("-classpath");
		options.add(this.classPath);

		CompilationTask task = compiler.getTask(null, fileManager,
				diagnosticsr, options, null, fileObjects);
		boolean success = task.call();
		DynamicClassLoader classLoader = new DynamicClassLoader(
				parentClassLoader);
		if (success) {
			JavaClassObject javaClassObject = fileManager
					.getMainJavaClassObject();
			List<JavaClassObject> innerClassObjects = fileManager
					.getInnerClassJavaClassObjects();
			if (innerClassObjects != null && innerClassObjects.size() > 0) {
				for (JavaClassObject inner : innerClassObjects) {
					classLoader.loadClass(inner);
				}
			}
			Class<?> clazz = classLoader.loadClass(javaClassObject);
			if (clazz != null) {
				long end = System.currentTimeMillis();
				System.out.println(filePath + "\t javaCodeToObject use :"
						+ (end - start) + " ms");
				return clazz;
			}
		} else {
			StringBuffer error = new StringBuffer();
			for (Diagnostic<?> diagnostic : diagnosticsr.getDiagnostics()) {
				error.append(compilePrint(diagnostic));
			}
			System.err.println("Exception in compile" + filePath + "\t "
					+ error);
		}
		return null;
	}

	private String compilePrint(Diagnostic<?> diagnostic) {
		StringBuffer res = new StringBuffer();
		res.append("Code:[" + diagnostic.getCode() + "]\n");
		res.append("Kind:[" + diagnostic.getKind() + "]\n");
		res.append("Position:[" + diagnostic.getPosition() + "]\n");
		res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
		res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
		res.append("Source:[" + diagnostic.getSource() + "]\n");
		res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
		res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
		res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");

		return res.toString();
	}

	public Class<?> loadClass(File file) throws IOException {
		DynamicClassLoader classLoader = new DynamicClassLoader(
				parentClassLoader);
		return classLoader.loadClass(file);
	}

}
