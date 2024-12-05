package tech.bongers.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

public abstract class AdventOfCode {

    private static final Logger logger = Logger.getLogger(AdventOfCode.class.getName());

    public abstract void doPuzzle();

    public void log(final String message, final Object... params) {
        System.out.println(String.format(message.replace("{}", "%s"), params));
    }

    public List<String> getPuzzleInputForYearAndDay(final String year, final String day) {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(String.format("%s/day%s.txt", year, day));

        if (nonNull(inputStream)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().toList();
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }

        return Collections.emptyList();
    }
}
