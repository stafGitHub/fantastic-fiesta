package ru_shift.repository.conservation.strategy.specific.strategy;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.AbstractFigure;
import ru_shift.repository.conservation.strategy.SaveStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class CurrentDirectorySaveFileStrategy implements SaveStrategy<AbstractFigure> {
    public static final String FILE_NAME = "result_Task2_Stafievskiy";
    private final ApplicationData applicationData;
    private final Path savePath;

    public CurrentDirectorySaveFileStrategy(ApplicationData applicationData) {
        this.applicationData = applicationData;
        Path savePath = Paths.get(".").toAbsolutePath().normalize();
        this.savePath = savePath.resolve(FILE_NAME);
    }

    @Override
    public void save(AbstractFigure input) {
        try {
            if (!savePath.toFile().exists()) {
                Files.createFile(savePath);
                log.info("Создан файл для результатов программы {}", savePath);
            }
        } catch (IOException e) {
            log.error("Ошибка создания файла {} " , savePath);
            log.error("Подробности {} " , e.getMessage());
            throw new RuntimeException(e);
        }


        try(BufferedWriter writer = Files.newBufferedWriter(savePath,StandardOpenOption.APPEND)) {
            writer.write(input.getDescription());
        } catch (IOException e) {
            log.error("Ошибка сохранения в файл {}", applicationData.getSaveFilePath());

            throw new RuntimeException(e);
        }

        log.info("Файл сохранён {} ", applicationData.getSaveFilePath());
    }
}
