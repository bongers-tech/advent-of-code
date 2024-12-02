package tech.bongers.aoc.day;

import tech.bongers.aoc.util.FileReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  <a href="https://adventofcode.com/2024/day/1">2024 Day 01</a>
 */
public class Day01 {
    
    public static void main(final String[] args) {
        final List<String> locations = FileReaderUtil.getContentAsList("day01.txt");
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

    private static void calculateTotalDistance(final List<Integer> firstList, final List<Integer> secondList) {
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
        
        System.out.println("Total difference in distance: " + totalDifference);
    }

    private static void calculateSimilarityScore(final List<Integer> firstList, final List<Integer> secondList) {
        final AtomicInteger similarityScore = new AtomicInteger();
        firstList.forEach(value -> similarityScore.addAndGet(value * Collections.frequency(secondList, value)));
        System.out.println("Total similarity score: " + similarityScore.get());
    }
}
