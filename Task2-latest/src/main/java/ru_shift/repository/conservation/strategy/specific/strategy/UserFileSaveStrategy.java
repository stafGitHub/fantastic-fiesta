package ru_shift.repository.conservation.strategy.specific.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.AbstractFigure;
import ru_shift.repository.conservation.strategy.SaveStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
@Slf4j
public class UserFileSaveStrategy implements SaveStrategy<AbstractFigure> {
    private final ApplicationData applicationData;

    @Override
    public void save(AbstractFigure input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of(applicationData.getSaveFilePath()), StandardOpenOption.APPEND)) {
            writer.write(input.getDescription());
        } catch (IOException e) {
            log.error("Ошибка сохранения в файл {}", applicationData.getSaveFilePath());

            throw new RuntimeException(e);
        }

        log.info("Файл сохранён {} ", applicationData.getSaveFilePath());
    }
}
