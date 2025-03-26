package ru_shift;

import ru_shift.args.ApplicationArgumentParser;
import ru_shift.dto.ApplicationData;
import ru_shift.dto.figure.FigureNameMap;
import ru_shift.dto.figure.specific.figure.Circle;
import ru_shift.dto.figure.specific.figure.Rectangle;
import ru_shift.dto.figure.specific.figure.Triangle;
import ru_shift.file.SystemFileReader;
import ru_shift.repository.application.args.repo.ApplicationArgsRepoController;
import ru_shift.repository.application.args.repo.operations.ReadFilePathOperation;
import ru_shift.repository.application.args.repo.operations.SaveFilePathOperation;
import ru_shift.repository.conservation.strategy.SaveStrategyController;
import ru_shift.repository.fabric.FigureFactory;
import ru_shift.repository.fabric.SaveStrategyFactory;

public class Main {
    public static void main(String[] args) {
        // Создание команд для cоздания dto
        var applicationArgsRepoController = new ApplicationArgsRepoController();
        var readFilePathOperation = new ReadFilePathOperation();
        var saveFilePathOperation = new SaveFilePathOperation();
        applicationArgsRepoController.addOperation(readFilePathOperation, saveFilePathOperation);

        // Создание map с именами фигур
        var figureNameMap = new FigureNameMap();
        figureNameMap.addFigure(Rectangle.class);
        figureNameMap.addFigure(Triangle.class);
        figureNameMap.addFigure(Circle.class);

        //Создание объектов для работы с файлом
        var figureFactory = new FigureFactory();
        var applicationArgumentParser = new ApplicationArgumentParser();
        var fileReader = new SystemFileReader();

        // Работа с аргументами программы
        var parseArgsMap = applicationArgumentParser.argumentConfigure(args);

        // Создание Dto на основе аргументов
        var applicationData = applicationArgsRepoController.operationsExecute(parseArgsMap, ApplicationData.builder());

        // Работа со стратегией сохранения
        var saveStrategyController = new SaveStrategyController(
                applicationData,
                new SaveStrategyFactory(applicationData),
                figureFactory
        );


        // Работа с файлом
        var fileBufferedReader = fileReader.readComputerFile(applicationData.getReadFilePath());

        // Сохранение
        saveStrategyController.processFigureFromSaveStrategy(fileBufferedReader , figureNameMap);

    }
}