package se.ugli.java.lang;

import java.io.Serializable;
import java.util.Objects;

public class ValueObject<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -8029278818258881658L;
    public final T value;

    public ValueObject(final T value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass().isAssignableFrom(getClass()))
            return Objects.deepEquals(this.value, ((ValueObject<?>) obj).value);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [value=" + value + "]";
    }

}
