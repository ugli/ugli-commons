package se.ugli.java.util;

import java.util.Optional;

public interface ImmutableMap<K, V> extends ImmutableSet<MapEntry<K, V>> {

    boolean containsKey(K key);

    boolean containsValue(V value);

    Optional<V> get(K key);

    ImmutableSet<K> keySet();

    ImmutableCollection<V> values();

}
