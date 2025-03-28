package ru.shift.factory.output;

import ru.shift.figures.AbstractFigure;
import ru.shift.args.NameConsoleArgs;
import ru.shift.figures.NameOfTheFigure;
import ru.shift.output.FigureWriter;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface WriterFactory<F extends FigureWriter<AbstractFigure>> {
    String getNameOfTheFigureWriter();

    Map<String, WriterFactory<?>> figureWriters = ServiceLoader.load(WriterFactory.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(WriterFactory::getNameOfTheFigureWriter, w -> (WriterFactory<?>) w));

    F createWriter();

    static FigureWriter<AbstractFigure> create(Map<String, String> args) {
        WriterFactory<?> writerFactory;

        if (args.containsKey(NameConsoleArgs.s.name())) {
            writerFactory = figureWriters.get(NameConsoleArgs.s.name());
        } else {
            writerFactory = figureWriters.get(NameConsoleArgs.console.name());
        }

        return writerFactory.createWriter();
    }
}
