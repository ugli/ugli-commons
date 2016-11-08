package se.ugli.java.util;

import java.util.UUID;

import se.ugli.java.lang.ValueObject;

public class Id extends ValueObject<String> {

    private static final long serialVersionUID = -576487949652181350L;

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