package se.ugli.commons.collection.immutable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

public class ImmutableMap<K, V> implements Map<K, V> {

	private static class ImmutableEntry<K, V> implements Entry<K, V> {

		private final K key;
		private final V value;

		ImmutableEntry(final Entry<K, V> entry) {
			this.key = entry.getKey();
			this.value = entry.getValue();
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
		public V setValue(final Object value) {
			throw new UnsupportedOperationException("immutable");
		}

	}

	private static final long serialVersionUID = 4094914028437804802L;

	private Set<Entry<K, V>> entrySet;
	private Set<K> keySet;
	private final java.util.Map<K, V> mapImpl;
	private Collection<V> values;

	public ImmutableMap(final java.util.Map<K, V> mapImpl) {
		this.mapImpl = mapImpl;
	}

	@Override
	public boolean containsKey(final Object key) {
		return mapImpl.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return mapImpl.containsValue(value);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (entrySet == null)
			createEntrySet();
		return entrySet;
	}

	@Override
	public V get(final Object key) {
		return mapImpl.get(key);
	}

	@Override
	public boolean isEmpty() {
		return mapImpl.isEmpty();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return entrySet().iterator();
	}

	@Override
	public Set<K> keySet() {
		if (keySet == null)
			createKeySet();
		return keySet;
	}

	@Override
	public int size() {
		return mapImpl.size();
	}

	@Override
	public Collection<V> values() {
		if (values == null)
			createValues();
		return values;
	}

	private synchronized void createEntrySet() {
		if (entrySet == null) {
			final LinkedHashSet<Entry<K, V>> linkedSet = new LinkedHashSet<Entry<K, V>>();
			for (final Entry<K, V> entry : mapImpl.entrySet())
				linkedSet.add(new ImmutableEntry<K, V>(entry));
			entrySet = SetFactory.linkedHashSet(linkedSet);
		}
	}

	private synchronized void createKeySet() {
		if (keySet == null)
			keySet = SetFactory.linkedHashSet(mapImpl.keySet());
	}

	private synchronized void createValues() {
		if (values == null)
			values = ListFactory.linkedList(mapImpl.values());
	}

}
