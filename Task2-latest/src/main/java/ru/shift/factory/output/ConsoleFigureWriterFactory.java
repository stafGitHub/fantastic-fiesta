package ru.shift.factory.output;

import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;
import ru.shift.output.specific.writer.ConsoleFigureWriter;

@Slf4j
public class ConsoleFigureWriterFactory implements WriterFactory<FigureWriter<AbstractFigure>> {
    @Override
    public OutputTypes getNameOfTheFigureWriter() {
        return OutputTypes.consoleOutput;
    }

    @Override
    public FigureWriter<AbstractFigure> createWriter() {
        var consoleFigureWriter = new ConsoleFigureWriter();
        log.info("Создан {}", consoleFigureWriter);
        return consoleFigureWriter;
    }
}
