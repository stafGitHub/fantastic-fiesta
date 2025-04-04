package ru.shift.factory.figure;

import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.NameOfTheFigure;
import ru.shift.figures.specific.figures.Rectangle;
import ru.shift.utils.ArgumentsParsing;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class RectangleFactory implements FigureFactory<Rectangle> {
    private final static int ARGUMENTS_NECESSARY_FOR_THE_FIGURE = 2;

    @Override
    public NameOfTheFigure getNameOfTheFigure() {
        return NameOfTheFigure.RECTANGLE;
    }

    @Override
    public Rectangle read(BufferedReader reader) throws IOException {
        log.info("Создание фигуры {}", getNameOfTheFigure());
        var figureParams = reader.readLine();
        try {
            var parseArguments = ArgumentsParsing.parseArguments(figureParams, ARGUMENTS_NECESSARY_FOR_THE_FIGURE);
            var parseArgumentsDouble = new Double[parseArguments.length];

            for (int i = 0; i < parseArguments.length; i++) {
                parseArgumentsDouble[i] = ArgumentsParsing.parseDouble(parseArguments[i]);
            }

            return new Rectangle(parseArgumentsDouble[0], parseArgumentsDouble[1]);
        } catch (IllegalArgumentException e) {
            log.warn("Ошибка создания {}", getNameOfTheFigure());
            throw new FigureException(getNameOfTheFigure(), figureParams, e);
        }


    }
}
