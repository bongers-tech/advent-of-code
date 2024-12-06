package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href="https://adventofcode.com/2015/day/2">2015 Day 02</a>
 */
public class Day02 extends Year2015 {

    public static void main(final String[] args) {
        new Day02().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> giftDimensions = getPuzzleInputForDay("02");
        calculateSquareFeetOfWrappingPaperAndBow(List.of("2x3x4"));
        calculateSquareFeetOfWrappingPaperAndBow(List.of("1x1x10"));
        calculateSquareFeetOfWrappingPaperAndBow(giftDimensions);

    }

    private void calculateSquareFeetOfWrappingPaperAndBow(final List<String> giftDimensions) {
        final AtomicInteger squareFeetPaper = new AtomicInteger();
        final AtomicInteger feetOfRibbon = new AtomicInteger();

        giftDimensions.forEach(dimension -> {
            final String[] dimensions = dimension.split("x");
            final int length = Integer.parseInt(dimensions[0]);
            final int width = Integer.parseInt(dimensions[1]);
            final int height = Integer.parseInt(dimensions[2]);

            final int surfaceArea = 2 * (length * width + width * height + height * length);
            final int slack = Math.min(length * width, Math.min(width * height, height * length));

            final int smallestSide = Math.min(length, Math.min(width, height));
            final int secondSmallestSide = width < height
                    ? Math.clamp(length, width, height)
                    : Math.clamp(length, height, width);

            final int ribbon = smallestSide + smallestSide + secondSmallestSide + secondSmallestSide;
            final int bow = length * width * height;

            feetOfRibbon.getAndAdd(ribbon + bow);
            squareFeetPaper.getAndAdd(surfaceArea + slack);
        });

        log("Total square feet of wrapping paper: {}", squareFeetPaper.get());
        log("Total feet of ribbon: {}", feetOfRibbon.get());

    }
}
