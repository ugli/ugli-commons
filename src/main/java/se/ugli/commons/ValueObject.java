package se.ugli.commons;

import java.io.Serializable;
import java.util.Objects;

public class ValueObject<T> implements Serializable {

    private static final long serialVersionUID = -8304815984373703779L;

    public final T value;

    public ValueObject(final T value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object obj) {
        return Objects.deepEquals(obj, this);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
