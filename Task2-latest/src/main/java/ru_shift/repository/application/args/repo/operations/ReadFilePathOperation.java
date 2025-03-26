package ru_shift.repository.application.args.repo.operations;

import ru_shift.dto.ApplicationData;
import ru_shift.repository.application.args.repo.ArgsName;
import ru_shift.repository.application.args.repo.Operation;

import java.util.Map;

public class ReadFilePathOperation implements Operation<Map<String,Object>, ApplicationData.ApplicationDataBuilder> {
    @Override
    public void execute(Map<String, Object> input, ApplicationData.ApplicationDataBuilder object) {
        String consoleArgument = (String) input.get(ArgsName.r.name());

        object.readFilePath(consoleArgument);
    }
}
