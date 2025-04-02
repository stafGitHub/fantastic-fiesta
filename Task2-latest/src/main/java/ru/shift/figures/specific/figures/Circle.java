package ru.shift.figures.specific.figures;

import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.AbstractFigure;
import ru.shift.figures.NameOfTheFigure;

@Slf4j
public class Circle extends AbstractFigure {
    private final double radius;

    public Circle(double radius) {
        super(NameOfTheFigure.CIRCLE.name());
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String getDescription() {
        return String.format("""
                %s
                Радиус: %.2f мм
                Диаметр: %.2f мм
                """, super.getDescription(), radius, 2 * radius);
    }
}
