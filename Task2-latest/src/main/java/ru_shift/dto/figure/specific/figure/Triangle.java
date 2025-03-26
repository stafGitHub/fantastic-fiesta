package ru_shift.dto.figure.specific.figure;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.AbstractFigure;

import java.io.BufferedReader;

@Slf4j
public class Triangle extends AbstractFigure {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public static AbstractFigure readFile(BufferedReader reader) {
        var paramsParseDouble = readParamsFigure(reader, 3 , Triangle.class.getSimpleName().toUpperCase());
        if (paramsParseDouble != null) {
            return new Triangle(
                    paramsParseDouble[0],
                    paramsParseDouble[1],
                    paramsParseDouble[2]
            );
        }else {
            return null;
        }

    }

    @Override
    public double calculateArea() {
        double p = calculatePerimeter() / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String getDescription() {
        double angleA = calculateAngle(sideB, sideC, sideA);
        double angleB = calculateAngle(sideA, sideC, sideB);
        double angleC = calculateAngle(sideA, sideB, sideC);

        return String.format("""
                %s
                Сторона A: %.2f мм, Противолежащий угол: %.2f°
                Сторона B: %.2f мм, Противолежащий угол: %.2f°
                Сторона C: %.2f мм, Противолежащий угол: %.2f°
                """, super.getDescription(), sideA, angleA, sideB, angleB, sideC, angleC);
    }

    private double calculateAngle(double b, double c, double a) {
        double cosA = (b * b + c * c - a * a) / (2 * b * c);
        return Math.toDegrees(Math.acos(cosA));
    }
}
