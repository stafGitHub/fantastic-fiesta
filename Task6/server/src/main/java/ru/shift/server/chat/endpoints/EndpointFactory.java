package ru.shift.server.chat.endpoints;

import ru.shift.network.RequestType;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public interface EndpointFactory {
    Map<RequestType, Endpoint> factories = ServiceLoader.load(Endpoint.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toMap(Endpoint::getProtocol, e -> e));

    static Endpoint create(RequestType protocol) {
        return factories.get(protocol);
    }
}
