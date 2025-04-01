package ru.shift.validationData.validationStrategy;

import ru.shift.dto.UserInput;
import ru.shift.validationData.Validation;

public class InputCountStrategy implements Validation<UserInput> {
    private static final int MAX_NUMBER = 32;
    private static final int MIN_NUMBER = 1;


    @Override
    public void validate(UserInput input) {
        input.setValid(input.getUserNumber() >= MIN_NUMBER && input.getUserNumber() <= MAX_NUMBER);
    }
}
