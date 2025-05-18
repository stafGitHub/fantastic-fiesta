package ru.shift.network.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.model.*;

import java.util.List;

public final class Mapper {
    @Getter
    private static ObjectMapper jsonMapper;
    private static final List<Class<? extends ServerMessage>> messageSubTypesClasses;

    static {
        messageSubTypesClasses = initMessageSubTypesClasses();
        jsonMapper = initJsonMapper();
    }

    private static ObjectMapper initJsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModules(new ParameterNamesModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        messageSubTypesClasses.forEach(objectMapper::registerSubtypes);

        return objectMapper;
    }

    private static List<Class<? extends ServerMessage>> initMessageSubTypesClasses() {
        return List.of(
                ChatMessage.class,
                LoginMessageError.class,
                LoginMessageSuccess.class,
                SystemMessage.class,
                UsersMessage.class
        );
    }
}
