package cn.com.spdb.uds.log;

import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemOutAndErrPrintStream extends PrintStream {

	private Logger logger = LoggerFactory.getLogger(SystemOutAndErrPrintStream.class);
	/*
	Rename variable
	 */
	private boolean isError = false;


	public SystemOutAndErrPrintStream(OutputStream out, boolean isErr) {
		super(out, false);
		if (isErr) {
			System.setErr(this);
		} else {
			System.setOut(this);
		}
		this.isError = isErr;
	}

	@Override
	public void print(String string) {
		if (isError) {
			logger.error(string);
		} else {
			logger.info(string);
		}
	}

	@Override
	public void print(boolean b) {
		if (isError) {
			logger.error(String.valueOf(b));
		} else {
			logger.info(String.valueOf(b));
		}
	}

	@Override
	public void print(char c) {
		if (isError) {
			logger.error(String.valueOf(c));
		} else {
			logger.info(String.valueOf(c));
		}
	}

	@Override
	public void print(int i) {
		if (isError) {
			logger.error(String.valueOf(i));
		} else {
			logger.info(String.valueOf(i));
		}
	}

	@Override
	public void print(long l) {
		if (isError) {
			logger.error(String.valueOf(l));
		} else {
			logger.info(String.valueOf(l));
		}
	}

	@Override
	public void print(float f) {
		if (isError) {
			logger.error(String.valueOf(f));
		} else {
			logger.info(String.valueOf(f));
		}
	}

	@Override
	public void print(double d) {
		if (isError) {
			logger.error(String.valueOf(d));
		} else {
			logger.info(String.valueOf(d));
		}
	}

	@Override
	public void print(char[] x) {
		if (isError) {
			logger.error(String.valueOf(x));
		} else {
			logger.info(String.valueOf(x));
		}
	}

	@Override
	public void print(Object obj) {
		if (isError) {
			logger.error(String.valueOf(obj));
		} else {
			logger.info(String.valueOf(obj));
		}
	}
}
