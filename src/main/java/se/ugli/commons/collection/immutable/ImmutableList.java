package se.ugli.commons.collection.immutable;

public class ImmutableList<E> extends ImmutableCollection<E> implements List<E> {

	private static final long serialVersionUID = -6640721546365433623L;

	private final java.util.List<E> listImpl;

	public ImmutableList(final java.util.List<E> listImpl) {
		super(listImpl);
		this.listImpl = listImpl;
	}

	@Override
	public E get(final int index) {
		return listImpl.get(index);
	}

	@Override
	public int indexOf(final Object o) {
		return listImpl.indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o) {
		return listImpl.lastIndexOf(o);
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return new ImmutableList<E>(listImpl.subList(fromIndex, toIndex));
	}

}
