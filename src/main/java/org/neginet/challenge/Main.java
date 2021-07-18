package org.neginet.challenge;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // /tmp/neginet-coding-test-data.txt

        System.out.println("Input file absolute path: ");
        Scanner scanner = new Scanner(System.in);

        String absolutePath = scanner.nextLine();

        System.out.println("Modified names quantity: ");
        int qty = scanner.nextInt();

        FileService fileService = new FileService();
        String fileToProcess = fileService.getFile(absolutePath);

        CardinalityService cardinalityService = new CardinalityService(fileToProcess);
        MostCommonService mostCommonService = new MostCommonService(fileToProcess);
        ModifiedNameService modifiedNamesService = new ModifiedNameService(fileToProcess);

        System.out.println("==========================");
        System.out.println("1) CARDINALITY: ");
        System.out.println("FULLNAMES: " + cardinalityService.getFullNameCardinality());
        System.out.println("LASTNAMES: " + cardinalityService.getLastNameCardinality());
        System.out.println("FIRSTNAMES: " + cardinalityService.getFirstNameCardinality());

        System.out.println("==========================");
        System.out.println("2) MOST COMMON: ");
        System.out.println("===============");
        System.out.println("LASTNAMES: ");
        System.out.println("===============");
        Map<String, Integer> mostCommonLastNames = mostCommonService.get10MostCommonLastNames();
        mostCommonService.printMapInDescendingOrder(mostCommonLastNames);
        System.out.println("===============");
        System.out.println("FIRSTNAMES: ");
        System.out.println("===============");
        Map<String, Integer> mostCommonFirstNames = mostCommonService.get10MostCommonFirstNames();
        mostCommonService.printMapInDescendingOrder(mostCommonFirstNames);

        System.out.println("==========================");
        System.out.println("3) MODIFIED NAMES: ");
        System.out.println("===============");
        System.out.println("FIRST N WITH NO PREVIOUS APPEARANCE: ");
        System.out.println("===============");
        LinkedHashSet<String> namesToBeModified = modifiedNamesService.getNamesToBeModified(qty);
        System.out.println(namesToBeModified);
        System.out.println("===============");
        System.out.println("MODIFIED NAMES: ");
        System.out.println("===============");
        List<String> modifiedNames = modifiedNamesService.getModifiedNames(namesToBeModified);
        System.out.println(modifiedNames);

        fileService.deleteFile(fileToProcess);
    }
}
