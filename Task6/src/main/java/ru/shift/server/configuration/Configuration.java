package ru.shift.server.configuration;

import java.util.List;

public record Configuration(List<Integer> ports, List<String> name) {
}
