package ru.shift.server.chat.endpoints;

import ru.shift.network.MessageType;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public interface EndpointProvider {
    Map<MessageType, Endpoint> factories = ServiceLoader.load(Endpoint.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toMap(Endpoint::getProtocol, e -> e));

    static Endpoint getEndpointByMessageType(MessageType protocol) {
        return factories.get(protocol);
    }
}
