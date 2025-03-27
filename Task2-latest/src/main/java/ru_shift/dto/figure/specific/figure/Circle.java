package ru_shift.dto.figure.specific.figure;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.AbstractFigure;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class Circle extends AbstractFigure {
    private final double radius;

    public Circle(double radius ) {
        super();
        this.radius = radius;
    }

    public static AbstractFigure readFile(BufferedReader reader) throws IllegalArgumentException {
        try {
            var parseParamsFigure = readParamsFigure(reader, 1, Circle.class.getSimpleName().toUpperCase());
            return new Circle(parseParamsFigure[0]);
        }catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            throw new IllegalArgumentException("Параметры некорректны - " + Circle.class.getSimpleName().toUpperCase());
        }
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
