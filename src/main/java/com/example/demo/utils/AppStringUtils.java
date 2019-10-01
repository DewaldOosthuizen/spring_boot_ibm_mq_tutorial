package com.example.demo.utils;

import java.util.Arrays;

/**
 * The type App string utils.
 */
public class AppStringUtils {

    /**
     * Create string string.
     *
     * @param args the args
     * @return the string
     */
    public static String createString(String... args) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(args).forEach(val -> builder.append(val));
        return builder.toString();
    }

}
