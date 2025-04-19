package ru_shift.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ComputationStrategy;
import ru_shift.dto.Task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@RequiredArgsConstructor
public class CalculationService {
    public static final int START_OF_RANGE = 0;
    private final ComputationStrategy computationStrategy;

    public void calculate(long number) {
        log.info("Range calculation: {} - {} , formula - {}" , START_OF_RANGE , number, computationStrategy.getFormula());
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Task task = Task.createRootTask(START_OF_RANGE,number,computationStrategy);

        BigDecimal result = forkJoinPool.invoke(task);

        long end = System.currentTimeMillis();
        long duration = end - start;

        log.info("Calculation completed: {} - {} ms",result.setScale(10, RoundingMode.HALF_UP) , duration);
    }
}
