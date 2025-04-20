package ru_shift.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

    public List<Task> awaitCompletion(List<Future<Task>> futures) throws InterruptedException, ExecutionException {
        List<Task> results = new ArrayList<>();
        for (Future<Task> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                futures.forEach(f -> f.cancel(true));
                throw e;
            } catch (ExecutionException e) {
                futures.forEach(f -> f.cancel(true));
                throw e;
            }
        }
        return results;
    }

    public void shutdown() {
        executor.shutdown();
    }
}