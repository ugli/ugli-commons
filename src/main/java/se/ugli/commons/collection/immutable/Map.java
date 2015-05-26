package se.ugli.commons.collection.immutable;

import java.io.Serializable;
import java.util.Map.Entry;

public interface Map<K, V> extends Iterable<Entry<K, V>>, Serializable {

	boolean containsKey(Object key);

	boolean containsValue(Object value);

	Set<Entry<K, V>> entrySet();

	V get(Object key);

	boolean isEmpty();

	Set<K> keySet();

	int size();

	Collection<V> values();
}
