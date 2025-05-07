package ru.shift.server.configuration;

import lombok.extern.slf4j.Slf4j;
import ru.shift.server.expections.ConfigurationException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

@Slf4j
public class ConfigurationLoader {
    private static final Map<Class<? extends Number>, Function<String, ?>> NUMBER_PARSERS = Map.of(
            Integer.class, Integer::parseInt,
            Double.class, Double::parseDouble,
            Long.class, Long::parseLong,
            Float.class, Float::parseFloat
    );

    private ConfigurationLoader() {
    }

    public static Configuration getConfiguration(String fileName) {
        var properties = loadProperties(fileName);
        return new Configuration(getPropertyValue(properties, ConfigurationKeys.PORT.getKey(), Integer.class));
    }

    private static Properties loadProperties(String fileName) throws ConfigurationException {
        Properties properties = new Properties();
        try {
            properties.load(ConfigurationLoader.class.getClassLoader().getResourceAsStream(fileName));
            return properties;
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    private static <T extends Number> T getPropertyValue(Properties properties,
                                                         String key,
                                                         Class<T> clazz) throws ConfigurationException {
        String propertyValue = properties.getProperty(key);

        if (propertyValue == null) {
            throw new ConfigurationException(key + ": обязательный параметр отсутствует");
        }

        Number number;

        try {
            number = (Number) NUMBER_PARSERS.get(clazz).apply(propertyValue);
        } catch (NumberFormatException e) {

            throw new ConfigurationException(
                    "Значение указанно не корректно " + key + ": " + e.getMessage() + " --> " + clazz.getName()
            );
        }

        if (number.doubleValue() <= 0) {
            throw new ConfigurationException(key + ": " + number + ": значение должно быть положительным");
        }
        log.info("Параметр получен {} : {}", key, propertyValue);
        return clazz.cast(number);
    }
}
