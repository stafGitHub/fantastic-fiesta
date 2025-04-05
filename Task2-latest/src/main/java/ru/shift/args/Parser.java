package ru.shift.args;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
public class Parser {
    private final static String READ_ARGS = "-r";
    private final static String SAVE_ARGS = "-s";

    public static ApplicationArgs parseApplicationArgs(String[] arguments) {
        var applicationArgs = new ApplicationArgs();

        var it = Arrays.stream(arguments).iterator();
        while (it.hasNext()) {
            var arg = it.next();
            if (!arg.startsWith("-")) break;

            switch (arg) {
                case READ_ARGS -> {
                    if (!it.hasNext()) throw new IllegalArgumentException("Отсутствует аргумент '%s'".formatted(READ_ARGS));
                    applicationArgs.setFileReadPath(Path.of(it.next()));
                }
                case SAVE_ARGS -> {
                    if (!it.hasNext()) throw new IllegalArgumentException("Отсутствует аргумент '%s'".formatted(SAVE_ARGS));
                    applicationArgs.setFileWritePath(Path.of(it.next()));
                }
            }
        }

        if (applicationArgs.getFileReadPath() == null) {
            throw new IllegalArgumentException("Укажите путь к файлу для чтения фигур [%s]".formatted(READ_ARGS));
        }

        log.info("Консольные аргументы прочитаны");

        return applicationArgs;
    }
}