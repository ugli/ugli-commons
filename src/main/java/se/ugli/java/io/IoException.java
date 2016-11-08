package se.ugli.java.io;

import java.io.IOException;

public class IoException extends RuntimeException {

	private static final long serialVersionUID = 1450574720843973525L;

	public IoException(final IOException e) {
		super(e);
	}

	public IoException(final String msg) {
		super(msg);
	}

}
