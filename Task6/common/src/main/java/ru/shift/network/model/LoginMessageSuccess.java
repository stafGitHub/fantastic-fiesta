package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public class LoginMessageSuccess extends ServerMessage {
    @Builder
    @JsonCreator
    public LoginMessageSuccess(LocalDate dispatchDate) {
        super(dispatchDate);
    }
}
