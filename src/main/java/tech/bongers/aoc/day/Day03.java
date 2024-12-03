package tech.bongers.aoc.day;

import tech.bongers.aoc.util.FileReaderUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * <a href="https://adventofcode.com/2024/day/3">2024 Day 03</a>
 */
public class Day03 {

    private static final String MULTIPLICATION_REGEX = "mul\\(\\d{1,3},\\d{1,3}\\)";
    private static final String ENABLED_MULTIPLICATION_REGEX = MULTIPLICATION_REGEX + "|do\\(\\)|don't\\(\\)";

    private static final String ENABLE_MULTIPLY = "do()";
    private static final String DISABLE_MULTIPLY = "don't()";

    public static void main(final String[] args) {
        final List<String> memory = FileReaderUtil.getContentAsList("day03.txt");

        calculateTotalOfMultiplications(memory);
        calculateTotalOfEnabledMultiplications(memory);
    }

    private static void calculateTotalOfMultiplications(final List<String> memory) {
        final AtomicInteger total = new AtomicInteger();
        for (String instruction : memory) {
            final Matcher matcher = compile(MULTIPLICATION_REGEX).matcher(instruction);
            while (matcher.find()) {
                processMultiplication(matcher.group(0), total);
            }
        }
        System.out.println("Total Sum of Multiplications: " + total.get());
    }

    private static void calculateTotalOfEnabledMultiplications(final List<String> memory) {
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
        System.out.println("Total Sum of Enabled Multiplications: " + total.get());
    }

    private static void processMultiplication(final String statement, final AtomicInteger total) {
        final String[] numbers = statement.replace("mul(", "").replace(")", "").replace("do(", "").replace("don\\'t(", "").split(",");
        total.getAndAdd(Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]));
    }
}
