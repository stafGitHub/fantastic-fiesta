package ru_shift.repository.conservation.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.FigureNameMap;
import ru_shift.repository.fabric.FigureFactory;
import ru_shift.repository.fabric.SaveStrategyFactory;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SaveStrategyController {
    private final ApplicationData applicationData;
    private final SaveStrategyFactory saveStrategyFactory;
    private final FigureFactory figureFactory;


    public void processFigureFromSaveStrategy(BufferedReader fileBufferedReader,
                                              FigureNameMap figureNameMap) {
        var strategy = saveStrategyFactory.createStrategy(applicationData.getSaveFilePath());

        String line;
        try(fileBufferedReader) {
            while ((line = fileBufferedReader.readLine()) != null) {

                if (figureNameMap.getFigureNameMap().containsKey(line)) {
                    var figure = figureFactory.createFigure(line, fileBufferedReader);

                    if (figure != null) {
                        strategy.save(figure);
                        log.info("Фигура сохранена {}", figure.getName());
                    }
                }

            }
        } catch (IOException e) {
            log.error("Ошибка сохранения фигуры {} " ,e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
