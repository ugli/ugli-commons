package se.ugli.commons.collection.immutable;


public class MapFactory {

	public static <K, V> Map<K, V> hashMap(final Iterable<java.util.Map.Entry<K, V>> entries) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.hashMap(entries));
	}

	public static <K, V> Map<K, V> hashMap(final java.util.Map.Entry<K, V>... entries) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.hashMap(entries));
	}

	public static <K, V> Map<K, V> hashMap(final java.util.Map<K, V> map) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.hashMap(map));
	}

	public static <K, V> Map<K, V> hashMap(final Map<K, V> map) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.hashMap(map));
	}

	public static <K, V> Map<K, V> linkedHashMap(final Iterable<java.util.Map.Entry<K, V>> entries) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.linkedHashMap(entries));
	}

	public static <K, V> Map<K, V> linkedHashMap(final java.util.Map.Entry<K, V>... entries) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.linkedHashMap(entries));
	}

	public static <K, V> Map<K, V> linkedHashMap(final java.util.Map<K, V> map) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.linkedHashMap(map));
	}

	public static <K, V> Map<K, V> linkedHashMap(final Map<K, V> map) {
		return new ImmutableMap<K, V>(se.ugli.commons.collection.mutable.MapFactory.linkedHashMap(map));
	}

}
