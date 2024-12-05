package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * <a href="https://adventofcode.com/2024/day/3">2024 Day 03</a>
 */
public class Day03 extends Year2024 {
    private static final String MULTIPLICATION_REGEX = "mul\\(\\d{1,3},\\d{1,3}\\)";

    private static final String ENABLED_MULTIPLICATION_REGEX = MULTIPLICATION_REGEX + "|do\\(\\)|don't\\(\\)";

    private static final String ENABLE_MULTIPLY = "do()";
    private static final String DISABLE_MULTIPLY = "don't()";

    public static void main(final String[] args) {
        new Day03().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> memory = getPuzzleInputForDay("03");

        calculateTotalOfMultiplications(memory);
        calculateTotalOfEnabledMultiplications(memory);
    }

    private void calculateTotalOfMultiplications(final List<String> memory) {
        final AtomicInteger total = new AtomicInteger();
        for (String instruction : memory) {
            final Matcher matcher = compile(MULTIPLICATION_REGEX).matcher(instruction);
            while (matcher.find()) {
                processMultiplication(matcher.group(0), total);
            }
        }
        log("Total Sum of Multiplications: {}", total.get());
    }

    private void calculateTotalOfEnabledMultiplications(final List<String> memory) {
        final AtomicInteger total = new AtomicInteger();
        final AtomicBoolean enabledMultiplication = new AtomicBoolean(true);

        for (String instruction : memory) {
            final Matcher matcher = compile(ENABLED_MULTIPLICATION_REGEX).matcher(instruction);
            while (matcher.find()) {
                final String statement = matcher.group(0);
                if (ENABLE_MULTIPLY.equalsIgnoreCase(statement)) {
                    enabledMultiplication.set(true);
                } else if (DISABLE_MULTIPLY.equalsIgnoreCase(statement)) {
                    enabledMultiplication.set(false);
                } else if (enabledMultiplication.get()) {
                    processMultiplication(statement, total);
                }
            }
        }
        log("Total Sum of Enabled Multiplications: {}", total.get());
    }

    private void processMultiplication(final String statement, final AtomicInteger total) {
        final String[] numbers = statement.replace("mul(", "").replace(")", "").replace("do(", "").replace("don\\'t(", "").split(",");
        total.getAndAdd(Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]));
    }
}
