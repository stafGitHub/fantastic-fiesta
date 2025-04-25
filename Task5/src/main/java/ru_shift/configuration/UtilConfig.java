package ru_shift.configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Slf4j
public class UtilConfig {
    public static int getPropertyElement(Properties properties, String key) throws ErrorConfiguration {
        var propertyElement = properties.get(key);
        try {
            var number = Integer.parseInt(propertyElement.toString());

            if (number == 0) {
                throw new ErrorConfiguration("Значение не может быть 0: %s".formatted(key));
            }
            if (number < 0) {
                log.info("Значение преобразовано в положительное: {} - ({})", key, number);
                number = number * -1;
            }

            log.info("Получено значение : {} - {}", key, number);

            return number;
        } catch (NumberFormatException e) {
            throw new ErrorConfiguration("Значение должно быть числом : %s - %s".formatted(key, propertyElement));
        }
    }

    public static Properties getProperty(String fileName) throws ErrorConfiguration {
        try (InputStream input = UtilConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ErrorConfiguration("Файл загрузки не найден в classpath");
            }

            Properties properties = new Properties();

            properties.load(input);

            return properties;
        } catch (IOException e) {
            throw new ErrorConfiguration(e);
        }
    }
}