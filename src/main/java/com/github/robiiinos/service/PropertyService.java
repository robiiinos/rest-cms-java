package com.github.robiiinos.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyService {
    private PropertyService() {
    }

    public static Properties loadProperties(String propertyFileName) {
        final Properties properties = new Properties();

        try (final InputStream is = PropertyService.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (is != null) {
                properties.load(is);
            }
            else {
                throw new IllegalArgumentException("Cannot find property file: " + propertyFileName);
            }
        }
        catch (IOException io) {
            throw new RuntimeException("Failed to read property file.", io);
        }

        return properties;
    }
}
