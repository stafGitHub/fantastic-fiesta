package ru_shift.dto.figure;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru_shift.dto.figure.specific.figure.Triangle;
import ru_shift.utils.UtilsArgumentParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Getter
public abstract class AbstractFigure implements Figure {
    private final String name = this.getClass().getSimpleName().toUpperCase();


    protected static double[] readParamsFigure(BufferedReader reader,
                                               int elementSize, String name) {
        try {
            var paramsFigure = UtilsArgumentParser.parseArguments(reader);
            log.info("Аргументы фигуры прочитаны {} - {}", name, paramsFigure);

            var paramsParseDouble = Arrays.stream(paramsFigure)
                    .mapToDouble(UtilsArgumentParser::parseDouble)
                    .toArray();

            boolean checkCorrectParseArgument = Arrays.stream(paramsParseDouble)
                    .anyMatch(d -> Double.doubleToLongBits(d) == Double.doubleToLongBits(-1));

            if (paramsParseDouble.length != elementSize || checkCorrectParseArgument) {
                log.error("Аргументы фигуры {} некорректны", name);

                return null;
            }else {
                return paramsParseDouble;
            }

        }catch(IOException e) {
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