package ru_shift.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public final class ConsoleProgramRestart {
    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleProgramRestart() {
    }

    public static boolean restart() {
        while (true) {
            log.info("Продолжить использование производства? 1: продолжить, 0: завершить");
            String text = scanner.nextLine();

            try {
                int number = Integer.parseInt(text);

                if (number == 0 || number == 1) {
                    return number == 1;
                } else {
                    log.info("Можно ввести только 0 или 1");
                }

            } catch (NumberFormatException e) {
                log.info("Ошибка: введите число (0 или 1)");
            }
        }
    }
}
