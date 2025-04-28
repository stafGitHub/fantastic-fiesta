package ru_shift.configuration;

import lombok.extern.slf4j.Slf4j;
import ru_shift.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Slf4j
public final class ConfigurationLoader {
    private ConfigurationLoader() {
    }

    public static ConfigurationProperties getProperties(String fileName) throws ConfigurationException {
        Properties properties = readProperties(fileName);
        return new ConfigurationProperties(
                getPropertyElement(properties, ConfigurationParam.PRODUCER_COUNT.propertyKey),
                getPropertyElement(properties, ConfigurationParam.CONSUMER_COUNT.propertyKey),
                getPropertyElement(properties, ConfigurationParam.PRODUCER_TIME.propertyKey),
                getPropertyElement(properties, ConfigurationParam.CONSUMER_TIME.propertyKey),
                getPropertyElement(properties, ConfigurationParam.STORAGE_SIZE.propertyKey)

        );
    }

    private static Properties readProperties(String fileName) throws ConfigurationException {
        try (InputStream input = ConfigurationLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ConfigurationException("Файл загрузки не найден в classpath");
            }

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }

    private static int getPropertyElement(Properties properties, String key) throws ConfigurationException {
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
            throw new ConfigurationException("Значение должно быть числом : %s - %s".formatted(key, propertyElement));
        }
    }
}