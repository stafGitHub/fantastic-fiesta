package ru.shift.server.chat.endpoints;

import ru.shift.network.MessageType;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public interface EndpointFactory {
    Map<MessageType, Endpoint> factories = ServiceLoader.load(Endpoint.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toMap(Endpoint::getProtocol, e -> e));

    static Endpoint create(MessageType protocol) {
        return factories.get(protocol);
    }
}
