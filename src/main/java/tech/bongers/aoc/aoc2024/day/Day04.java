package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="https://adventofcode.com/2024/day/4">2024 Day 04</a>
 */
public class Day04 extends Year2024 {

    public static void main(final String[] args) {
        new Day04().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> wordPuzzle = getPuzzleInputForDay("04");

        final int numberOfRows = wordPuzzle.size();
        final int numberOfColumns = wordPuzzle.getFirst().split("").length;

        final String[][] puzzleGrid = new String[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            final String[] charsAtRow = wordPuzzle.get(i).split("");
            System.arraycopy(charsAtRow, 0, puzzleGrid[i], 0, charsAtRow.length);
        }

        findTotalOccurrencesOfXmas(puzzleGrid, numberOfRows, numberOfColumns);
        findTotalOccurrencesOfCrossedMas(puzzleGrid, numberOfRows, numberOfColumns);
    }

    private void findTotalOccurrencesOfXmas(final String[][] puzzleGrid, final int numberOfRows, final int numberOfColumns) {
        final AtomicInteger totalOccurrences = new AtomicInteger();

        for (int x = 0; x < numberOfRows; x++) {
            for (int y = 0; y < numberOfColumns; y++) {
                if (puzzleGrid[x][y].equals("X")) {

                    // Left to right
                    if (x + 3 < numberOfRows &&
                            puzzleGrid[x + 1][y].equals("M") &&
                            puzzleGrid[x + 2][y].equals("A") &&
                            puzzleGrid[x + 3][y].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Right to left
                    if (x - 3 >= 0 &&
                            puzzleGrid[x - 1][y].equals("M") &&
                            puzzleGrid[x - 2][y].equals("A") &&
                            puzzleGrid[x - 3][y].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Top to bottom
                    if (y + 3 < numberOfColumns &&
                            puzzleGrid[x][y + 1].equals("M") &&
                            puzzleGrid[x][y + 2].equals("A") &&
                            puzzleGrid[x][y + 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Bottom to top
                    if (y - 3 >= 0 &&
                            puzzleGrid[x][y - 1].equals("M") &&
                            puzzleGrid[x][y - 2].equals("A") &&
                            puzzleGrid[x][y - 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Top left to bottom right
                    if (x + 3 < numberOfRows && y + 3 < numberOfColumns &&
                            puzzleGrid[x + 1][y + 1].equals("M") &&
                            puzzleGrid[x + 2][y + 2].equals("A") &&
                            puzzleGrid[x + 3][y + 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Bottom right to top left 
                    if (x - 3 >= 0 && y - 3 >= 0 &&
                            puzzleGrid[x - 1][y - 1].equals("M") &&
                            puzzleGrid[x - 2][y - 2].equals("A") &&
                            puzzleGrid[x - 3][y - 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Bottom left to top right 
                    if (x + 3 < numberOfRows && y - 3 >= 0 &&
                            puzzleGrid[x + 1][y - 1].equals("M") &&
                            puzzleGrid[x + 2][y - 2].equals("A") &&
                            puzzleGrid[x + 3][y - 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }

                    // Top right to bottom left
                    if (x - 3 >= 0 && y + 3 < numberOfColumns &&
                            puzzleGrid[x - 1][y + 1].equals("M") &&
                            puzzleGrid[x - 2][y + 2].equals("A") &&
                            puzzleGrid[x - 3][y + 3].equals("S")) {
                        totalOccurrences.getAndIncrement();
                    }
                }
            }
        }

        log("Total occurrences of XMAS: {}", totalOccurrences.get());
    }

    private void findTotalOccurrencesOfCrossedMas(final String[][] puzzleGrid, final int numberOfRows, final int numberOfColumns) {
        final AtomicInteger totalOccurrences = new AtomicInteger();

        for (int x = 0; x < numberOfRows; x++) {
            for (int y = 0; y < numberOfColumns; y++) {
                if (puzzleGrid[x][y].equals("A") && x - 1 >= 0 && y - 1 >= 0 && x + 1 < numberOfRows && y + 1 < numberOfColumns) {
                    if (puzzleGrid[x - 1][y - 1].equals("M") && puzzleGrid[x + 1][y + 1].equals("S")) {
                        checkOppositeCorners(puzzleGrid, x, y, totalOccurrences);
                    }
                    if (puzzleGrid[x - 1][y - 1].equals("S") && puzzleGrid[x + 1][y + 1].equals("M")) {
                        checkOppositeCorners(puzzleGrid, x, y, totalOccurrences);
                    }
                }
            }
        }

        log("Total occurrences of Crossed MAS: {}", totalOccurrences.get());
    }

    private void checkOppositeCorners(final String[][] puzzleGrid, final int x, final int y, final AtomicInteger totalOccurrences) {
        if (puzzleGrid[x + 1][y - 1].equals("M") && puzzleGrid[x - 1][y + 1].equals("S")) {
            totalOccurrences.getAndIncrement();
        }
        if (puzzleGrid[x - 1][y + 1].equals("M") && puzzleGrid[x + 1][y - 1].equals("S")) {
            totalOccurrences.getAndIncrement();
        }
    }
}
