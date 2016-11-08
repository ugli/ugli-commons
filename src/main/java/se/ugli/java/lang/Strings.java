package se.ugli.java.lang;

public class Strings {

    public static String nonEmptyOrNull(final String s) {
        if (s == null || s.trim().isEmpty())
            return null;
        return s.trim();
    }

}
