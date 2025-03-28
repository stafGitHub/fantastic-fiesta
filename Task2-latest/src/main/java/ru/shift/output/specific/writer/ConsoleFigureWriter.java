package ru.shift.output.specific.writer;

import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;

import java.util.Map;

public class ConsoleFigureWriter implements FigureWriter<AbstractFigure> {

    @Override
    public void write(AbstractFigure figure , Map<String,String> applicationArgs) {
        System.out.println(figure.getDescription());
    }

}
