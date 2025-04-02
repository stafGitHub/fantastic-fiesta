package ru.shift.figures.specific.figures;

import lombok.extern.slf4j.Slf4j;
import ru.shift.figures.AbstractFigure;
import ru.shift.figures.NameOfTheFigure;

@Slf4j
public class Rectangle extends AbstractFigure {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        super(NameOfTheFigure.RECTANGLE.name());
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }

    @Override
    public String getDescription() {
        double diagonal = Math.sqrt(length * length + width * width);
        return String.format("""
                %s
                Длина: %.2f мм
                Ширина: %.2f мм
                Диагональ: %.2f мм
                """, super.getDescription(), length, width, diagonal);
    }
}
