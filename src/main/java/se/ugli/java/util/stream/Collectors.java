package se.ugli.java.util.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import se.ugli.java.util.ImmutableList;
import se.ugli.java.util.ImmutableMap;
import se.ugli.java.util.ImmutableSet;
import se.ugli.java.util.juc.ImmutableListImpl;
import se.ugli.java.util.juc.ImmutableMapImpl;
import se.ugli.java.util.juc.ImmutableSetImpl;

public class Collectors {

    private Collectors() {
    }

    public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet(final Supplier<Set<T>> mutableSetFactory,
            final Function<Set<T>, ImmutableSet<T>> immutableSetFactory) {
        return Collector.of(mutableSetFactory, (list, e) -> list.add(e), (left, right) -> {
            left.addAll(right);
            return left;
        }, immutableSetFactory);
    }

    public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList(final Supplier<List<T>> mutableListFactory,
            final Function<List<T>, ImmutableList<T>> immutableListFactory) {
        return Collector.of(mutableListFactory, (list, e) -> list.add(e), (left, right) -> {
            left.addAll(right);
            return left;
        }, immutableListFactory);
    }

    public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList() {
        return toImmutableList(ArrayList::new, list -> new ImmutableListImpl<>(list));
    }

    public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet() {
        return toImmutableSet(HashSet::new, set -> new ImmutableSetImpl<>(set));
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
            final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends V> valueMapper) {
        return toImmutableMap(keyMapper, valueMapper, HashMap::new, map -> new ImmutableMapImpl<>(map));
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
            final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends V> valueMapper,
            final Supplier<Map<K, V>> mutableMapFactory,
            final Function<Map<K, V>, ImmutableMap<K, V>> immutableMapFactory) {
        return Collector.of(mutableMapFactory,
                (map, element) -> map.put(keyMapper.apply(element), valueMapper.apply(element)), (m1, m2) -> {
                    m1.putAll(m2);
                    return m1;
                }, immutableMapFactory);
    }

    public static <T, K> Collector<T, ?, ImmutableMap<K, ImmutableList<T>>> groupingBy(
            final Function<? super T, ? extends K> classifier) {
        return Collector.of((Supplier<Map<K, List<T>>>) HashMap::new, (m, t) -> {
            m.computeIfAbsent(classifier.apply(t), k -> new ArrayList<>()).add(t);
        }, (m1, m2) -> {
            m2.entrySet().forEach(e -> {
                m1.computeIfAbsent(e.getKey(), k -> new ArrayList<>()).addAll(e.getValue());
            });
            return m1;
        }, map -> {
            final Map<K, ImmutableList<T>> newMap = new HashMap<>();
            map.entrySet().forEach(e -> newMap.put(e.getKey(), new ImmutableListImpl<>(e.getValue())));
            return new ImmutableMapImpl<>(newMap);
        });
    }

}
