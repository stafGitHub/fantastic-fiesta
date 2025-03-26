package ru_shift.dto.figure;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FigureNameMap {
    private final Map<String,String> figureNameMap = new HashMap<>();

    public void addFigure(Class<? extends AbstractFigure> figureClass) {
        figureNameMap.put(figureClass.getSimpleName().toUpperCase(), figureClass.getSimpleName().toUpperCase());
        log.debug("Имя фигуры добавлено в {} -- {} ",figureNameMap , figureClass.getSimpleName().toUpperCase());
    }

    public Map<String, String> getFigureNameMap() {
        return Collections.unmodifiableMap(figureNameMap);
    }
}
