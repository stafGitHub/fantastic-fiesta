package ru_shift.configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Slf4j
public class FileConfig {
    private FileConfig() {
        throw new UnsupportedOperationException("Это служебный класс, и его экземпляр не может быть создан.");
    }

    public static int getPropertyElement(Properties properties, String key) throws ConfigurationException {
        var propertyElement = properties.get(key);
        try {
            var number = Integer.parseInt(propertyElement.toString());

            if (number == 0) {
                throw new ConfigurationException("Значение не может быть 0: %s".formatted(key));
            }
            if (number < 0) {
                log.info("Значение преобразовано в положительное: {} - ({})", key, number);
                number = number * -1;
            }

            log.info("Получено значение : {} - {}", key, number);

            return number;
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Значение должно быть числом : %s - %s".formatted(key, propertyElement));
        }catch (NullPointerException e){
            throw new ConfigurationException("Значение не может быть пустым : %s - %s".formatted(key, propertyElement));
        }
    }

    public static Properties getProperty(String fileName) throws ConfigurationException {
        try (InputStream input = FileConfig.class.getClassLoader().getResourceAsStream(fileName)) {
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
}