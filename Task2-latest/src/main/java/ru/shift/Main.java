package ru.shift;

import lombok.extern.slf4j.Slf4j;
import ru.shift.args.NameConsoleArgs;
import ru.shift.args.Parser;
import ru.shift.factory.figure.FigureFactory;
import ru.shift.file.SystemFileReader;
import ru.shift.factory.output.WriterFactory;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        // парсинг аргументов программы
        var applicationArgsMap = Parser.parseApplicationArgs(args);
        var systemFileReader = new SystemFileReader();

        // чтение файла
        var fileBr = systemFileReader.readComputerFile(applicationArgsMap.get(NameConsoleArgs.r.name()));

        // Создание фигуры
        var abstractFigure = FigureFactory.create(fileBr);

        // Запись
        var figureWriter = WriterFactory.create(applicationArgsMap);
        figureWriter.write(abstractFigure,applicationArgsMap);

        log.info("Программа закончила выполнение");
    }
}