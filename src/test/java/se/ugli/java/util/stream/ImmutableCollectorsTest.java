package se.ugli.java.util.stream;

import static org.junit.Assert.assertEquals;
import static se.ugli.java.util.stream.Collectors.toImmutableList;
import static se.ugli.java.util.stream.Collectors.toImmutableMap;
import static se.ugli.java.util.stream.Collectors.toImmutableSet;

import java.util.stream.Stream;

import org.junit.Test;

import se.ugli.java.util.ImmutableList;
import se.ugli.java.util.ImmutableMap;
import se.ugli.java.util.ImmutableSet;

public class ImmutableCollectorsTest {

    @Test
    public void shouldCollectImmutableList() {
        final ImmutableList<String> list = Stream.of("1", "2").collect(toImmutableList());
        assertEquals(list.size(), 2);
        assertEquals(list.get(0), "1");
        assertEquals(list.get(1), "2");
    }

    @Test
    public void shouldCollectImmutableSet() {
        final ImmutableSet<String> set = Stream.of("1", "2").collect(toImmutableSet());
        assertEquals(set.size(), 2);
        assertEquals(set.contains("1"), true);
        assertEquals(set.contains("2"), true);
        assertEquals(set.contains("0"), false);
    }

    @Test
    public void shouldCollectImmutableMap() {
        final ImmutableMap<String, String> map = Stream.of("1", "2").collect(toImmutableMap(s -> s, s -> s + s));
        assertEquals(map.size(), 2);
        assertEquals(map.get("1").get(), "11");
        assertEquals(map.get("2").get(), "22");
    }

    enum NumberClass {
        NEG, ZERO, POS
    }

    @Test
    public void shouldCollectGroupBy() {
        final ImmutableMap<NumberClass, ImmutableList<Integer>> collect = Stream.of(-1, 0, 1, 2, 3, 0, -2, -4)
                .collect(Collectors.groupingBy(i -> {
                    if (i == 0)
                        return NumberClass.ZERO;
                    else if (i > 0)
                        return NumberClass.POS;
                    return NumberClass.NEG;
                }));
        assertEquals(collect.size(), 3);
        assertEquals(collect.get(NumberClass.ZERO).get().size(), 2);
        assertEquals(collect.get(NumberClass.NEG).get().size(), 3);
        assertEquals(collect.get(NumberClass.POS).get().size(), 3);
    }

}
