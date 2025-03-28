package ru.shift.factory.figure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.figures.AbstractFigure;
import ru.shift.figures.NameOfTheFigure;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface FigureFactory<T extends AbstractFigure> {
    Logger log = LoggerFactory.getLogger(FigureFactory.class);
    NameOfTheFigure getNameOfTheFigure();

    T read(BufferedReader reader) throws IOException;

    Map<NameOfTheFigure, FigureFactory<?>> factures = ServiceLoader.load(FigureFactory.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(Collectors.toMap(FigureFactory::getNameOfTheFigure, f -> (FigureFactory<?>) f));

    static AbstractFigure create(BufferedReader reader) throws IOException {
        NameOfTheFigure figureName;
        try {
            figureName = NameOfTheFigure.valueOf(reader.readLine().toUpperCase());
        }
        catch (IllegalArgumentException e){
            log.warn("Ошибка чтения фигуры из файл - {}" , e.getMessage());
            throw e;
        }

        try (reader) {
            var factory = factures.get(figureName);
            AbstractFigure figure = factory.read(reader);
            log.info("Фигура создана - {}", figureName);
            return figure;
        }
    }

}
