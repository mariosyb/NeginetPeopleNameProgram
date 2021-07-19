package org.neginet.challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * The cardinality (count of unique items) of each the three sets of full, last, and first names
 * Lastname, Firstname
 * A full name is identical to another full name if it has the same first name (case sensitive) and last name (case sensitive).
 */
public class CardinalityService {
    private final String fileName;

    public CardinalityService(String fileName) {
        this.fileName = fileName;
    }

    public long getFullNameCardinality() throws IOException {
        return getCardinality("FULL");
    }

    public long getLastNameCardinality() throws IOException {
        return getCardinality("LAST");
    }

    public long getFirstNameCardinality() throws IOException {
        return getCardinality("FIRST");
    }

    private long getCardinality(String option) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> getLastFirstOrFullName(line, option))
                    .distinct()
                    .count();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    private String getLastFirstOrFullName(String fullName, String option) {
        String result = null;

        if (option.equals("LAST")) {
            result = fullName.substring(0, fullName.indexOf(","));
        }

        if (option.equals("FIRST")) {
            result = fullName.substring(fullName.indexOf(",") + 1);
        }

        if (option.equals("FULL")) {
            result = fullName.replace(",", "");
        }

        return result;
    }
}

