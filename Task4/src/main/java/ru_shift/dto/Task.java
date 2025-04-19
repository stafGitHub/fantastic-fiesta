package ru_shift.dto;

import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ComputationStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class Task extends RecursiveTask<BigDecimal> {
    public static final int ORACLE_THRESHOLD_COEFFICIENT = 8;
    public static final int THRESHOLD_MIN = 10_000;
    public static final int THRESHOLD_MAX = 1_000_000;

    private final long startNumber;
    private final long endNumber;
    private final ComputationStrategy strategy;
    private final int threshold;

    public static Task createRootTask(long startNumber, long endNumber, ComputationStrategy strategy) {
        return new Task(startNumber, endNumber, strategy, calculateThreshold(endNumber));
    }

    private Task(long startNumber, long endNumber, ComputationStrategy strategy, int threshold) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.strategy = strategy;
        this.threshold = threshold;
    }

    @Override
    protected BigDecimal compute() {
        if (endNumber - startNumber <= threshold) {
            BigDecimal result = strategy.compute(startNumber, endNumber);

            log.info("Calculated range {} - {} = {} (stream: {})",
                    startNumber, endNumber,
                    result.setScale(10, RoundingMode.HALF_UP),
                    Thread.currentThread().getName());

            return result;
        }

        long mid = startNumber + (endNumber - startNumber) / 2;
        Task leftTask = new Task(startNumber, mid, strategy, threshold);
        Task rightTask = new Task(mid + 1, endNumber, strategy, threshold);

        leftTask.fork();
        BigDecimal rightResult = rightTask.compute();
        BigDecimal leftResult = leftTask.join();

        return leftResult.add(rightResult);
    }

    private static int calculateThreshold(long rangeSize) {
        log.info("Calculating the threshold for splitting calculations");
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threshold = (int) (rangeSize / (ORACLE_THRESHOLD_COEFFICIENT * availableProcessors));

        threshold = Math.min(threshold, THRESHOLD_MAX);
        threshold = Math.max(threshold, THRESHOLD_MIN);

        log.info("Calculation completed: {}", threshold);

        return threshold;
    }
}
