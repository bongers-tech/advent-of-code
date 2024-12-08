package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.lang.Long.parseLong;
import static java.util.function.Predicate.not;

/**
 * <a href="https://adventofcode.com/2024/day/7">2024 Day 07</a>
 */
public class Day07 extends Year2024 {

    public static void main(final String[] args) {
        new Day07().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> calibrations = getPuzzleInputForDay("07");

        final Map<Long, List<Long>> equations = mapCalibrationsToEquations(calibrations);
        final List<Long> correctTestValues = filterCorrectTestValues(equations, getEquationPredicate());
        final List<Long> correctTestValuesWithConcat = filterCorrectTestValues(equations, getEquationWithConcatPredicate());
        
        log("Total calibration result: {}", correctTestValues.stream().mapToLong(Long::longValue).sum());
        log("Total calibration result with concat: {}", correctTestValuesWithConcat.stream().mapToLong(Long::longValue).sum());
    }

    private List<Long> filterCorrectTestValues(final Map<Long, List<Long>> equations, final Predicate<Map.Entry<Long, List<Long>>> predicate) {
        return equations
                .entrySet()
                .stream()
                .filter(predicate)
                .map(Map.Entry::getKey)
                .toList();
    }

    private boolean checkEquation(final Long testValue, final List<Long> equation) {
        return calculateWithDifferentOperators(new AtomicInteger(0), equation.getFirst(), testValue, equation, false);
    }

    private boolean checkEquationWithConcat(final Long testValue, final List<Long> equation) {
        return calculateWithDifferentOperators(new AtomicInteger(0), equation.getFirst(), testValue, equation, true);
    }

    private boolean calculateWithDifferentOperators(final AtomicInteger iteration, final Long currentValue, final Long testValue, final List<Long> equation, boolean includeConcat) {
        iteration.getAndIncrement();
        if (iteration.get() >= equation.size()) {
            return currentValue.equals(testValue);
        }

        final Long nextValue = equation.get(iteration.get());
        boolean isCorrectEquation = calculateWithDifferentOperators(iteration, currentValue + nextValue, testValue, equation, includeConcat)
                || calculateWithDifferentOperators(iteration, currentValue * nextValue, testValue, equation, includeConcat);

        if (includeConcat) {
            final Long concatValue = parseLong(currentValue + "" + nextValue);
            isCorrectEquation = isCorrectEquation || calculateWithDifferentOperators(iteration, concatValue, testValue, equation, true);
        }

        return isCorrectEquation;
    }

    private Map<Long, List<Long>> mapCalibrationsToEquations(final List<String> calibrations) {
        final Map<Long, List<Long>> testValues = new HashMap<>();
        for (final String calibration : calibrations) {
            final String[] splitValue = calibration.split(":");
            final List<Long> equationNumbers = Arrays.stream(splitValue[1].split("\\s"))
                    .filter(not(String::isBlank))
                    .map(Long::valueOf)
                    .toList();

            testValues.put(Long.parseLong(splitValue[0]), equationNumbers);
        }
        return testValues;
    }
    
    private Predicate<Map.Entry<Long, List<Long>>> getEquationPredicate() {
        return entry -> checkEquation(entry.getKey(), entry.getValue());
    }

    private Predicate<Map.Entry<Long, List<Long>>> getEquationWithConcatPredicate() {
        return entry -> checkEquationWithConcat(entry.getKey(), entry.getValue());
    }
}
