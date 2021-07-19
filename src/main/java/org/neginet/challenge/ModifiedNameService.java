package org.neginet.challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Take the first N names from the file where the following is true:
 * <p>
 * . No previous name has the same first name.
 * . No previous name has the same last name.
 * <p>
 * After you have this list of 25 names, print a new list that contains N
 * modified names.  These modified names should only use first names and last
 * names from the original 25 names.  However, the new list and the old list should not share any full names.
 */
public class ModifiedNameService {
    private final String fileName;

    private final Set<String> seenLastNames = new HashSet<>(); // every last name that have been already seen
    private final Set<String> seenFirstNames = new HashSet<>(); // every first name that have been already seen
    private final Set<String> validLastNames = new HashSet<>(); // last names that will be part of the final list
    private final Set<String> validFirstNames = new HashSet<>(); // first names that will be part of the final list
    private final LinkedHashSet<String> validFullNames = new LinkedHashSet<>(); // final list of full names

    public ModifiedNameService(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getModifiedNames(LinkedHashSet<String> namesToBeModified) {
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<String> modifiedNames = new ArrayList<>();


        namesToBeModified
                .forEach(fullName -> {
                    String[] namesArray = fullName.split(",");
                    lastNames.add(namesArray[0]);
                    firstNames.add(namesArray[1]);
                });

        Collections.reverse(firstNames);

        if (firstNames.size() % 2 != 0) {
            int middle = firstNames.size() / 2;
            Collections.swap(firstNames, middle, middle - 1);
        }

        IntStream
                .range(0, firstNames.size())
                .mapToObj(i -> lastNames.get(i) + "," + firstNames.get(i))
                .forEach(modifiedNames::add);

        return modifiedNames;
    }

    public LinkedHashSet<String> getNamesToBeModified(int quantity) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream
                    .filter(line -> !line.isEmpty())
                    .forEach(this::processName);

            LinkedHashSet<String> finalSetOfFullNames = new LinkedHashSet<>();

            validFullNames
                    .stream()
                    .limit(quantity)
                    .forEachOrdered(finalSetOfFullNames::add);

            return finalSetOfFullNames;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    private void processName(String fullNameFromFile) {
        String[] namesArray = fullNameFromFile.split(",");

        if (seenLastNames.add(namesArray[0]) && seenFirstNames.add(namesArray[1])) {
            // if it is the first time i see these first and last name, add them to the valid collections at once
            validLastNames.add(namesArray[0]);
            validFirstNames.add(namesArray[1]);
            validFullNames.add(fullNameFromFile);
        } else {
            // not the first appearance of at least one of them
            // invalid full name combination containing one of them was seen
            if (!validLastNames.contains(namesArray[0]) && !validFirstNames.contains(namesArray[1])) {
                // if the last name and first name are not already in the valid sets, they are ok to be added
                validLastNames.add(namesArray[0]);
                validFirstNames.add(namesArray[1]);
                validFullNames.add(fullNameFromFile);
            }

        }
    }
}
