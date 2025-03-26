package ru_shift.repository.application.args.repo;

public interface Operation<I , O> {
    void execute(I input , O object);
}
