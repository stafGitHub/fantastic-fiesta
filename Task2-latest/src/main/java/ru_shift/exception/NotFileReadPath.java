package ru_shift.exception;

public class NotFileReadPath extends RuntimeException {
  public NotFileReadPath() {
    super("Укажите путь к файлу для чтения фигур [-r::?]");
  }
}
