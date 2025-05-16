package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public class LoginMessageSuccess extends ServerMessage {
    @JsonCreator
    public LoginMessageSuccess(@JsonProperty() LocalDate dispatchDate) {
        super(dispatchDate);
    }
}
