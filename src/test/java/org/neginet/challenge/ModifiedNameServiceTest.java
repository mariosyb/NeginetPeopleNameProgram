package org.neginet.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModifiedNameServiceTest {

    static final int QUANTITY = 25;

    FileService fileService;
    ModifiedNameService modifiedNameService;

    @BeforeEach
    void setUp() throws IOException {
        final String TEST_FILE_NAME = "test-data-modified-names.txt";

        Path resourceDirectory = Paths.get("src", "test", "resources", TEST_FILE_NAME);
        String resourceFile = resourceDirectory.toFile().getAbsolutePath();

        fileService = new FileService();
        String tmp_input_file = fileService.getFile(resourceFile);
        modifiedNameService = new ModifiedNameService(tmp_input_file);
    }

    @Test
    void getModifiedNames() throws IOException {
        LinkedHashSet<String> namesTobeModified = modifiedNameService.getNamesToBeModified(QUANTITY);
        List<String> result = modifiedNameService.getModifiedNames(namesTobeModified);

        System.out.println(result);

        assertThat(result)
                .hasSize(3)
                .doesNotContain("Smith,Joan", "Upton,Tom", "Vasquez,Cesar");
    }

    @Test
    void getNamesToBeModified() throws IOException {
        LinkedHashSet<String> result = modifiedNameService.getNamesToBeModified(QUANTITY);

        assertThat(result)
                .hasSize(3)
                .contains("Smith,Joan", "Upton,Tom", "Vasquez,Cesar")
                .doesNotContain("Smith,John", "Smith,Sam", "Thomas,Joan", "Upton,Joan");
    }
}