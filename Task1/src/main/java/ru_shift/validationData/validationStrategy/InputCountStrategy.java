package ru_shift.validationData.validationStrategy;

import ru_shift.dto.UserInput;
import ru_shift.validationData.Validation;

public class InputCountStrategy implements Validation<UserInput> {
    int MAX_NUMBER;
    int MIN_NUMBER;

    public InputCountStrategy(int min , int max) {
        this.MIN_NUMBER = min;
        this.MAX_NUMBER = max;
    }

    @Override
    public void validate(UserInput input) {
        input.setValid(input.getUserNumber() >= MIN_NUMBER && input.getUserNumber() <= MAX_NUMBER);
    }
}
