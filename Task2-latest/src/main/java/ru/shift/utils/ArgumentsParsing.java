package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ArgumentsParsing {
    private static final String SPLIT_ARGUMENTS = " ";

    private ArgumentsParsing() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] parseArguments(String br, int totalArgs) throws IllegalArgumentException {
        log.info("Парсинг аргументов фигуры");
        String[] args;

        try {
            args = br.split(SPLIT_ARGUMENTS);
        }catch (NullPointerException e) {
            log.warn("Параметры фигуры отсутствуют");
            throw new IllegalArgumentException("Параметры фигуры не могут быть пустыми");
        }

        if (totalArgs != args.length) {
            log.warn("Количество аргументов несоответствует фигуре");
            throw new IllegalArgumentException("Количество аргументов несоответствует фигуре %s --> %s".formatted(args.length, totalArgs));
        } else {
            return args;
        }
    }

    public static double parseDouble(String str) throws IllegalArgumentException {
        double parseDouble;
        try {
            parseDouble = Double.parseDouble(str);
            log.info("Аргументы приведены к типу double");
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка преобразования параметра фигуры {} - {}", str, e.getMessage());
            throw new IllegalArgumentException("Значение должно быть числом");
        }
        return checkPositiveNumber(parseDouble);
    }

    private static double checkPositiveNumber(double number) throws IllegalArgumentException {
        if (number < 0) {
            log.warn("Ошибка , число отрицательное {} ", number);
            throw new IllegalArgumentException("Число меньше 0");
        } else if (number == 0) {
            log.warn("Ошибка , число равно 0 ");
            throw new IllegalArgumentException("Число не может быть 0");
        } else {
            return number;
        }
    }
}
