package ru_shift.repository.fabric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.AbstractFigure;
import ru_shift.repository.application.args.repo.ArgsName;
import ru_shift.repository.conservation.strategy.SaveStrategy;
import ru_shift.repository.conservation.strategy.specific.strategy.ConsoleSaveStrategy;
import ru_shift.repository.conservation.strategy.specific.strategy.CurrentDirectorySaveFileStrategy;
import ru_shift.repository.conservation.strategy.specific.strategy.UserFileSaveStrategy;


@RequiredArgsConstructor
@Slf4j
public class SaveStrategyFactory {
    private final ApplicationData applicationData;

    public SaveStrategy<AbstractFigure> createStrategy(String saveFilePath) {
        SaveStrategy<AbstractFigure> strategy = null;

        if (ArgsName.console.name().equals(saveFilePath)) {
            strategy = new ConsoleSaveStrategy();
        }else if(applicationData.getSaveFilePath().equals(".")) {
            strategy = new CurrentDirectorySaveFileStrategy(applicationData);
        }else if (!saveFilePath.isEmpty()) {
            strategy = new UserFileSaveStrategy(applicationData);
        }else {
            log.warn("Укажите путь для сохранения [-s::?]");
            throw new IllegalArgumentException();
        }

        log.info("Сохранение - {}" , strategy.getClass().getSimpleName());
        return strategy;
    }

}
