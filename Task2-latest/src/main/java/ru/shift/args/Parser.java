package ru.shift.args;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
public class Parser {

    public static ApplicationArgs parseApplicationArgs(String[] arguments) {
        var applicationArgs = new ApplicationArgs();

        var it = Arrays.stream(arguments).iterator();
        while (it.hasNext()) {
            var arg = it.next();
            if (!arg.startsWith("-")) break;

            switch (arg) {
                case "-r" -> {
                    if (!it.hasNext()) throw new IllegalArgumentException("Отсутствует аргумент '-f'");
                    applicationArgs.setFileReadPath(Path.of(it.next()));
                }
                case "-s" -> {
                    if (!it.hasNext()) throw new IllegalArgumentException("Отсутствует аргумент '-s'");
                    applicationArgs.setFileWritePath(Path.of(it.next()));
                }
            }
        }

        if (applicationArgs.getFileReadPath() == null) {
            throw new NotFileReadPath();
        }

        log.info("Консольные аргументы прочитаны");

        return applicationArgs;
    }
}