package ru_shift.configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConfigurationParam {
    PRODUCER_COUNT("producerCount"),
    CONSUMER_COUNT("consumerCount"),
    PRODUCER_TIME("producerTime"),
    CONSUMER_TIME("consumerTime"),
    STORAGE_SIZE("storageSize");

    final String propertyKey;
}
