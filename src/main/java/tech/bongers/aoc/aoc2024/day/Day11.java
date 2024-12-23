package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://adventofcode.com/2024/day/11">2024 Day 11</a>
 */
public class Day11 extends Year2024 {
    
    public static void main(final String[] args) {
        new Day11().doPuzzle();
    }
    
    @Override
    public void doPuzzle() {
        final String input = "0 5601550 3914 852 50706 68 6 645371";

        final List<Long> stones = Arrays.stream(input.split(" ")).map(Long::parseLong).toList();
        final Map<Long, Long> stoneCounts = new HashMap<>();
        for (Long stone : stones) {
            stoneCounts.put(stone, stoneCounts.getOrDefault(stone, 0L) + 1);
        }
        
        log("Total stones for 25 blinks: {}",countStones(stoneCounts, 25));
        log("Total stones for 75 blinks: {}",countStones(stoneCounts, 75));
    }

    private long countStones(Map<Long, Long> stoneCounts, int blinks) {
        for (int i = 0; i < blinks; i++) {
            final Map<Long, Long> nextCounts = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stoneCounts.entrySet()) {
                final List<Long> newStones = determineNewStones(entry.getKey());
                for (Long stone : newStones) {
                    nextCounts.put(stone, nextCounts.getOrDefault(stone, 0L) + entry.getValue());
                }
            }
            stoneCounts = nextCounts;
        }
        return stoneCounts.values().stream().mapToLong(l -> l).sum();
    }

    private List<Long> determineNewStones(Long stone) {
        if (stone == 0) {
            return List.of(1L);
        } else if (String.valueOf(stone).length() % 2 == 0) {
           final  String toSplit = String.valueOf(stone);
            final Long left = Long.parseLong(toSplit.substring(0, toSplit.length() / 2));
            final Long right = Long.parseLong(toSplit.substring(toSplit.length() / 2));
            return List.of(left, right);
        } else {
            return List.of(stone * 2024);
        }
    }
}
