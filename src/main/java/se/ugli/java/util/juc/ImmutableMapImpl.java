package se.ugli.java.util.juc;

import static java.util.stream.Collectors.toSet;
import static se.ugli.java.util.stream.Collectors.toImmutableList;
import static se.ugli.java.util.stream.Collectors.toImmutableSet;

import java.util.Map;
import java.util.Optional;

import se.ugli.java.util.ImmutableCollection;
import se.ugli.java.util.ImmutableMap;
import se.ugli.java.util.ImmutableSet;
import se.ugli.java.util.MapEntry;

public class ImmutableMapImpl<K, V> extends ImmutableSetImpl<MapEntry<K, V>> implements ImmutableMap<K, V> {

    private static final long serialVersionUID = -6330686482444969304L;
    private final Map<K, V> map;

    public ImmutableMapImpl(final Map<K, V> map) {
        super(map.entrySet().stream().map(MapEntry::apply).collect(toSet()));
        this.map = map;
    }

    @Override
    public boolean containsKey(final K key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final V value) {
        return map.containsValue(value);
    }

    @Override
    public Optional<V> get(final K key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public ImmutableSet<K> keySet() {
        return map.keySet().stream().collect(toImmutableSet());
    }

    @Override
    public ImmutableCollection<V> values() {
        return map.values().stream().collect(toImmutableList());
    }

}