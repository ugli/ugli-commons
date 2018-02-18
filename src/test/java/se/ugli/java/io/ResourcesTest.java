package se.ugli.java.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResourcesTest {

    @Test
    public void shouldFindClass() {
        assertEquals(true, Resources.classExists("se.ugli.java.util.Id"));
    }

    @Test
    public void shouldNotFindClass() {
        assertEquals(false, Resources.classExists("se.ugli.commons.Id"));
    }
}
