package ru_shift.util;

import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class UserInputScanner {
    private UserInputScanner() {
        throw new UnsupportedOperationException("Static utility class");
    }

    public static long parseLongFromUser() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            log.info("Enter value: ");
            try {
                long value = scanner.nextLong();

                if (value <= 0) {
                    log.info("The number must be greater than 0");
                    continue;
                }

                return value;

            } catch (InputMismatchException e) {
                log.info("the value is incorrect, enter long");
                scanner.nextLine();
            }
        }
    }
}
