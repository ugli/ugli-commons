package se.ugli.java.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BlockingQueueObjectPoolTest {

    @Test
    public void testStuff() {
        try (ObjectPool<StuffImpl> pool = ObjectPool.builder(() -> new StuffImpl()).build()) {
            final List<Integer> list = new ArrayList<>();
            try (Stuff stuff = pool.borrow()) {
                assertThat(stuff.getInt(), is(5));
                stuff.add(list);
                assertThat(list.size(), is(1));
            }
        }
    }

    interface Stuff extends AutoCloseable {

        @Override
        void close();

        int getInt();

        void add(final List<Integer> list);

    }

    class StuffImpl implements Stuff {

        @Override
        public void close() {
        }

        @Override
        public int getInt() {
            return 5;
        }

        @Override
        public void add(final List<Integer> list) {
            list.add(getInt());
        }

    }

}
