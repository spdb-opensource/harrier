package cn.spdb.harrier.common.dynamicjava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaClassObject extends SimpleJavaFileObject {

	protected final ByteArrayOutputStream bosArrayOutputStream = new ByteArrayOutputStream();

	public static JavaClassObject buildJavaClassObject(String name, Kind kind) {
		URI uri = URI.create("file:///" + name.replace(".", "/") + kind.extension);
		return new JavaClassObject(uri, kind);
	}
	
	protected JavaClassObject(URI uri, Kind kind) {
		super(uri, kind);
	}

	public byte[] getBytes() {
		return bosArrayOutputStream.toByteArray();
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		return bosArrayOutputStream;
	}
}
