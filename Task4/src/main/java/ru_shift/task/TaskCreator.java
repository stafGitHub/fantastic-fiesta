package ru_shift.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.calculations.ComputationStrategy;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TaskCreator {
    public static final int START_RANGE = 1;
    public static final int MIN_RANGE_SIZE = 10_000;
    public static final int MAX_TASKS_PER_CORE = 4;
    private final ComputationStrategy computationStrategy;

    public List<Task> createTasks(long endNumber) {
        log.info("Start creating tasks , formula - {}", computationStrategy.getFormula());

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int optimalTaskCount = calculateOptimalTaskCount(availableProcessors, endNumber);
        long range = calculateRangeSize(endNumber, optimalTaskCount);
        var tasksByRangeSize = createTasksByRangeSize(endNumber, range);

        log.info("Finish creating tasks");

        return tasksByRangeSize;
    }

    private List<Task> createTasksByRangeSize(long endNumber, long rangeSize) {
        List<Task> tasks = new ArrayList<>();
        for (long start = START_RANGE; start <= endNumber; start += rangeSize) {
            long end = Math.min(start + rangeSize - 1, endNumber);
            var task = new Task(start, end, computationStrategy);
            log.info("Creating task {}", task.getTaskName());
            tasks.add(task);
        }
        return tasks;
    }

    private int calculateOptimalTaskCount(int processors, long endNumber) {
        int baseTaskCount = processors * MAX_TASKS_PER_CORE;

        long estimatedRangeSize = endNumber / baseTaskCount;

        if (estimatedRangeSize < MIN_RANGE_SIZE) {
            baseTaskCount = (int) Math.max(1, endNumber / MIN_RANGE_SIZE);
            log.info("Optimal number of tasks {}", baseTaskCount);
            return baseTaskCount;
        }

        log.info("Optimal number of tasks {}", baseTaskCount);
        return baseTaskCount;
    }

    private long calculateRangeSize(long endNumber, int taskCount) {
        var range = endNumber / taskCount;
        log.info("Calculating range size {}", range);
        return range;
    }
}