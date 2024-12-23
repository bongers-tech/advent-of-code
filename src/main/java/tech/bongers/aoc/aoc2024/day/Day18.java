package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.*;


/**
 * <a href="https://adventofcode.com/2024/day/18">2024 Day 18</a>
 */
public class Day18 extends Year2024 {

    public static void main(final String[] args) {
        new Day18().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> bytePositions = getPuzzleInputForDay("18");
        log("Number of bytes: {}", bytePositions.size());
        
        final String[][] grid = createGridWithByteCorruptions(1024, bytePositions);
        log("Number of steps: {}", calculateMinimumNumberOfSteps(grid));
        
        for (int i = 1024; i <= bytePositions.size(); i++) {
            try {
                final String[][] newGrid = createGridWithByteCorruptions(i, bytePositions);
                calculateMinimumNumberOfSteps(newGrid);
            } catch (final Exception e) {
                log("Byte position that bricks the grid: {}", bytePositions.get(i - 1));
                break;
            }
        }
    }

    private String[][] createGridWithByteCorruptions(final int maxBytes, final List<String> bytePositions) {
        final String[][] grid = new String[71][71];
        for (int y = 0; y <= 70; y++) {
            for (int x = 0; x <= 70; x++) {
                grid[x][y] = ".";
            }
        }
        bytePositions
                .stream()
                .limit(maxBytes)
                .forEach(position -> {
                    final String[] coordinate = position.split(",");
                    grid[Integer.parseInt(coordinate[0])][Integer.parseInt(coordinate[1])] = "#";
                });
        return grid;
    }

    private Integer calculateMinimumNumberOfSteps(final String[][] grid) {
        // (left, down, right, up)
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        final Set<String> visited = new HashSet<>();

        final Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, 0});
        visited.add("0,0");

        while (!queue.isEmpty()) {
            final int[] current = queue.poll();
            final int x = current[0];
            final int y = current[1];
            final int steps = current[2];

            if (x == 70 && y == 70) {
                return steps;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX <= 70 && newY >= 0 && newY <= 70
                        && grid[newY][newX].equals(".")
                        && !visited.contains(newX + "," + newY)) {

                    queue.add(new int[]{newX, newY, steps + 1});
                    visited.add(newX + "," + newY);
                }
            }
        }

        throw new IllegalStateException("No path found");
    }
}
