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

	// @Test
	// public void abc() throws Exception {
	// final File inFile = new File(
	// "/Users/frekiv/Dropbox (Ugli)/DAW/projects/opinionen/Vi kommer aldrig fram till vår strand.wav");
	// final File outFile = new File(
	// "/Users/frekiv/Dropbox (Ugli)/DAW/projects/opinionen/Vi kommer aldrig fram till vår strand.wav.copy");
	// if (true) {
	//
	// CopyCommand.apply().copy(new FileInputStream(inFile), new
	// FileOutputStream(outFile));
	// final long nanoTime = System.nanoTime();
	// CopyCommand.apply().copy(new FileInputStream(inFile), new
	// FileOutputStream(outFile));
	// System.out.println(System.nanoTime() - nanoTime);
	// }
	// if (true) {
	// IOUtils.copy(new FileInputStream(inFile), new FileOutputStream(outFile));
	// final long nanoTime = System.nanoTime();
	// IOUtils.copy(new FileInputStream(inFile), new FileOutputStream(outFile));
	// System.out.println(System.nanoTime() - nanoTime);
	//
	// }
	// {
	// Runtime.getRuntime().exec(new String[] { "cp", inFile.getAbsolutePath(),
	// outFile.getAbsolutePath() });
	// final long nanoTime = System.nanoTime();
	// Runtime.getRuntime().exec(new String[] { "cp", inFile.getAbsolutePath(),
	// outFile.getAbsolutePath() });
	// System.out.println(System.nanoTime() - nanoTime);
	//
	// }
	// }

}
