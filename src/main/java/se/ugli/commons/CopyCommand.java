package se.ugli.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class CopyCommand {

	private final int bufferSize;

	private CopyCommand(final int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public static CopyCommand apply(final int bufferSize) {
		return new CopyCommand(bufferSize);
	}

	public static CopyCommand apply() {
		return new CopyCommand(1024);
	}

	public void copy(final ReadableByteChannel readableByteChannel, final WritableByteChannel writableByteChannel) {
		try {
			final ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
			while (readableByteChannel.read(byteBuffer) > 0)
				writableByteChannel.write((ByteBuffer) byteBuffer.flip());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void copy(final InputStream inputStream, final OutputStream outputStream) {
		ReadableByteChannel readableByteChannel = null;
		WritableByteChannel writableByteChannel = null;
		try {
			readableByteChannel = Channels.newChannel(new BufferedInputStream(inputStream));
			writableByteChannel = Channels.newChannel(new BufferedOutputStream(outputStream));
			copy(readableByteChannel, writableByteChannel);
		} finally {
			CloseCommand.execute(readableByteChannel, writableByteChannel);
		}
	}

	public byte[] copyToBytes(final InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			copy(inputStream, byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		} finally {
			CloseCommand.execute(byteArrayOutputStream);
		}
	}

	public String copyToString(final InputStream inputStream, final String charsetName) {
		return copyToString(inputStream, Charset.forName(charsetName));
	}

	public String copyToString(final InputStream inputStream, final Charset charset) {
		return new String(copyToBytes(inputStream), charset);
	}

	public String copyToString(final InputStream inputStream) {
		return copyToString(inputStream, Charset.defaultCharset());
	}

}
