package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.util.List;
import java.util.regex.Pattern;

/**
 * <a href="https://adventofcode.com/2015/day/5>2015 Day 05</a>
 */
public class Day05 extends Year2015 {

    public static void main(final String[] args) {
        new Day05().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        final List<String> strings = getPuzzleInputForDay("05");

        final List<String> niceStrings = filterNiceStrings(strings);
        log("Total nice strings: {}", niceStrings.size());

        final List<String> nicerStrings = filterNicerStrings(strings);
        log("Total nicer strings: {}", nicerStrings.size());
    }

    private List<String> filterNiceStrings(final List<String> strings) {
        return strings
                .stream()
                .filter(this::isNiceString)
                .toList();
    }

    private List<String> filterNicerStrings(final List<String> strings) {
        return strings
                .stream()
                .filter(this::isNicerString)
                .toList();
    }

    private boolean isNiceString(final String string) {
        return !containsDisallowedSubstrings(string) && containsDoubleLetter(string) && containsThreeVowels(string);
    }

    private boolean isNicerString(final String string) {
        return containsRepeatingNonOverlappingPair(string) && containsLetterBetweenTwoLetters(string);
    }

    private boolean containsDisallowedSubstrings(final String string) {
        return string.contains("ab") || string.contains("cd") || string.contains("pq") || string.contains("xy");
    }

    private boolean containsDoubleLetter(final String string) {
        return string.matches(".*(.)\\1.*");
    }

    private boolean containsThreeVowels(final String string) {
        return string.matches(".*[aeiou].*[aeiou].*[aeiou].*");
    }

    private boolean containsRepeatingNonOverlappingPair(final String string) {
        final Pattern pairPattern = Pattern.compile("(..).*\\1");
        return pairPattern.matcher(string).find();
    }

    private boolean containsLetterBetweenTwoLetters(final String string) {
        final Pattern repeatPattern = Pattern.compile("(.).\\1");
        return repeatPattern.matcher(string).find();
    }
}
