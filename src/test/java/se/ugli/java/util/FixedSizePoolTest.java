package se.ugli.java.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FixedSizePoolTest {

    @Test
    public void shouldPoolStuff() {
        try (Pool<Stuff> pool = Pool.builder(() -> new Stuff()).build()) {
            final List<Integer> list = new ArrayList<>();
            try (Stuff stuff = pool.borrow()) {
                assertThat(stuff.getInt(), is(5));
                stuff.add(list);
                assertThat(list.size(), is(1));
            }
        }
    }

    static class Stuff implements AutoCloseable {

        @Override
        public void close() {
        }

        public int getInt() {
            return 5;
        }

        public void add(final List<Integer> list) {
            list.add(getInt());
        }

    }

}
