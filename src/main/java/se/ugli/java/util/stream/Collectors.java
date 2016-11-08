package se.ugli.java.util.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
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
        return toImmutableList(ArrayList::new, ImmutableListImpl::new);
    }

    public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet() {
        return toImmutableSet(HashSet::new, ImmutableSetImpl::new);
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
            final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends V> valueMapper) {
        return toImmutableMap(keyMapper, valueMapper, (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        }, HashMap::new, ImmutableMapImpl::new);
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
            final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends V> valueMapper,
            final BinaryOperator<V> mergeFunction) {
        return toImmutableMap(keyMapper, valueMapper, mergeFunction, HashMap::new, ImmutableMapImpl::new);
    }

    public static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
            final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends V> valueMapper,
            final BinaryOperator<V> mergeFunction, final Supplier<Map<K, V>> mutableMapFactory,
            final Function<Map<K, V>, ImmutableMap<K, V>> immutableMapFactory) {
        return Collector.of(mutableMapFactory,
                (map, element) -> map.merge(keyMapper.apply(element), valueMapper.apply(element), mergeFunction),
                (m1, m2) -> {
                    m2.entrySet().forEach(e -> m1.merge(e.getKey(), e.getValue(), mergeFunction));
                    return m1;
                }, immutableMapFactory);
    }

    public static <T, K> Collector<T, ?, ImmutableMap<K, ImmutableList<T>>> groupingBy(
            final Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, ArrayList::new, ImmutableListImpl::new, HashMap::new, HashMap::new,
                ImmutableMapImpl::new);
    }

    public static <T, K> Collector<T, ?, ImmutableMap<K, ImmutableList<T>>> groupingBy(
            final Function<? super T, ? extends K> classifier, final Supplier<List<T>> mutableListFactory,
            final Function<List<T>, ImmutableList<T>> immutableListFactory,
            final Supplier<Map<K, List<T>>> mutableContainerFactory,
            final Supplier<Map<K, ImmutableList<T>>> mutableMapFactory,
            final Function<Map<K, ImmutableList<T>>, ImmutableMap<K, ImmutableList<T>>> immutableMapFactory) {
        return Collector.of(mutableContainerFactory, (m, t) -> {
            m.computeIfAbsent(classifier.apply(t), k -> mutableListFactory.get()).add(t);
        }, (m1, m2) -> {
            m2.entrySet().forEach(e -> {
                m1.computeIfAbsent(e.getKey(), k -> mutableListFactory.get()).addAll(e.getValue());
            });
            return m1;
        }, map -> {
            final Map<K, ImmutableList<T>> newMap = mutableMapFactory.get();
            map.entrySet().forEach(e -> newMap.put(e.getKey(), immutableListFactory.apply(e.getValue())));
            return immutableMapFactory.apply(newMap);
        });
    }

}
