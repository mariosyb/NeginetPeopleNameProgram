package org.neginet.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MostCommonServiceTest {

    FileService fileService;
    MostCommonService mostCommonService;

    @BeforeEach
    void setUp() throws IOException {
        final String TEST_FILE_NAME = "test-data-cardinality-and-most-common.txt";

        Path resourceDirectory = Paths.get("src", "test", "resources", TEST_FILE_NAME);
        String resourceFile = resourceDirectory.toFile().getAbsolutePath();

        fileService = new FileService();
        String tmp_input_file = fileService.getFile(resourceFile);
        mostCommonService = new MostCommonService(tmp_input_file);
    }

    @Test
    void get10MostCommonLastNames() throws IOException {
        // given
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Smith", 2);
        expected.put("Thomas", 1);
        expected.put("Upton", 1);
        expected.put("Cartman", 1);

        //when
        Map<String, Integer> result = mostCommonService.get10MostCommonLastNames();

        // then
        assertThat(result)
                .hasSize(4)
                .containsAllEntriesOf(expected);
    }

    @Test
    void get10MostCommonFirstNames() throws IOException {
        // given
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Joan", 3);
        expected.put("Sam", 1);
        expected.put("Eric", 1);

        //when
        Map<String, Integer> result = mostCommonService.get10MostCommonFirstNames();

        // then
        assertThat(result)
                .hasSize(3)
                .containsAllEntriesOf(expected);
    }
}