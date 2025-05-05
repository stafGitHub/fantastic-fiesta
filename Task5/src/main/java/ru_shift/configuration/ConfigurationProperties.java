package ru_shift.configuration;

public record ConfigurationProperties(int producerCount, int consumerCount, int producerTime,
                                      int consumerTime, int storageSize) {
}

