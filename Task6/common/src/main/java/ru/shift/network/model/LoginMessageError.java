package ru.shift.network.model;


import lombok.Getter;
import lombok.Setter;
import ru.shift.network.message.ServerMessage;

@Getter
@Setter
public class LoginMessageError extends ServerMessage {
    private String exception;
}
