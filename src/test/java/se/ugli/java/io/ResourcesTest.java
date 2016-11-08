package se.ugli.java.io;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ResourcesTest {

    @Test
    public void shouldFindClass() {
        assertThat(true, equalTo(Resources.classExists("se.ugli.java.util.Id")));
    }

    @Test
    public void shouldNotFindClass() {
        assertThat(false, equalTo(Resources.classExists("se.ugli.commons.Id")));
    }
}
