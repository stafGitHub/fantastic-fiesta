package ru.shift.server.chat.endpoints.factory;

import ru.shift.network.model.MessageType;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.endpoints.Endpoint;
import ru.shift.server.chat.session.SessionManager;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public interface EndpointsFactory<T extends AbstractEndpoint> {
    SessionManager sessionManager = new SessionManager();

    MessageType getMessageType();

    T createEndpoint(SessionManager sessionManager);

    Map<MessageType, Endpoint> endpoints = ServiceLoader.load(EndpointsFactory.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toMap(
                    EndpointsFactory::getMessageType,
                    factory -> factory.createEndpoint(sessionManager))
            );

    static Endpoint getEndpointByMessageType(MessageType protocol) {
        return endpoints.get(protocol);
    }
}
