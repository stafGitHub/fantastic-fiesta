package ru_shift.dto.figure;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru_shift.utils.UtilsArgumentParser;

import java.io.BufferedReader;
import java.io.IOException;


@Slf4j
@Getter
public abstract class AbstractFigure implements Figure {
    private final String name = this.getClass().getSimpleName().toUpperCase();


    protected static double[] readParamsFigure(BufferedReader reader,
                                               int elementSize, String name) throws IllegalArgumentException {
        try {
            var paramsFigure = UtilsArgumentParser.parseArguments(reader);
            log.info("Аргументы фигуры прочитаны {} - {}", name, paramsFigure);
            var paramsParseDouble = new double[paramsFigure.length];

            for (int i = 0; i < paramsFigure.length; i++) {
                try {
                    paramsParseDouble[i] = UtilsArgumentParser.parseDouble(paramsFigure[i]);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Ошибка парсинга аргументов");
                }
            }

            if (paramsParseDouble.length != elementSize) {
                log.warn("Аргументы фигуры {} некорректны", name);

                throw new IllegalArgumentException("Несоответствие количества аргументов , для создания фигуры: "+ paramsParseDouble.length + " --> " + elementSize);
            } else {
                return paramsParseDouble;
            }

        } catch (IOException e) {
            log.error("Ошибка чтения аргументов фигуры {}", name);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDescription() {
        return String.format("""
                Тип фигуры: %s
                Площадь: %.2f кв. мм
                Периметр: %.2f мм
                """, name, calculateArea(), calculatePerimeter());
    }
}