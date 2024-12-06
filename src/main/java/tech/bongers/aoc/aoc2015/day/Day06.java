package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://adventofcode.com/2015/day/6>2015 Day 06</a>
 */
public class Day06 extends Year2015 {

    private static final int GRID_SIZE = 1_000;
    private static final String TURN_ON = "turn on";
    private static final String TURN_OFF = "turn off";
    private static final String TOGGLE = "toggle";

    public static void main(final String[] args) {
        new Day06().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> instructions = getPuzzleInputForDay("06");

        final int[][] lightGrid = createGrid();
        final int[][] brightnessGrid = createGrid();

        performLightInstruction(lightGrid, instructions);
        calculateTotalLightsOn(lightGrid);

        performBrightnessInstruction(brightnessGrid, instructions);
        calculateTotalLightsOn(brightnessGrid);
    }

    private void performLightInstruction(final int[][] grid, final List<String> instructions) {
        instructions.forEach(instruction -> {
            if (instruction.startsWith(TURN_ON)) {
                final Coordinates coordinates = getCoordinates(instruction, TURN_ON);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y] = 1;
                    }
                }
            } else if (instruction.startsWith(TURN_OFF)) {
                final Coordinates coordinates = getCoordinates(instruction, TURN_OFF);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y] = 0;
                    }
                }
            } else if (instruction.startsWith(TOGGLE)) {
                final Coordinates coordinates = getCoordinates(instruction, TOGGLE);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y] = grid[x][y] == 0 ? 1 : 0;
                    }
                }
            }
        });
    }

    private void performBrightnessInstruction(final int[][] grid, final List<String> instructions) {
        instructions.forEach(instruction -> {
            if (instruction.startsWith(TURN_ON)) {
                final Coordinates coordinates = getCoordinates(instruction, TURN_ON);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y]++;
                    }
                }
            } else if (instruction.startsWith(TURN_OFF)) {
                final Coordinates coordinates = getCoordinates(instruction, TURN_OFF);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y]--;
                        if (grid[x][y] < 0) {
                            grid[x][y] = 0;
                        }
                    }
                }
            } else if (instruction.startsWith(TOGGLE)) {
                final Coordinates coordinates = getCoordinates(instruction, TOGGLE);
                for (int x = coordinates.x1(); x <= coordinates.x2(); x++) {
                    for (int y = coordinates.y1(); y <= coordinates.y2(); y++) {
                        grid[x][y] += 2;
                    }
                }
            }
        });
    }

    private void calculateTotalLightsOn(final int[][] grid) {
        int totalLightsOn = 0;
        for (final int[] row : grid) {
            for (final int light : row) {
                totalLightsOn += light;
            }
        }
        log("Total lights on: {}", totalLightsOn);
    }

    private int[][] createGrid() {
        final int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        for (final int[] intArray : grid) {
            Arrays.fill(intArray, 0);
        }
        return grid;
    }

    private static Coordinates getCoordinates(final String instruction, final String action) {
        final String[] coordinates = instruction
                .replace(action, "")
                .replace("through", ",")
                .replace(" ", "")
                .split(",");
        return new Coordinates(
                Integer.parseInt(coordinates[0]),
                Integer.parseInt(coordinates[1]),
                Integer.parseInt(coordinates[2]),
                Integer.parseInt(coordinates[3])
        );
    }

    private record Coordinates(int x1, int y1, int x2, int y2) {}
    
}
