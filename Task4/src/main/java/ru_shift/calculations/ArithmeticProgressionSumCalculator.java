package ru_shift.calculations;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class ArithmeticProgressionSumCalculator implements ComputationStrategy {
    private static final String FORMULA = "sum(1 + 0.1*i) from i=0 to n";

    @Override
    public BigDecimal compute(long startNumber, long endNumber) {
        log.debug("Starting range calculation");
        BigDecimal sum = BigDecimal.ZERO;

        for (long i = startNumber; i <= endNumber; i++) {
            BigDecimal term = BigDecimal.ONE.add(BigDecimal.valueOf(i).multiply(BigDecimal.valueOf(0.1)));

            sum = sum.add(term);
        }

        log.debug("Result of range calculation [{}-{}]: {}",
                startNumber,
                endNumber,
                sum.setScale(5, RoundingMode.HALF_UP));
        return sum;
    }

    @Override
    public String getFormula() {
        return FORMULA;
    }
}