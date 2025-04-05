package ru.shift.output;

import ru.shift.args.ApplicationArgs;

import java.io.IOException;

public interface FigureWriter<F> {
    void write(F figure , ApplicationArgs applicationArgs) throws IOException;
}
