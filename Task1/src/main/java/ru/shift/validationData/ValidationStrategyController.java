package ru.shift.validationData;

import ru.shift.dto.UserInput;

import java.util.ArrayList;
import java.util.List;

public class ValidationStrategyController {
    private final List<Validation<UserInput>> validations = new ArrayList<>();

    public void registerValidation(Validation<UserInput> validation) {
        validations.add(validation);
    }

    public void validations(UserInput userInput) {
        for (Validation<UserInput> validation : validations) {
            validation.validate(userInput);

            if (!userInput.isValid()){
                break;
            }
        }
    }
}
