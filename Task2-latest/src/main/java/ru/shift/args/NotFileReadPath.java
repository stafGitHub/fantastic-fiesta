package ru.shift.args;

public class NotFileReadPath extends RuntimeException {
  public NotFileReadPath() {
    super("Укажите путь к файлу для чтения фигур [-r]");
  }
}
