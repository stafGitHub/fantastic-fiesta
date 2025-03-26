package ru_shift.repository.application.args.repo;

import ru_shift.dto.ApplicationData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ApplicationArgsRepoController {
    private final ArrayList<Operation<Map<String,Object>,ApplicationData.ApplicationDataBuilder>> operations = new ArrayList<>();

    @SafeVarargs
    public final void addOperation(Operation<Map<String, Object>, ApplicationData.ApplicationDataBuilder>... operation) {
        operations.addAll(Arrays.asList(operation));
    }

    public ApplicationData operationsExecute(Map<String,Object> args , ApplicationData.ApplicationDataBuilder builder) {
        operations.forEach(operation-> operation.execute(args , builder));

        return builder.build();
    }
}
