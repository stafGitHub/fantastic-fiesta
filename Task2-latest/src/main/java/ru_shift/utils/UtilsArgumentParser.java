package ru_shift.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public final class UtilsArgumentParser {
    private static final String SPLIT_ARGUMENTS = " ";
    private static final int NUMBER_EXCEPTION = -1;

    private UtilsArgumentParser() {
    }

    public static String[] parseArguments(BufferedReader br) throws IOException {
        log.info("Парсинг аргументов фигуры");

        return br.readLine().split(SPLIT_ARGUMENTS);
    }

    public static double parseDouble(String str) {
        try {

            var parseDouble = Double.parseDouble(str);
            log.info("Аргументы приведены к типу double");

            return checkPositiveNumber(parseDouble);
        }catch (NumberFormatException e) {
            log.error("Ошибка преобразования параметра фигуры {}" , str);
            log.error("Подробности {} " , e.getMessage());
            return NUMBER_EXCEPTION;
        }
    }

    private static double checkPositiveNumber(double str) {
        if (str < 0) {
            log.error("Ошибка , число отрицательное {} " , str);
            return NUMBER_EXCEPTION;
        }else if (str == 0) {
            log.error("Ошибка , число равно {} " , str);
            return NUMBER_EXCEPTION;
        }else {
            return str;
        }
    }
}
