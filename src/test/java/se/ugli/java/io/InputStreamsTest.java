package se.ugli.java.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class InputStreamsTest {

    @Test
    public void shouldCopy() {
        final String testdata = "DSF ASDF ADSF  asdfadf fdf d< f r545 wr5wr5w hsdfs 56wrfgsfdhshwrtgzg";
        final InputStream inputStream = new ByteArrayInputStream(testdata.getBytes());
        assertEquals(testdata, InputStreams.apply().copyToString(inputStream));
    }

    @Test
    public void shouldCopyLargeXmlFile() throws IOException {
        final byte[] bytes1 = IOUtils.toByteArray(Resource.apply("/pubmed-example.xml").asInputStream());
        final byte[] bytes2 = InputStreams.apply().copyToBytes(Resource.apply("/pubmed-example.xml").asInputStream());
        assertEquals(new String(bytes2), new String(bytes1));
    }

}
