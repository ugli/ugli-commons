package se.ugli.java.util.function;

@FunctionalInterface
public interface ThrowableSupplier<T> {

    T get() throws Exception;

}