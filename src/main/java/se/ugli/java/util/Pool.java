package se.ugli.java.util;

import java.io.Closeable;
import java.util.function.Consumer;

public interface Pool<T> extends Closeable {

    void borrow(Consumer<T> borrower);

}
