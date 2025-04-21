package ru_shift.factory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Slf4j
public class FactoryConfig {
    private final int producerCount;
    private final int consumerCount;
    private final int producerTime;
    private final int consumerTime;
    private final int storageSize;

    public FactoryConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new ErrorConfiguration("Файл загрузки не найден в classpath");
            }
            props.load(input);

            this.producerCount = Integer.parseInt(props.getProperty("producerCount"));
            this.consumerCount = Integer.parseInt(props.getProperty("consumerCount"));
            this.producerTime = Integer.parseInt(props.getProperty("producerTime"));
            this.consumerTime = Integer.parseInt(props.getProperty("consumerTime"));
            this.storageSize = Integer.parseInt(props.getProperty("storageSize"));

            log.info("Конфигурация загружена: {} производителей, {} потребителей", producerCount, consumerCount);

        } catch (IOException | NumberFormatException e) {
            throw new ErrorConfiguration(e);
        }
    }

}