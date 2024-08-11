package com.lcaohoanq.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class StringUtils {
    public static LinkedHashMap<String, String> convertStringVisualToEnum(String... args) {
        return Arrays.stream(args)
            .collect(Collectors.toMap(
                s -> s, //This specifies how the keys in the map are created. Here, s -> s means that each original string will be used as its own key.
                s -> s.toUpperCase().replace(" ", "_"), //This specifies how the values in the map are created. Each string is converted to uppercase, and any spaces are replaced with underscores.
                (oldValue, newValue) -> oldValue, //This handles cases where there are duplicate keys. If a duplicate key is found, the merge function decides which value to keep. Here, the old value is kept.
                LinkedHashMap::new //This specifies the type of Map to be used. Here, a LinkedHashMap is created, which maintains the order of insertion.
            ));
    }

    public static void main(String[] args) {
        System.out.println(convertStringVisualToEnum("Male", "Female", "Not Provide"));
    }

}
