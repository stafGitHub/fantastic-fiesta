package ru_shift.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Config {
    private final int producerCount;
    private final int consumerCount;
    private final int producerTime;
    private final int consumerTime;
    private final int storageSize;

    public Config(String configFile) throws ConfigurationException {
        try {
            var property = FileConfig.getProperty(configFile);

            this.producerCount = FileConfig.getPropertyElement(property, "producerCount");
            this.consumerCount = FileConfig.getPropertyElement(property, "consumerCount");
            this.producerTime = FileConfig.getPropertyElement(property, "producerTime");
            this.consumerTime = FileConfig.getPropertyElement(property, "consumerTime");
            this.storageSize = FileConfig.getPropertyElement(property, "storageSize");
        } catch (ConfigurationException configurationException) {
            log.warn("Ошибка создания : {}", configurationException.getMessage());
            throw configurationException;
        }
    }
}
