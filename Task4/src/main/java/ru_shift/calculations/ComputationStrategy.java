package ru_shift.calculations;

import java.math.BigDecimal;

public interface ComputationStrategy {
    BigDecimal compute(long startNumber, long endNumber);
    String getFormula();
}
