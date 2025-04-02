package ru.shift.output.specific.writer;

import ru.shift.args.ApplicationArgs;
import ru.shift.figures.AbstractFigure;
import ru.shift.output.FigureWriter;

public class ConsoleFigureWriter implements FigureWriter<AbstractFigure> {

    @Override
    public void write(AbstractFigure figure , ApplicationArgs applicationArgs) {
        System.out.println(figure.getDescription());
    }

}
