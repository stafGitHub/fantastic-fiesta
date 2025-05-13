package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class LoginMessageError extends ServerMessage {
    private final String exception;

    @JsonCreator
    public LoginMessageError(@JsonProperty("dispatchDate") LocalDate dispatchDate,
                             @JsonProperty("exception") String exception) {
        super(dispatchDate);
        this.exception = exception;
    }

}
