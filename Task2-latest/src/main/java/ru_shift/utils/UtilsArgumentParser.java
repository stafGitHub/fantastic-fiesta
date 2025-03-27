package ru_shift.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public final class UtilsArgumentParser {
    private static final String SPLIT_ARGUMENTS = " ";

    private UtilsArgumentParser() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] parseArguments(BufferedReader br) throws IOException {
        log.info("Парсинг аргументов фигуры");

        return br.readLine().split(SPLIT_ARGUMENTS);
    }

    public static double parseDouble(String str) throws IllegalArgumentException {
        try {
            var parseDouble = Double.parseDouble(str);
            log.info("Аргументы приведены к типу double");
            return checkPositiveNumber(parseDouble);

        } catch (IllegalArgumentException e) {
            log.warn("Ошибка преобразования параметра фигуры {}", str);
            log.warn("Подробности {} ", e.getMessage());
            throw new IllegalArgumentException("Значение должно быть числом");
        }
    }

    private static double checkPositiveNumber(double str) throws IllegalArgumentException {
        if (str < 0) {
            log.warn("Ошибка , число отрицательное {} ", str);
            throw new IllegalArgumentException("Ошибка число отрицательное");
        } else if (str == 0) {
            log.warn("Ошибка , число равно 0 ");
            throw new IllegalArgumentException("Ошибка число не может быть равно 0");
        } else {
            return str;
        }
    }
}
