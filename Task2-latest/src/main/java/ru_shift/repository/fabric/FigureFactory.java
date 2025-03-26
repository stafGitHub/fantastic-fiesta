package ru_shift.repository.fabric;

import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.AbstractFigure;
import ru_shift.dto.figure.specific.figure.Circle;
import ru_shift.dto.figure.specific.figure.Rectangle;
import ru_shift.dto.figure.specific.figure.Triangle;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FigureFactory {
    private final Map<String, AbstractFigure> figures = new HashMap<>();
    public AbstractFigure createFigure(String figureName ,
                                       BufferedReader reader) {
        try {
            return switch (figureName) {
                case "CIRCLE" -> Circle.readFile(reader);
                case "RECTANGLE" -> Rectangle.readFile(reader);
                case "TRIANGLE" ->  Triangle.readFile(reader);
                default -> null;
            };

        }catch (IllegalStateException e){
            log.error("Ошибка обработки фигуры {}", figureName);
            log.error("Подробности {}", e.getMessage());
        }
        return null;
    }
}
