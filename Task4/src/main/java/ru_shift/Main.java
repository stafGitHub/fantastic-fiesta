package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ArithmeticProgressionSumCalculator;
import ru_shift.calculations.ComputationStrategy;
import ru_shift.services.CalculationService;
import ru_shift.util.UserInputScanner;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var number = UserInputScanner.parseLongFromUser();
        ComputationStrategy calculatingTheSeries = new ArithmeticProgressionSumCalculator();
        CalculationService calculationService = new CalculationService(calculatingTheSeries);
        calculationService.calculate(number);
    }
}