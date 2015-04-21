package se.ugli.commons;

import java.io.Serializable;
import java.util.UUID;

public class Id implements Serializable {

	private static final long serialVersionUID = -2963877918811034926L;

	public static Id apply(final String value) {
		return new Id(value);
	}

	public static Id create() {
		return new Id(UUID.randomUUID().toString());
	}

	public final String value;

	private Id(final String value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final Id that = (Id) obj;
		return this.value == null && that.value == null || this.value != null && this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return 31 * 1 + (value == null ? 0 : value.hashCode());
	}

	@Override
	public String toString() {
		return value;
	}

}