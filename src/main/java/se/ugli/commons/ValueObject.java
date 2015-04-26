package se.ugli.commons;

import java.io.Serializable;

public abstract class ValueObject<T> implements Serializable {

	private static final long serialVersionUID = -8304815984373703779L;

	public final T value;

	protected ValueObject(final T value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final ValueObject<?> that = (ValueObject<?>) obj;
		return this.value == null && that.value == null || this.value != null && this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return 31 * 1 + (value == null ? 0 : value.hashCode());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [value=" + value + "]";
	}

}
