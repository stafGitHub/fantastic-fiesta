package ru_shift.dto.figure.specific.figure;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.AbstractFigure;

import java.io.BufferedReader;

@Slf4j
public class Circle extends AbstractFigure {
    private final double radius;

    public Circle(double radius ) {
        super();
        this.radius = radius;
    }

    public static AbstractFigure readFile(BufferedReader reader) {
        var parseParamsFigure = readParamsFigure(reader,1, Circle.class.getSimpleName().toUpperCase());

        if (parseParamsFigure != null) {
            return new Circle(parseParamsFigure[0]);
        }else {
            return null;
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
