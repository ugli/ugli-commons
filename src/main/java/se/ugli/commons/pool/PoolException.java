package se.ugli.commons.pool;

public class PoolException extends RuntimeException {

    public PoolException(Exception e) {
        super(e);
    }

    public PoolException(String msg) {
        super(msg);
    }
}
