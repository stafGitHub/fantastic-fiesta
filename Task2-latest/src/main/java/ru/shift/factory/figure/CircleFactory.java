package ru.shift.factory.figure;

import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.NameOfTheFigure;
import ru.shift.figures.specific.figures.Circle;
import ru.shift.utils.ArgumentsParsing;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class CircleFactory implements FigureFactory<Circle> {

    @Override
    public NameOfTheFigure getNameOfTheFigure() {
        return NameOfTheFigure.CIRCLE;
    }

    @Override
    public Circle read(BufferedReader reader) throws IOException {
        log.info("Создание фигуры {}" ,getNameOfTheFigure());
        try {
            return new Circle(ArgumentsParsing.parseDouble(reader.readLine()));
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка создания {} - {}", CircleFactory.class.getSimpleName(), e.getMessage());
            throw e;
        }
    }
}
