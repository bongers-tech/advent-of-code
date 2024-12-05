package tech.bongers.aoc.day;

import tech.bongers.aoc.util.FileReaderUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <a href="https://adventofcode.com/2024/day/5">2024 Day 05</a>
 */
public class Day05 {

    public static void main(final String[] args) {
        final List<String> rulesAndUpdates = FileReaderUtil.getContentAsList("day05.txt");

        final Map<String, List<String>> rules = rulesAndUpdates
                .stream()
                .filter(line -> line.contains("|"))
                .map(line -> line.split("\\|"))
                .collect(Collectors.toMap(
                        rule -> rule[0],
                        rule -> new ArrayList<>(Collections.singletonList(rule[1])),
                        (existingList, newList) -> {
                            existingList.addAll(newList);
                            return existingList;
                        }
                ));

        final List<String> updates = rulesAndUpdates
                .stream()
                .filter(line -> line.contains(","))
                .toList();

        final List<String> correctUpdates = filterCorrectUpdatesForRules(updates, rules);
        final List<String> incorrectUpdates = new ArrayList<>(updates);
        incorrectUpdates.removeAll(correctUpdates);

        final List<String> fixedIncorrectUpdates = fixOrderOfIncorrectUpdates(incorrectUpdates, rules);

        System.out.println("Total correct updates: " + correctUpdates.size());
        System.out.println("Total incorrect updates: " + incorrectUpdates.size());

        System.out.println("Addition of middle pages for correct updates: " + calculateAdditionOfMiddlePages(correctUpdates));
        System.out.println("Addition of middle pages for corrected updates: " + calculateAdditionOfMiddlePages(fixedIncorrectUpdates));
    }

    private static List<String> filterCorrectUpdatesForRules(final List<String> updates, final Map<String, List<String>> rules) {
        final List<String> correctUpdates = new ArrayList<>();
        for (String page : updates) {
            if (isCorrectOrder(page.split(","), rules)) {
                correctUpdates.add(page);
            }
        }
        return correctUpdates;
    }

    private static List<String> fixOrderOfIncorrectUpdates(final List<String> incorrectUpdates, final Map<String, List<String>> rules) {
        final List<String> correctedUpdates = new ArrayList<>();
        for (String update : incorrectUpdates) {
            final List<String> orderedPages = Arrays.asList(update.split(","));
            orderedPages.sort((first, second) -> {
                if (rules.containsKey(first) && rules.get(first).contains(second)) {
                    return 1;
                } else if (rules.containsKey(second) && rules.get(second).contains(first)) {
                    return -1;
                }
                return 0;
            });
            correctedUpdates.add(String.join(",", orderedPages));
        }
        return correctedUpdates;
    }

    private static boolean isCorrectOrder(final String[] pages, final Map<String, List<String>> rules) {
        final Map<String, Integer> pageOrder = new HashMap<>();
        for (int i = 0; i < pages.length; i++) {
            pageOrder.put(pages[i], i);
        }

        for (int i = 0; i < pages.length; i++) {
            final String page = pages[i];
            if (rules.containsKey(page)) {
                for (String afterPage : rules.get(page)) {
                    if (pageOrder.containsKey(afterPage) && pageOrder.get(afterPage) < i) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int calculateAdditionOfMiddlePages(final List<String> correctUpdates) {
        final AtomicInteger total = new AtomicInteger();
        correctUpdates.forEach(update -> {
            final List<Integer> pageNumbers = Arrays.stream(update.split(",")).map(Integer::parseInt).toList();
            total.getAndAdd(pageNumbers.get(pageNumbers.size() / 2));
        });
        return total.get();
    }
}
