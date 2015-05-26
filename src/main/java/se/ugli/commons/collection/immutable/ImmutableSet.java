package se.ugli.commons.collection.immutable;

public class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {

	private static final long serialVersionUID = -9068460717434050964L;

	public ImmutableSet(final java.util.Set<E> setImpl) {
		super(setImpl);
	}

}
