package se.ugli.commons;

import java.util.UUID;

public class Id extends ValueObject<String> {

    private static final long serialVersionUID = -2963877918811034926L;

    private Id(final String value) {
        super(value);
    }

    public static Id apply(final String value) {
        return new Id(value);
    }

    public static Id create() {
        return new Id(UUID.randomUUID().toString().replaceAll("-", ""));
    }

}