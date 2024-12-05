package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://adventofcode.com/2024/day/2">2024 Day 02</a>
 */
public class Day02 extends Year2024 {

    public static void main(final String[] args) {
        new Day02().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> reports = getPuzzleInputForDay("02");

        calculateTotalSafeReports(reports);
        calculateTotalSafeReportsWithDampener(reports);
    }

    private void calculateTotalSafeReports(final List<String> reports) {
        final List<String> safeReports = reports
                .stream()
                .filter(this::levelDifferencesAreAtLeastOneAndAtMostThreeForReport)
                .filter(this::levelAllIncreasingOrDecreasingForReport)
                .toList();

        log("Safe reports: {}", safeReports.size());
    }

    private void calculateTotalSafeReportsWithDampener(final List<String> reports) {
        final List<String> safeReports = reports
                .stream()
                .filter(this::levelDifferencesAreAtLeastOneAndAtMostThreeForReport)
                .filter(this::levelAllIncreasingOrDecreasingForReport)
                .toList();

        final List<String> unsafeReports = new ArrayList<>(reports);
        unsafeReports.removeAll(safeReports);

        final List<String> safeReportsWithDampener = unsafeReports
                .stream()
                .filter(this::applyDampenerToUnsafeReport)
                .toList();

        log("Safe reports with dampener: {}", safeReports.size() + safeReportsWithDampener.size());
    }

    private boolean levelDifferencesAreAtLeastOneAndAtMostThreeForReport(final String report) {
        final String[] levels = report.split("\\s+");
        return levelDifferencesAreAtLeastOneAndAtMostThree(levels);
    }

    private boolean levelAllIncreasingOrDecreasingForReport(final String report) {
        final String[] levels = report.split("\\s+");
        return levelAllIncreasingOrDecreasing(levels);
    }

    private boolean applyDampenerToUnsafeReport(final String report) {
        final String[] levels = report.split("\\s+");
        for (int i = 0; i < levels.length; i++) {
            final String[] dampenedLevels = getDampenedLevels(levels, i);
            if (levelDifferencesAreAtLeastOneAndAtMostThree(dampenedLevels) && levelAllIncreasingOrDecreasing(dampenedLevels)) {
                return true;
            }
        }
        return false;
    }

    private boolean levelDifferencesAreAtLeastOneAndAtMostThree(final String[] levels) {
        for (int i = 0; i < levels.length - 1; i++) {
            int currentLevel = Integer.parseInt(levels[i]);
            int nextLevel = Integer.parseInt(levels[i + 1]);

            final int difference = Math.abs(currentLevel - nextLevel);
            if (difference == 0 || difference > 3) {
                return false;
            }
        }
        return true;
    }

    private boolean levelAllIncreasingOrDecreasing(final String[] levels) {
        final Integer[] parsedLevels = Arrays.stream(levels)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return isAllIncreasing(parsedLevels) || isAllDecreasing(parsedLevels);
    }

    private boolean isAllIncreasing(final Integer[] levels) {
        for (int i = 1; i < levels.length; i++) {
            if (levels[i] <= levels[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllDecreasing(final Integer[] levels) {
        for (int i = 1; i < levels.length; i++) {
            if (levels[i] >= levels[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private String[] getDampenedLevels(final String[] levels, int indexToRemove) {
        final List<String> dampenedLevels = new ArrayList<>(Arrays.asList(levels));
        dampenedLevels.remove(indexToRemove);
        return dampenedLevels.toArray(String[]::new);
    }
}

