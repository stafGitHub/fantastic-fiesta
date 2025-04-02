package ru.shift.factory.figure;

import ru.shift.figures.NameOfTheFigure;
import ru.shift.figures.specific.figures.Triangle;
import ru.shift.utils.ArgumentsParsing;

import java.io.BufferedReader;
import java.io.IOException;

public class TriangleFactory implements FigureFactory<Triangle> {
    private final static int ARGUMENTS_NECESSARY_FOR_THE_FIGURE = 3;
    @Override
    public NameOfTheFigure getNameOfTheFigure() {
        return NameOfTheFigure.TRIANGLE;
    }

    @Override
    public Triangle read(BufferedReader reader) throws IOException {
        log.info("Создание фигуры {}" ,getNameOfTheFigure());
        try {
            var parseArguments = ArgumentsParsing.parseArguments(reader, ARGUMENTS_NECESSARY_FOR_THE_FIGURE);
            var parseArgumentsDouble = new Double[parseArguments.length];
            for (int i = 0; i < parseArguments.length; i++) {
                parseArgumentsDouble[i] = ArgumentsParsing.parseDouble(parseArguments[i]);
            }
            return new Triangle(parseArgumentsDouble[0], parseArgumentsDouble[1], parseArgumentsDouble[2]);
        }catch (IllegalArgumentException e){
            log.warn("Ошибка создания {} - {}",TriangleFactory.class.getSimpleName(), e.getMessage());
            throw e;
        }


    }
}
