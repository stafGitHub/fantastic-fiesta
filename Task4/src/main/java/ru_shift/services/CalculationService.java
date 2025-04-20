package ru_shift.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.task.Task;
import ru_shift.task.TaskCreator;
import ru_shift.task.TaskExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
public class CalculationService {
    private final TaskCreator taskCreator;
    private final TaskExecutor taskExecutor;

    public void calculate(long number) {
        log.info("Starting calculation from {} to {}", TaskCreator.START_RANGE, number);
        long startTime = System.currentTimeMillis();

        List<Task> tasks = taskCreator.createTasks(number);
        List<Future<Task>> tasksAtWork = taskExecutor.executeTasks(tasks);
        List<Task> completeTasks;

        try {
            completeTasks = taskExecutor.awaitCompletion(tasksAtWork);
            taskExecutor.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            taskExecutor.shutdown();
            log.error(e.getMessage());
            return;
        }

        BigDecimal result = aggregateResults(completeTasks);

        long duration = System.currentTimeMillis() - startTime;
        log.info("Calculation completed in {} ms ---> result({})", duration, result);
    }

    private BigDecimal aggregateResults(List<Task> completedTasks) {
        return completedTasks.stream()
                .map(Task::getResult)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}