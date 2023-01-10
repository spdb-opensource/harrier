package cn.spdb.harrier.common.log;

import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackPrintStream extends PrintStream {

	private Logger logger = LoggerFactory.getLogger("logbackPrint");

	public LogbackPrintStream(OutputStream out) {
		super(out);
		System.setErr(this);
	}

	@Override
	public void print(String string) {

		logger.error(string);

	}

	@Override
	public void print(boolean b) {

		print(b);

	}

	@Override
	public void print(char c) {

		print(c);

	}

	@Override
	public void print(int i) {

		print(i);

	}

	@Override
	public void print(long l) {
		print(l);
	}

	@Override
	public void print(float f) {
		print(f);
	}

	@Override
	public void print(double d) {
		print(d);
	}

	@Override
	public void print(char[] x) {
		print(x);
	}

	@Override
	public void print(Object obj) {
		logger.error(String.valueOf(obj));
	}
}