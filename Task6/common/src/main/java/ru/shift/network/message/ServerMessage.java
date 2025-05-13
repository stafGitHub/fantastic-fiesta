package ru.shift.network.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.shift.network.MessageType;
import ru.shift.network.model.*;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginMessageSuccess.class, name = "LOGIN_OK"),
        @JsonSubTypes.Type(value = LoginMessageError.class, name = "LOGIN_ERROR"),
        @JsonSubTypes.Type(value = ChatMessage.class, name = "SEND_MESSAGE"),
        @JsonSubTypes.Type(value = SystemMessage.class, name = "SYSTEM_MESSAGE"),
        @JsonSubTypes.Type(value = UsersMessage.class, name = "USER_MESSAGE")
})
public interface ServerMessage extends Serializable {
    MessageType getMessageStatus();

    default LocalDate getMessageDate() {
        return LocalDate.now();
    }
}
