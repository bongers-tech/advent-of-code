package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="https://adventofcode.com/2015/day/3">2015 Day 03</a>
 */
public class Day03 extends Year2015 {

    public static void main(final String[] args) {
        new Day03().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> directions = getPuzzleInputForDay("03");

        final char[] entryChars = directions.getFirst().toCharArray();

        final List<String> entries = new ArrayList<>();
        final List<String> santaEntries = new ArrayList<>();
        final List<String> robotEntries = new ArrayList<>();

        for (int i = 0; i < entryChars.length; i++) {
            entries.add(String.valueOf(entryChars[i]));
            if (i % 2 == 0) {
                santaEntries.add(String.valueOf(entryChars[i]));
            } else {
                robotEntries.add(String.valueOf(entryChars[i]));
            }
        }

        log("Total visited houses: {}", calculateTotalHouseVisits(entries, new HashMap<>(Map.of("x0y0", 1))).size());

        final Map<String, Integer> santaVisits = calculateTotalHouseVisits(santaEntries, new HashMap<>(Map.of("x0y0", 1)));
        final Map<String, Integer> combinedHouseVisits = calculateTotalHouseVisits(robotEntries, santaVisits);
        log("Total combined visited houses: {}", combinedHouseVisits.size());
    }

    private Map<String, Integer> calculateTotalHouseVisits(final List<String> entries, final Map<String, Integer> houseVisits) {
        final AtomicInteger x = new AtomicInteger(0);
        final AtomicInteger y = new AtomicInteger(0);

        for (final String direction : entries) {
            String currentCoordinate = switch (direction) {
                case "^" -> "x" + x.get() + "y" + y.incrementAndGet();
                case ">" -> "x" + x.incrementAndGet() + "y" + y.get();
                case "v" -> "x" + x.get() + "y" + y.decrementAndGet();
                case "<" -> "x" + x.decrementAndGet() + "y" + y.get();
                default -> "";
            };
            houseVisits.putIfAbsent(currentCoordinate, 0);
            houseVisits.computeIfPresent(currentCoordinate, (key, value) -> value + 1);
        }

        return houseVisits;
    }
}
