package se.ugli.java.util.stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static se.ugli.java.util.stream.Collectors.toImmutableList;
import static se.ugli.java.util.stream.Collectors.toImmutableMap;
import static se.ugli.java.util.stream.Collectors.toImmutableSet;

import java.util.stream.Stream;

import org.junit.Test;

import se.ugli.java.util.ImmutableList;
import se.ugli.java.util.ImmutableMap;
import se.ugli.java.util.ImmutableSet;
import se.ugli.java.util.stream.Collectors;

public class ImmutableCollectorsTest {

    @Test
    public void shouldCollectImmutableList() {
        final ImmutableList<String> list = Stream.of("1", "2").collect(toImmutableList());
        assertThat(list.size(), is(2));
        assertThat(list.get(0), is("1"));
        assertThat(list.get(1), is("2"));
    }

    @Test
    public void shouldCollectImmutableSet() {
        final ImmutableSet<String> set = Stream.of("1", "2").collect(toImmutableSet());
        assertThat(set.size(), is(2));
        assertThat(set.contains("1"), is(true));
        assertThat(set.contains("2"), is(true));
        assertThat(set.contains("0"), is(false));
    }

    @Test
    public void shouldCollectImmutableMap() {
        final ImmutableMap<String, String> map = Stream.of("1", "2").collect(toImmutableMap(s -> s, s -> s + s));
        assertThat(map.size(), is(2));
        assertThat(map.get("1").get(), is("11"));
        assertThat(map.get("2").get(), is("22"));
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
        assertThat(collect.size(), is(3));
        assertThat(collect.get(NumberClass.ZERO).get().size(), is(2));
        assertThat(collect.get(NumberClass.NEG).get().size(), is(3));
        assertThat(collect.get(NumberClass.POS).get().size(), is(3));
    }

}
