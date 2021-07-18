package org.neginet.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CardinalityServiceTest {

    FileService fileService;
    CardinalityService cardinalityService;

    @BeforeEach
    void setUp() throws IOException {
        final String TEST_FILE_NAME = "test-data-cardinality-and-most-common.txt";

        Path resourceDirectory = Paths.get("src", "test", "resources", TEST_FILE_NAME);
        String resourceFile = resourceDirectory.toFile().getAbsolutePath();

        fileService = new FileService();
        String tmp_input_file = fileService.getFile(resourceFile);
        cardinalityService = new CardinalityService(tmp_input_file);
    }

    @Test
    void getFullNameCardinality() throws IOException {
        long result = cardinalityService.getFullNameCardinality();
        assertEquals(5, result);
    }

    @Test
    void getLastNameCardinality() throws IOException {
        long result = cardinalityService.getLastNameCardinality();
        assertEquals(4, result);
    }

    @Test
    void getFirstNameCardinality() throws IOException {
        long result = cardinalityService.getFirstNameCardinality();
        assertEquals(3, result);
    }
}