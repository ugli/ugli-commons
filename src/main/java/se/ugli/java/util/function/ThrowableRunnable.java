package se.ugli.java.util.function;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Exception;

}