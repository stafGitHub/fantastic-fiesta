package ru.shift.output;

import java.util.Map;

public interface FigureWriter<F> {
    void write(F figure , Map<String,String> applicationArgs);
}
