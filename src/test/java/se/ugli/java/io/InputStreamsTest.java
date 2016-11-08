package se.ugli.java.io;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import se.ugli.java.io.InputStreams;
import se.ugli.java.io.Resource;

public class InputStreamsTest {

	@Test
	public void shouldCopy() {
		final String testdata = "DSF ASDF ADSF  asdfadf fdf d< f r545 wr5wr5w hsdfs 56wrfgsfdhshwrtgzg";
		final InputStream inputStream = new ByteArrayInputStream(testdata.getBytes());
		assertThat(testdata, equalTo(InputStreams.apply().copyToString(inputStream)));
	}

	@Test
	public void shouldCopyLargeXmlFile() throws IOException {
		final byte[] bytes1 = IOUtils.toByteArray(Resource.apply("/pubmed-example.xml").asInputStream());
		final byte[] bytes2 = InputStreams.apply().copyToBytes(Resource.apply("/pubmed-example.xml").asInputStream());
		assertThat(new String(bytes2), equalTo(new String(bytes1)));
	}

}
