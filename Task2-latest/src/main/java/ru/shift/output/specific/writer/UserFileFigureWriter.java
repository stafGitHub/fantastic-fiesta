package ru.shift.output.specific.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;
import ru.shift.args.NameConsoleArgs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Slf4j
public class UserFileFigureWriter implements FigureWriter<AbstractFigure> {

    @Override
    public void write(AbstractFigure figure , Map<String,String> applicationArgs) {
        var savePath = applicationArgs.get(NameConsoleArgs.s.name());
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of(savePath), StandardOpenOption.APPEND)) {
            writer.write(figure.getDescription());
        } catch (IOException e) {
            log.warn("Ошибка сохранения в файл {}", savePath);

            throw new RuntimeException(e);
        }

        log.info("Фигура записана в файл {} ", savePath);
    }
}
