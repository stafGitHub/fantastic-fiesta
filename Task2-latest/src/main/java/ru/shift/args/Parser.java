package ru.shift.args;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Parser {

    public static Map<String, String> parseApplicationArgs(String[] arguments) {
        var applicationArgsMap = new HashMap<String, String>();

        for (String argument : arguments) {
            if (argument.startsWith("-")) {
                String[] arg = argument.split("::");

                try {
                    applicationArgsMap.put(arg[0].substring(1), arg[1]);
                }catch (IndexOutOfBoundsException e){
                    log.warn("Ошибка чтения консольных аргументов программы {} - {}", argument , e.getMessage());
                    throw e;
                }

            }
        }

        if (!applicationArgsMap.containsKey("r")){
            throw new NotFileReadPath();
        }

        log.info("Консольные аргументы прочитаны");
        return applicationArgsMap;
    }
}