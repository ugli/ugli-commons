package se.ugli.commons.collection.mutable;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapFactory {

	private static class EntryImp<K, V> implements Entry<K, V> {

		private final K key;
		private V value;

		EntryImp(final K key, final V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(final V value) {
			final V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

	}

	public static <K, V> ConcurrentMap<K, V> concurrentHashMap(final Entry<K, V>... entries) {
		return concurrentHashMap(asList(entries));
	}

	public static <K, V> ConcurrentMap<K, V> concurrentHashMap(final Iterable<Entry<K, V>> entries) {
		return (ConcurrentHashMap<K, V>) createMap(entries, new ConcurrentHashMap<K, V>());
	}

	public static <K, V> ConcurrentMap<K, V> concurrentHashMap(final Map<K, V> map) {
		return new ConcurrentHashMap<K, V>(map);
	}

	public static <K, V> ConcurrentNavigableMap<K, V> concurrentSkipListMap(final Entry<K, V>... entries) {
		return concurrentSkipListMap(asList(entries));
	}

	public static <K, V> ConcurrentNavigableMap<K, V> concurrentSkipListMap(final Iterable<Entry<K, V>> entries) {
		return (ConcurrentNavigableMap<K, V>) createMap(entries, new ConcurrentSkipListMap<K, V>());
	}

	public static <K, V> ConcurrentNavigableMap<K, V> concurrentSkipListMap(final Map<K, V> map) {
		return new ConcurrentSkipListMap<K, V>(map);
	}

	public static <K, V> Entry<K, V> entry(final K key, final V value) {
		return new EntryImp<K, V>(key, value);
	}

	public static <K, V> Map<K, V> hashMap(final Entry<K, V>... entries) {
		return hashMap(asList(entries));
	}

	public static <K, V> Map<K, V> hashMap(final Iterable<Entry<K, V>> entries) {
		return createMap(entries, new HashMap<K, V>());
	}

	public static <K, V> Map<K, V> hashMap(final Map<K, V> map) {
		return new HashMap<K, V>(map);
	}

	public static <K, V> Map<K, V> identityHashMap(final Entry<K, V>... entries) {
		return identityHashMap(asList(entries));
	}

	public static <K, V> Map<K, V> identityHashMap(final Iterable<Entry<K, V>> entries) {
		return createMap(entries, new IdentityHashMap<K, V>());
	}

	public static <K, V> Map<K, V> identityHashMap(final Map<K, V> map) {
		return new IdentityHashMap<K, V>(map);
	}

	public static <K, V> Map<K, V> linkedHashMap(final Entry<K, V>... entries) {
		return linkedHashMap(asList(entries));
	}

	public static <K, V> Map<K, V> linkedHashMap(final Iterable<Entry<K, V>> entries) {
		return createMap(entries, new LinkedHashMap<K, V>());
	}

	public static <K, V> Map<K, V> linkedHashMap(final Map<K, V> map) {
		return new LinkedHashMap<K, V>(map);
	}

	public static <K, V> NavigableMap<K, V> treeMap(final Entry<K, V>... entries) {
		return treeMap(asList(entries));
	}

	public static <K, V> NavigableMap<K, V> treeMap(final Iterable<Entry<K, V>> entries) {
		return (NavigableMap<K, V>) createMap(entries, new TreeMap<K, V>());
	}

	public static <K, V> NavigableMap<K, V> treeMap(final Map<K, V> map) {
		return new TreeMap<K, V>(map);
	}

	public static <K, V> Map<K, V> weakHashMap(final Entry<K, V>... entries) {
		return weakHashMap(asList(entries));
	}

	public static <K, V> Map<K, V> weakHashMap(final Iterable<Entry<K, V>> entries) {
		return createMap(entries, new WeakHashMap<K, V>());
	}

	public static <K, V> Map<K, V> weakHashMap(final Map<K, V> map) {
		return new WeakHashMap<K, V>(map);
	}

	private static <K, V> Map<K, V> createMap(final Iterable<Entry<K, V>> entries, final Map<K, V> mapImpl) {
		for (final Entry<K, V> entry : entries)
			mapImpl.put(entry.getKey(), entry.getValue());
		return mapImpl;
	}

}
