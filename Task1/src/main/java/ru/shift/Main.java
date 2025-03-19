package ru.shift;

import ru.shift.dto.UserInput;
import ru.shift.Table.Table;
import ru.shift.validationData.ValidationStrategyController;
import ru.shift.validationData.validationStrategy.InputCountStrategy;

public class Main {
    public static void main(String[] args) {
        ValidationStrategyController validationStrategyController = new ValidationStrategyController();
        InputCountStrategy inputCountStrategy = new InputCountStrategy();
        validationStrategyController.registerValidation(inputCountStrategy);

        UserInput userInput = new UserInput();
        validationStrategyController.validations(userInput);

        if (userInput.isValid()) {
            Table table = new Table(userInput.getUserNumber());
            table.createTable();
        }else {
            System.out.println("""
                    Не прошли валидацию данных.
                    В данном случае , реализована одна - не соответствие границам допустимых чисел
                    """);
        }


    }
}