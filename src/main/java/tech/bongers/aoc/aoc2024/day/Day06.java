package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="https://adventofcode.com/2024/day/6">2024 Day 06</a>
 */
public class Day06 extends Year2024 {

    private static final String NORTH = "^";
    private static final String EAST = ">";
    private static final String SOUTH = "v";
    private static final String WEST = "<";

    private static final String EMPTY_CELL = ".";
    private static final String OBSTACLE = "#";

    public static void main(final String[] args) {
        new Day06().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> scene = getPuzzleInputForDay("06");
        final String[][] grid = scene.stream().map(line -> line.split("")).toArray(String[][]::new);

        final int width = grid[0].length;
        final int height = grid.length;

        determineDistinctPositionsForGuard(cloneGrid(grid), width, height);
        determineLoopCausingPositions(grid, width, height);
    }

    private void determineDistinctPositionsForGuard(final String[][] grid, final int width, final int height) {
        final int[] guardPosition = findInitialGuardPosition(grid, width, height);
        final Set<String> distinctPositions = new HashSet<>();
        distinctPositions.add(guardPosition[1] + "," + guardPosition[0]);

        boolean guardInBoundary = true;
        while (guardInBoundary) {
            final String direction = grid[guardPosition[1]][guardPosition[0]];
            guardInBoundary = moveGuardInDirection(grid, guardPosition, direction, width, height, distinctPositions);
        }

        log("Total distinct positions: {}", distinctPositions.size());
    }

    private void determineLoopCausingPositions(final String[][] grid, final int width, final int height) {
        final Set<String> loopPositions = new HashSet<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                final int[] guardStartPosition = findInitialGuardPosition(grid, width, height);
                if (canPlaceObstruction(x, y, guardStartPosition, grid)) {
                    final String[][] modifiedGrid = cloneGrid(grid);
                    modifiedGrid[y][x] = OBSTACLE;

                    if (doesCauseLoop(modifiedGrid, guardStartPosition, width, height)) {
                        loopPositions.add(y + "," + x);
                    }
                }
            }
        }
        log("Number of loop-causing positions: {}", loopPositions.size());
    }

    private int[] findInitialGuardPosition(final String[][] grid, final int width, final int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].matches("[\\^<>v]")) {
                    return new int[]{x, y};
                }
            }
        }
        throw new IllegalStateException("Guard not found in the grid.");
    }

    private boolean doesCauseLoop(final String[][] grid, final int[] guardPosition, final int width, final int height) {
        final Set<String> visitedPositions = new HashSet<>();
        boolean guardInBoundary = true;

        while (guardInBoundary) {
            final String direction = grid[guardPosition[1]][guardPosition[0]];
            final String positionState = guardPosition[1] + "," + guardPosition[0] + "," + direction;
            if (visitedPositions.contains(positionState)) {
                return true;
            }
            visitedPositions.add(positionState);
            guardInBoundary = moveGuardInDirection(grid, guardPosition, direction, width, height, new HashSet<>());

        }
        return false;
    }

    private boolean moveGuardInDirection(final String[][] grid, final int[] guardPosition, final String direction, final int width, final int height, final Set<String> distinctPositions) {
        int guardX = guardPosition[0];
        int guardY = guardPosition[1];

        switch (direction) {
            case NORTH -> {
                if (guardY - 1 < 0) return false;
                return processMove(grid, guardPosition, distinctPositions, 0, -1, EAST);
            }
            case EAST -> {
                if (guardX + 1 >= width) return false;
                return processMove(grid, guardPosition, distinctPositions, 1, 0, SOUTH);
            }
            case SOUTH -> {
                if (guardY + 1 >= height) return false;
                return processMove(grid, guardPosition, distinctPositions, 0, 1, WEST);
            }
            case WEST -> {
                if (guardX - 1 < 0) return false;
                return processMove(grid, guardPosition, distinctPositions, -1, 0, NORTH);
            }
            default -> throw new IllegalStateException("Unexpected value in grid: " + direction);
        }
    }

    private boolean processMove(final String[][] grid, final int[] guardPosition, final Set<String> distinctPositions, final int dx, final int dy, final String newDirection) {
        final int newX = guardPosition[0] + dx;
        final int newY = guardPosition[1] + dy;

        if (grid[newY][newX].equals(EMPTY_CELL)) {
            moveGuard(grid, guardPosition[0], guardPosition[1], dx, dy);
            guardPosition[0] = newX;
            guardPosition[1] = newY;
            distinctPositions.add(newY + "," + newX);
        } else if (grid[newY][newX].equals(OBSTACLE)) {
            turnGuard(grid, guardPosition[1], guardPosition[0], newDirection);
        }
        return true;
    }

    private void moveGuard(final String[][] grid, final int guardX, final int guardY, final int dx, final int dy) {
        grid[guardY + dy][guardX + dx] = grid[guardY][guardX];
        grid[guardY][guardX] = EMPTY_CELL;
    }

    private void turnGuard(final String[][] grid, final int guardY, final int guardX, final String newDirection) {
        grid[guardY][guardX] = newDirection;
    }

    private boolean canPlaceObstruction(final int x, final int y, final int[] guardStartPosition, final String[][] grid) {
        return !grid[y][x].equals(OBSTACLE) && !(guardStartPosition[0] == x && guardStartPosition[1] == y);
    }

    private String[][] cloneGrid(final String[][] grid) {
        return Arrays.stream(grid).map(String[]::clone).toArray(String[][]::new);
    }
}
