package ru.shift.factory.output;

import ru.shift.args.ApplicationArgs;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface WriterFactory<F extends FigureWriter<AbstractFigure>> {
    OutputTypes getNameOfTheFigureWriter();

    Map<OutputTypes, WriterFactory<?>> figureWriters = ServiceLoader.load(WriterFactory.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(WriterFactory::getNameOfTheFigureWriter, w -> (WriterFactory<?>) w));

    F createWriter();

    static FigureWriter<AbstractFigure> create(ApplicationArgs args) {
        OutputTypes outputType = args.getFileWritePath() != null ? OutputTypes.saveToUserFile : OutputTypes.consoleOutput;

        WriterFactory<?> writerFactory = figureWriters.get(outputType);
        return writerFactory.createWriter();
    }
}
