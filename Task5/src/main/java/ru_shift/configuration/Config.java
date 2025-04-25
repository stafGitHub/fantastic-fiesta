package ru_shift.configuration;

import lombok.Getter;

@Getter
public class Config {
    private final int producerCount;
    private final int consumerCount;
    private final int producerTime;
    private final int consumerTime;
    private final int storageSize;

    public Config(String configFile) throws ErrorConfiguration {
        var property = UtilConfig.getProperty(configFile);

        this.producerCount = UtilConfig.getPropertyElement(property, "producerCount");
        this.consumerCount = UtilConfig.getPropertyElement(property, "consumerCount");
        this.producerTime = UtilConfig.getPropertyElement(property, "producerTime");
        this.consumerTime = UtilConfig.getPropertyElement(property, "consumerTime");
        this.storageSize = UtilConfig.getPropertyElement(property, "storageSize");
    }
}
