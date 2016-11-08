package se.ugli.java.util;

public class PoolException extends RuntimeException {

    public PoolException(Exception e) {
        super(e);
    }

    public PoolException(String msg) {
        super(msg);
    }
}
