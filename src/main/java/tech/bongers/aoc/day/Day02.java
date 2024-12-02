package tech.bongers.aoc.day;

import tech.bongers.aoc.util.FileReaderUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static void main(final String[] args) {
        final List<String> reports = FileReaderUtil.getContentAsList("day02.txt");

        calculateTotalSafeReports(reports);
        calculateTotalSafeReportsWithDampener(reports);
    }

    private static void calculateTotalSafeReports(final List<String> reports) {
        final List<String> safeReports = reports
                .stream()
                .filter(Day02::levelDifferencesAreAtLeastOneAndAtMostThreeForReport)
                .filter(Day02::levelAllIncreasingOrDecreasingForReport)
                .toList();

        System.out.println("Safe reports: " + safeReports.size());
    }

    private static void calculateTotalSafeReportsWithDampener(final List<String> reports) {
        final List<String> safeReports = reports
                .stream()
                .filter(Day02::levelDifferencesAreAtLeastOneAndAtMostThreeForReport)
                .filter(Day02::levelAllIncreasingOrDecreasingForReport)
                .toList();

        final List<String> unsafeReports = new ArrayList<>(reports);
        unsafeReports.removeAll(safeReports);

        final List<String> safeReportsWithDampener = unsafeReports
                .stream()
                .filter(Day02::applyDampenerToUnsafeReport)
                .toList();

        System.out.println("Safe reports with dampener: " + (safeReports.size() + safeReportsWithDampener.size()));
    }

    private static boolean levelDifferencesAreAtLeastOneAndAtMostThreeForReport(final String report) {
        final String[] levels = report.split("\\s+");
        return levelDifferencesAreAtLeastOneAndAtMostThree(levels);
    }

    private static boolean levelAllIncreasingOrDecreasingForReport(final String report) {
        final String[] levels = report.split("\\s+");
        return levelAllIncreasingOrDecreasing(levels);
    }

    private static boolean applyDampenerToUnsafeReport(final String report) {
        final String[] levels = report.split("\\s+");
        for (int i = 0; i < levels.length; i++) {
            final String[] dampenedLevels = getDampenedLevels(levels, i);
            if (levelDifferencesAreAtLeastOneAndAtMostThree(dampenedLevels) && levelAllIncreasingOrDecreasing(dampenedLevels)) {
                return true;
            }
        }
        return false;
    }

    private static boolean levelDifferencesAreAtLeastOneAndAtMostThree(final String[] levels) {
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

    private static boolean levelAllIncreasingOrDecreasing(final String[] levels) {
        final Integer[] parsedLevels = Arrays.stream(levels)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return isAllIncreasing(parsedLevels) || isAllDecreasing(parsedLevels);
    }

    private static boolean isAllIncreasing(final Integer[] levels) {
        for (int i = 1; i < levels.length; i++) {
            if (levels[i] <= levels[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAllDecreasing(final Integer[] levels) {
        for (int i = 1; i < levels.length; i++) {
            if (levels[i] >= levels[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    private static String[] getDampenedLevels(final String[] levels, int indexToRemove) {
        final List<String> dampenedLevels = new ArrayList<>(Arrays.asList(levels));
        dampenedLevels.remove(indexToRemove);
        return dampenedLevels.toArray(String[]::new);
    }
}

