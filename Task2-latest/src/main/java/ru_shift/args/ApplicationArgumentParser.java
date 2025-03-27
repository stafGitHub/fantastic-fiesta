package ru_shift.args;

import lombok.extern.slf4j.Slf4j;
import ru_shift.exception.NotFileReadPath;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApplicationArgumentParser {

    public Map<String, Object> argumentConfigure(String[] arguments) {
        var applicationArgsMap = new HashMap<String, Object>();

        for (String argument : arguments) {
            if (argument.startsWith("-")) {
                String[] arg = argument.split("::");

                try {
                    applicationArgsMap.put(arg[0].substring(1), arg[1]);
                }catch (IndexOutOfBoundsException e){
                    log.warn("Ошибка чтения консольных аргументов программы {}", argument);
                    log.warn("Подробности {} ", e.getMessage());
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