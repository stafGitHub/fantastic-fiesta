package ru.shift.output.specific.writer;

import lombok.extern.slf4j.Slf4j;
import ru.shift.args.ApplicationArgs;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Slf4j
public class UserFileFigureWriter implements FigureWriter<AbstractFigure> {

    @Override
    public void write(AbstractFigure figure, ApplicationArgs applicationArgs) throws IOException {
        var savePath = applicationArgs.getFileWritePath();

        try (BufferedWriter writer = Files.newBufferedWriter(savePath, StandardOpenOption.APPEND)) {
            writer.write(figure.getDescription());
        } catch (IOException e) {
            log.warn("Ошибка сохранения в файл {} - {}", savePath , e.getClass().getSimpleName());
            throw e;
        }

        log.info("Фигура записана в файл {} ", savePath);
    }
}
