package ru.shift.network.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import ru.shift.network.model.*;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginMessageSuccess.class, name = "LOGIN_SUCCESS"),
        @JsonSubTypes.Type(value = LoginMessageError.class, name = "LOGIN_FAIL"),
        @JsonSubTypes.Type(value = ChatMessage.class, name = "CHAT_MESSAGE"),
        @JsonSubTypes.Type(value = SystemMessage.class, name = "SYSTEM_MESSAGE"),
        @JsonSubTypes.Type(value = UsersMessage.class, name = "GET_USERS")
})
@Getter
public abstract class ServerMessage implements Serializable {
    private final LocalDate dispatchDate;

    protected ServerMessage(LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }
}
