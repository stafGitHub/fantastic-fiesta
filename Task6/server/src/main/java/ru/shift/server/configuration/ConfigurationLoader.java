package ru.shift.server.configuration;

import lombok.extern.slf4j.Slf4j;
import ru.shift.server.expections.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigurationLoader {

    private ConfigurationLoader() {
    }

    public static Configuration getConfiguration(String fileName) {
        var properties = loadProperties(fileName);
        return new Configuration(getPropertyValue(properties, ConfigurationKeys.PORT.getKey()));
    }

    private static Properties loadProperties(String fileName) throws ConfigurationException {
        try (InputStream input = ConfigurationLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ConfigurationException("Файл загрузки не найден в classpath: " + fileName);
            }

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
    }

    private static int getPropertyValue(Properties properties,
                                        String key) throws ConfigurationException {
        var propertyElement = properties.get(key);

        if (propertyElement == null) {
            throw new ConfigurationException("Значение не может быть пустым : %s - %s".formatted(key, null));
        }

        try {
            var number = Integer.parseInt(propertyElement.toString());

            if (number <= 0) {
                throw new ConfigurationException("Число должно быть положительным (>0) %s : %s".formatted(key, number));
            }

            log.info("Получено значение : {} - {}", key, number);

            return number;
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Значение должно быть целым числом : %s - %s".formatted(key, propertyElement));
        }
    }
}
