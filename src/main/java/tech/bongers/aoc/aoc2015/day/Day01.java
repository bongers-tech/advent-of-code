package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="https://adventofcode.com/2015/day/1">2015 Day 01</a>
 */
public class Day01 extends Year2015 {

    public static void main(final String[] args) {
        new Day01().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> instructions = getPuzzleInputForDay("01");
        calculateFloorDestination(instructions);
    }

    private void calculateFloorDestination(final List<String> instructions) {
        final AtomicInteger floor = new AtomicInteger();
        final AtomicBoolean basementReached = new AtomicBoolean(false);

        instructions.forEach(instruction -> {
            final char[] chars = instruction.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(') {
                    floor.getAndIncrement();
                } else {
                    floor.getAndDecrement();
                }

                if (floor.get() == -1 && !basementReached.get()) {
                    basementReached.set(true);
                    log("Char position first basement entry: {}", i + 1);
                }
            }
        });

        log("Floor destination: {}", floor);
    }
}
