package ru_shift.dto.figure.specific.figure;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.AbstractFigure;

import java.io.BufferedReader;

@Slf4j
public class Rectangle extends AbstractFigure {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        super();
        this.length = length;
        this.width = width;
    }

    public static AbstractFigure readFile(BufferedReader reader) {
        var parseParamsFigure = readParamsFigure(reader, 2, Rectangle.class.getSimpleName().toUpperCase());

        if (parseParamsFigure != null) {
            return new Rectangle(
              parseParamsFigure[0],
              parseParamsFigure[1]
            );
        }else {
            return null;
        }
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
