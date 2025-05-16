package ru.shift.server.chat.endpoints;

import ru.shift.network.model.MessageType;
import ru.shift.server.chat.session.SessionManager;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public interface EndpointsDispatcher {
    SessionManager sessionManager = new SessionManager();

    Map<MessageType, Endpoint> endpoints = ServiceLoader.load(AbstractEndpoint.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toMap(
                    Endpoint::getMessageType,
                    endpoint -> {
                        endpoint.setSessionManager(sessionManager);
                        return endpoint;
                    }
            ));

    static Endpoint getEndpointByMessageType(MessageType protocol) {
        return endpoints.get(protocol);
    }
}
