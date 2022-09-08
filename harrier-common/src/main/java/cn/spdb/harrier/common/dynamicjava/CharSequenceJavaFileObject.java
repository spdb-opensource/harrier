package cn.spdb.harrier.common.dynamicjava;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

	private CharSequence content;
	
	public static CharSequenceJavaFileObject build(URI uri ,CharSequence  content){
		CharSequenceJavaFileObject object=new CharSequenceJavaFileObject(uri, Kind.SOURCE);
		object.setContent(content);
		return object;
	}
	
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		return content;
	}
	
	protected CharSequenceJavaFileObject(URI uri, Kind kind) {
		super(uri, kind);
	}

	public CharSequence getContent() {
		return content;
	}

	public void setContent(CharSequence content) {
		this.content = content;
	}

}
