package org.neginet.challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Input file format:
 *
 * Smith, Joan -- corporis
 *     Totam eos ut omnis et nemo dolore.
 * Smith, Sam -- ut
 *     Ut dolorem est voluptate fugit qui vitae.
 * Thomas, Joan -- modi
 *     Nesciunt magni suscipit maxime quaerat sint hic voluptate.
 * Upton, Joan -- veritatis
 *     Sed ut impedit harum.
 * Cartman, Eric -- tenetur
 */
public class FileService {

    public String getFile(String fileAbsolutePath) throws IOException {
        return writeTmpFile(readInputFile(fileAbsolutePath));
    }

    public void deleteFile(String fileAbsolutePath) {
        try {
            boolean result = Files.deleteIfExists(Paths.get(fileAbsolutePath));
            if (result) {
                System.out.println("Temp file is deleted!");
            } else {
                System.out.println("Sorry, unable to delete the file.");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private List<String> readInputFile(String fileAbsolutePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileAbsolutePath))) {

            return stream
                    // filter only lines with names
                    .filter(line -> !line.startsWith(" "))
                    // format lines with names, remove white spaces and everything after "--"
                    .map(str -> str.replaceAll("\\s\\W.*", "").replaceAll("\\s", ""))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    private String writeTmpFile(List<String> content) throws IOException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        Path path = Paths.get(tmpdir, "aux_" + String.valueOf(Instant.now().toEpochMilli()) + ".txt");

        // default utf_8
        Files.write(path, content);

        return path.toString();
    }
}
