package com.github.robiiinos.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public final class PropertyService {
    private static final Properties properties = new Properties();

    private static final String DATASOURCE_PROPERTIES = "datasource.properties";

    static {
        try {
            InputStream inputStream = PropertyService.class
                    .getClassLoader().getResourceAsStream(DATASOURCE_PROPERTIES);

            properties.load(inputStream);
        } catch (NullPointerException | IOException ignored) {}
    }

    private PropertyService() {
    }

    public static Map<String, String> get() {
        return properties.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> (String) e.getKey(),
                        e -> (String) e.getValue()
                ));
    }
}
