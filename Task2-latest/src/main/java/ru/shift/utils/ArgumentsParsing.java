package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public final class ArgumentsParsing {
    private static final String SPLIT_ARGUMENTS = " ";

    private ArgumentsParsing() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] parseArguments(BufferedReader br, int totalArgs) throws IOException, IllegalArgumentException {
        log.info("Парсинг аргументов фигуры");

        var args = br.readLine().split(SPLIT_ARGUMENTS);

        if (totalArgs != args.length) {
            log.warn("Количество аргументов несоответствует фигуре {} --> {}", args.length, totalArgs);
            throw new IllegalArgumentException("Количество аргументов несоответствует фигуре");
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
