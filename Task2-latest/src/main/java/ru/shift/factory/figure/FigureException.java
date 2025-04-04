package ru.shift.factory.figure;

import ru.shift.figures.NameOfTheFigure;

public class FigureException extends IllegalArgumentException {
    private static final String ERROR_TEMPLATE = "Тип фигуры : %s | Параметры : %s | Ошибка : %s";

    public FigureException(NameOfTheFigure figureType,
                           String figureParams,
                           Exception message) {
        super(String.format(ERROR_TEMPLATE, figureType, figureParams, message));
    }
}
