package ru_shift;

import ru_shift.dto.UserInput;
import ru_shift.Table.Table;
import ru_shift.util.PropertiesData;
import ru_shift.validationData.ValidationStrategyController;
import ru_shift.validationData.validationStrategy.InputCountStrategy;

public class Main {
    public static void main(String[] args) {
        PropertiesData propertiesData = new PropertiesData();
        int minNumber = Integer.parseInt((String) propertiesData.getProperties().get("min_number"));
        int maxNumber = Integer.parseInt((String) propertiesData.getProperties().get("max_number"));


        ValidationStrategyController validationStrategyController = new ValidationStrategyController();
        InputCountStrategy inputCountStrategy = new InputCountStrategy(minNumber, maxNumber);
        validationStrategyController.registerValidation(inputCountStrategy);

        UserInput userInput = new UserInput();
        validationStrategyController.validations(userInput);

        if (userInput.isValid()) {
            Table table = new Table(userInput.getUserNumber());
            System.out.println(table.createTable());
        }else {
            System.out.println("""
                    Не прошли валидацию данных.
                    В данном случае , реализована одна - не соответствие границам допустимых чисел
                    """);
        }


    }
}