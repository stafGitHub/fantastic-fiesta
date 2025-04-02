package ru.shift;

import lombok.extern.slf4j.Slf4j;
import ru.shift.args.ApplicationArgs;
import ru.shift.args.Parser;
import ru.shift.factory.figure.FigureFactory;
import ru.shift.factory.output.WriterFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // парсинг аргументов программы
        var applicationArgs = Parser.parseApplicationArgs(args);

        // чтение файла
        try (var fileBr = readComputerFile(applicationArgs)) {

            // Создание фигуры
            var abstractFigure = FigureFactory.create(fileBr);

            // Запись
            var figureWriter = WriterFactory.create(applicationArgs);
            figureWriter.write(abstractFigure, applicationArgs);

        } catch (IOException | IllegalArgumentException e) {
            log.warn(e.getMessage());
        }


        log.info("Программа закончила выполнение");
    }

    private static BufferedReader readComputerFile(ApplicationArgs filePath) throws FileNotFoundException {
        var bufferedReader = new BufferedReader(new FileReader(filePath.getFileReadPath().toFile()));
        log.info("Чтение файла {}", filePath.getFileReadPath().toAbsolutePath());
        return bufferedReader;
    }
}