package me.luckkyyz.luckchat.util;

import java.util.regex.Pattern;

public final class Patterns {

    private Patterns() {
        throw new UnsupportedOperationException();
    }

    private static final Pattern LETTERS = Pattern.compile("[^а-яА-Яa-zA-Z]");

    public static String onlyLetters(String string) {
        return LETTERS.matcher(string).replaceAll("");
    }

}
