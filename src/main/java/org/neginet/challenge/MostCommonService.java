package org.neginet.challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The ten most common last names sorted in descending order, including the count of these names.
 * The ten most common first names sorted in descending order, including the count of these names.
 */
public class MostCommonService {
    private final String fileName;

    public MostCommonService(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Integer> get10MostCommonLastNames() throws IOException {
        return get10MostCommon("LAST");
    }

    public Map<String, Integer> get10MostCommonFirstNames() throws IOException {
        return get10MostCommon("FIRST");
    }

    public void printMapInDescendingOrder(Map<String, Integer> map) {
        map
                .entrySet()
                .stream()
                // reverse natural order
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    private Map<String, Integer> get10MostCommon(String option) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> getLastOrFirstName(line, option)) // first or last name
                    .collect(Collectors.groupingBy(line -> line))// grouped by appearance
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().size())) // collect to Map<name, numberOfAppearance>
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    private String getLastOrFirstName(String fullName, String option) {
        String result = null;

        if (option.equals("LAST")) {
            result = fullName.substring(0, fullName.indexOf(","));
        }

        if (option.equals("FIRST")) {
            result = fullName.substring(fullName.indexOf(",") + 1);
        }

        return result;
    }
}
