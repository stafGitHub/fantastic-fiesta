package ru.shift.figures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractFigure implements Figure {
    protected final String name;

    @Override
    public String getDescription() {
        return String.format("""
                Тип фигуры: %s
                Площадь: %.2f кв. мм
                Периметр: %.2f мм
                """, name, calculateArea(), calculatePerimeter());
    }
}