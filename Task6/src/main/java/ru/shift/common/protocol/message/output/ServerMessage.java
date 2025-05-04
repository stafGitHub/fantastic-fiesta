package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginMessageSuccess.class, name = "LOGIN_OK"),
        @JsonSubTypes.Type(value = LoginMessageError.class, name = "LOGIN_ERROR"),
        @JsonSubTypes.Type(value = SendMessage.class, name = "SEND_MESSAGE"),
        @JsonSubTypes.Type(value = SystemMessage.class, name = "SYSTEM_MESSAGE")
})
public interface ServerMessage extends Serializable {
}
