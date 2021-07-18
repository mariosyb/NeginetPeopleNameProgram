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
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.replace(",", ""))
                    .distinct()
                    .count();
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }

    public long getLastNameCardinality() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.substring(0, line.indexOf(",")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }

    public long getFirstNameCardinality() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.substring(line.indexOf(",") + 1))
                    .distinct()
                    .count();
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }


}

