package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ArithmeticProgressionSumCalculator;
import ru_shift.calculations.ComputationStrategy;
import ru_shift.services.CalculationService;
import ru_shift.task.TaskCreator;
import ru_shift.task.TaskExecutor;
import ru_shift.util.UserInputScanner;

import java.util.concurrent.Executors;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var number = UserInputScanner.parseLongFromUser();
        ComputationStrategy calculatingTheSeries = new ArithmeticProgressionSumCalculator();
        var taskCreator = new TaskCreator(calculatingTheSeries);
        var taskExecutor = new TaskExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        CalculationService calculationService = new CalculationService(taskCreator, taskExecutor);
        calculationService.calculate(number);
    }
}