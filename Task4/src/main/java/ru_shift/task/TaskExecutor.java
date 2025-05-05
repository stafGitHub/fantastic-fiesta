package ru_shift.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RequiredArgsConstructor
@Slf4j
public class TaskExecutor {
    private final ExecutorService executor;

    public List<Future<Task>> executeTasks(List<Task> tasks) {
        List<Future<Task>> futures = new ArrayList<>();
        for (Task task : tasks) {
            log.info("Execution {}", task.getTaskName());
            futures.add(executor.submit(task));
        }
        return futures;
    }

    public List<Task> awaitCompletion(List<Future<Task>> futures, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {

        List<Task> results = new ArrayList<>();

        try {
            for (Future<Task> future : futures) {
                results.add(future.get(timeout, unit));
            }
            return results;
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            cancelAll(futures);
            throw e;
        }
    }

    public void shutdownNow() {
        executor.shutdownNow();
    }

    private void cancelAll(List<Future<Task>> futures) {
        futures.forEach(f -> f.cancel(true));
    }
}