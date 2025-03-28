package ru.shift.factory.output;

import lombok.extern.slf4j.Slf4j;
import ru.shift.args.NameConsoleArgs;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;
import ru.shift.output.specific.writer.UserFileFigureWriter;

@Slf4j
public class UserFileWriterFactory implements WriterFactory<FigureWriter<AbstractFigure>> {
    @Override
    public String getNameOfTheFigureWriter() {
        return NameConsoleArgs.s.name();
    }

    @Override
    public FigureWriter<AbstractFigure> createWriter() {
        var userFileFigureWriter = new UserFileFigureWriter();
        log.info("Создан {}", userFileFigureWriter);
        return userFileFigureWriter;
    }
}
