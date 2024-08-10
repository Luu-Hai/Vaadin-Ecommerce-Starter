package com.lcaohoanq.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class StringUtils {
    public static LinkedHashMap<String, String> convertStringVisualToEnum(String... args) {
        return Arrays.stream(args)
            .collect(Collectors.toMap(
                s -> s,
                String::toUpperCase,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));
    }

    public static void main(String[] args) {
        System.out.println(convertStringVisualToEnum("Male", "Female", "Not Provide"));
    }

}
