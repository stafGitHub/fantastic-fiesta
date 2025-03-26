package ru_shift.repository.conservation.strategy.specific.strategy;

import ru_shift.dto.figure.AbstractFigure;
import ru_shift.repository.conservation.strategy.SaveStrategy;

public class ConsoleSaveStrategy implements SaveStrategy<AbstractFigure> {

    @Override
    public void save(AbstractFigure figure) {
        System.out.println(figure.getDescription());
    }

}
