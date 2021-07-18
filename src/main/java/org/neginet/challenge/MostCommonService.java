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
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.substring(0, line.indexOf(","))) // lastnames
                    .collect(Collectors.groupingBy(line -> line))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().size()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue));
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }

    public Map<String, Integer> get10MostCommonFirstNames() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.substring(line.indexOf(",") + 1)) // firstnames
                    .collect(Collectors.groupingBy(line -> line))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().size()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue));
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }

    public void printMapInDescendingOrder(Map<String, Integer> map) {
        map
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }


}
