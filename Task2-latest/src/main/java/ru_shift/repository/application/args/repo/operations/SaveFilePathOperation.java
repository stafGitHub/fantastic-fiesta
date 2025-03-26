package ru_shift.repository.application.args.repo.operations;

import ru_shift.dto.ApplicationData;
import ru_shift.repository.application.args.repo.ArgsName;
import ru_shift.repository.application.args.repo.Operation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public class SaveFilePathOperation implements Operation<Map<String,Object>, ApplicationData.ApplicationDataBuilder> {
    @Override
    public void execute(Map<String, Object> input, ApplicationData.ApplicationDataBuilder object) {
        var consoleArgument = Optional.ofNullable(input.get(ArgsName.s.name()));

        if (consoleArgument.isPresent()) {
            Path filePath = Paths.get((String) consoleArgument.get());
            if (Files.exists(filePath)) {
                object.saveFilePath(consoleArgument.get().toString());
            }else {
                object.saveFilePath(".");
            }

        }else {
            object.saveFilePath(ArgsName.console.name());
        }
    }
}
