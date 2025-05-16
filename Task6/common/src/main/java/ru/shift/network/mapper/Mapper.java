package ru.shift.network.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.shift.network.message.ServerMessage;

import java.util.List;
import java.util.ServiceLoader;

public interface Mapper {
    List<ServerMessage> MESSAGE_SUBTYPES = ServiceLoader.load(ServerMessage.class).stream()
            .map(ServiceLoader.Provider::get)
            .toList();

    ObjectMapper jsonMapper = initJsonMapper();

    private static ObjectMapper initJsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MESSAGE_SUBTYPES.forEach(serverMessage -> objectMapper.registerSubtypes(serverMessage.getClass()));

        return objectMapper;
    }

    static ObjectMapper getMapper() {
        return jsonMapper;
    }

}
