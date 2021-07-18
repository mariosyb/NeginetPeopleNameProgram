package org.neginet.challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModifiedNameService {
    private final String fileName;

    private final Set<String> seenLastNames = new HashSet<>();
    private final Set<String> seenFirstNames = new HashSet<>();
    private final Set<String> validLastNames = new HashSet<>();
    private final Set<String> validFirstNames = new HashSet<>();
    private final LinkedHashSet<String> validFullNames = new LinkedHashSet<>();

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
        } catch (IOException e) {
            //TODO: handle exception
            throw e;
        }
    }

    private void processName(String fullNameFromFile) {
        String[] namesArray = fullNameFromFile.split(",");

        if (seenLastNames.add(namesArray[0]) && seenFirstNames.add(namesArray[1])) {
            // if if the first time I see these names add them at once
            validLastNames.add(namesArray[0]);
            validFirstNames.add(namesArray[1]);
            validFullNames.add(fullNameFromFile);
        } else {
            // if is not the first time i see one of these names
            // check if one of them is already in the final valid groups
            if (!validLastNames.contains(namesArray[0]) && !validFirstNames.contains(namesArray[1])) {
                validLastNames.add(namesArray[0]);
                validFirstNames.add(namesArray[1]);
                validFullNames.add(fullNameFromFile);
            }

        }
    }


}
