package tech.bongers.aoc.aoc2024.day;

import tech.bongers.aoc.aoc2024.Year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="https://adventofcode.com/2024/day/1">2024 Day 01</a>
 */
public class Day01 extends Year2024 {

    public static void main(final String[] args) {
        new Day01().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> locations = getPuzzleInputForDay("01");
        final List<Integer> firstList = new ArrayList<>();
        final List<Integer> secondList = new ArrayList<>();

        locations.forEach(line -> {
            final String[] splitLine = line.split("\\s+");

            firstList.add(Integer.parseInt(splitLine[0]));
            secondList.add(Integer.parseInt(splitLine[1]));
        });

        calculateTotalDistance(firstList, secondList);
        calculateSimilarityScore(firstList, secondList);
    }

    private void calculateTotalDistance(final List<Integer> firstList, final List<Integer> secondList) {
        Collections.sort(firstList);
        Collections.sort(secondList);

        int totalDifference = 0;

        for (int i = 0; i < firstList.size(); i++) {
            if (firstList.get(i) > secondList.get(i)) {
                totalDifference += firstList.get(i) - secondList.get(i);
            } else {
                totalDifference += secondList.get(i) - firstList.get(i);
            }
        }

        log("Total difference in distance: {}", totalDifference);
    }

    private void calculateSimilarityScore(final List<Integer> firstList, final List<Integer> secondList) {
        final AtomicInteger similarityScore = new AtomicInteger();
        firstList.forEach(value -> similarityScore.addAndGet(value * Collections.frequency(secondList, value)));
        log("Total similarity score: {}", similarityScore.get());
    }
}
