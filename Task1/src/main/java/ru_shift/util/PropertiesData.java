package ru_shift.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesData {
    private final Properties properties = new Properties();

    {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
