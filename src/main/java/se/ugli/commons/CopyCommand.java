package se.ugli.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class CopyCommand {

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	public static CopyCommand apply() {
		return new CopyCommand(DEFAULT_BUFFER_SIZE);
	}

	public static CopyCommand apply(final int bufferSize) {
		return new CopyCommand(bufferSize);
	}

	private final int bufferSize;

	private CopyCommand(final int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public void copy(final ReadableByteChannel in, final WritableByteChannel out) {
		try {
			final ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			while (in.read(buffer) > 0)
				out.write((ByteBuffer) buffer.flip());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void copy(final InputStream in, final OutputStream out) {
		ReadableByteChannel inChannel = null;
		WritableByteChannel outChannel = null;
		try {
			inChannel = Channels.newChannel(new BufferedInputStream(in, bufferSize));
			outChannel = Channels.newChannel(new BufferedOutputStream(out, bufferSize));
			copy(inChannel, outChannel);
		} finally {
			CloseCommand.execute(inChannel, outChannel);
		}
	}

	public void copy(final Reader in, final Charset charset, final OutputStream out) {
		copy(toInputStream(in, charset), out);
	}

	public void copy(final Reader in, final OutputStream out) {
		copy(in, Charset.defaultCharset(), out);
	}

	public byte[] copyToBytes(final InputStream in) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			copy(in, out);
			return out.toByteArray();
		} finally {
			CloseCommand.execute(out);
		}
	}

	public byte[] copyToBytes(final Reader in) {
		return copyToBytes(in, Charset.defaultCharset());
	}

	public byte[] copyToBytes(final Reader in, final Charset charset) {
		return copyToBytes(toInputStream(in, charset));
	}

	public String copyToString(final InputStream in) {
		return copyToString(in, Charset.defaultCharset());
	}

	public String copyToString(final InputStream in, final Charset charset) {
		return new String(copyToBytes(in), charset);
	}

	public String copyToString(final Reader in) {
		return copyToString(in, Charset.defaultCharset(), Charset.defaultCharset());
	}

	public String copyToString(final Reader in, final Charset inCharset, final Charset outCharset) {
		return copyToString(toInputStream(in, inCharset), outCharset);
	}

	private InputStream toInputStream(final Reader reader, final Charset charset) {
		try {
			final char[] charBuffer = new char[bufferSize];
			final StringBuilder builder = new StringBuilder();
			int numCharsRead;
			while ((numCharsRead = reader.read(charBuffer, 0, charBuffer.length)) != -1)
				builder.append(charBuffer, 0, numCharsRead);
			return new ByteArrayInputStream(builder.toString().getBytes(charset));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
