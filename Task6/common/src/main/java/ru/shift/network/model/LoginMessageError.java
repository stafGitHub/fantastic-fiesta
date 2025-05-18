package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class LoginMessageError extends ServerMessage {
    private final String exception;

    @Builder
    @JsonCreator
    public LoginMessageError(LocalDate dispatchDate, String exception) {
        super(dispatchDate);
        this.exception = exception;
    }
}
