package tech.bongers.aoc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

public final class FileReaderUtil {

    private static final Logger logger = Logger.getLogger(FileReaderUtil.class.getName());

    public static List<String> getContentAsList(final String fileName) {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

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
