package ru_shift.task;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ComputationStrategy;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
public class Task implements Callable<Task> {
    private static final AtomicInteger id = new AtomicInteger(0);
    private final String taskName;
    private final long start;
    private final long end;
    private final ComputationStrategy strategy;
    private BigDecimal result;

    public Task(long start, long end, ComputationStrategy strategy) {
        taskName = "Task " + id.incrementAndGet();;
        this.start = start;
        this.end = end;
        this.strategy = strategy;
    }

    @Override
    public Task call() {
        log.info("{}: Calculating range {}-{}", taskName, start, end);
        this.result = strategy.compute(start, end);
        log.info("{}: Completed range {}-{}. Result: {}", taskName, start, end, result);
        return this;
    }
}