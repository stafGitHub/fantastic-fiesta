package ru_shift.repository.conservation.strategy;

public interface SaveStrategy<I> {
    void save(I input);
}
