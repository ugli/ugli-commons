package se.ugli.commons;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class CopyCommandTest {

	@Test
	public void shouldCopy() {
		final String testdata = "DSF ASDF ADSF  asdfadf fdf d< f r545 wr5wr5w hsdfs 56wrfgsfdhshwrtgzg";
		final InputStream inputStream = new ByteArrayInputStream(testdata.getBytes());
		assertThat(testdata, equalTo(CopyCommand.apply().copyToString(inputStream)));
	}

}
