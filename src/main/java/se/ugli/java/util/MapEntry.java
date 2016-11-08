package se.ugli.java.util;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Objects;

public class MapEntry<K, V> implements Serializable {

    private static final long serialVersionUID = 7676596049395358735L;
    public final K key;
    public final V value;

    public MapEntry(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> MapEntry<K, V> apply(final Entry<K, V> entry) {
        return new MapEntry<>(entry.getKey(), entry.getValue());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MapEntry) {
            final MapEntry<?, ?> that = (MapEntry<?, ?>) obj;
            return Objects.equals(this.key, that.key);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return key == null ? "null" : key.toString() + "->" + value;
    }

}