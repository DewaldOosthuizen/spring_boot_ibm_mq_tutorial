package com.example.demo.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * The type App file utils.
 */
public class AppFileUtils {

    /**
     * Read line by line java 8 string.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(contentBuilder.toString());
        return contentBuilder.toString();
    }
}
