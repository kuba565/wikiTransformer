package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestUtil {
    public static String readLineByLine(String filePath) {
        final Logger LOGGER = LoggerFactory.getLogger(XmlFileReader.class);
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            LOGGER.error("exception happened while reading tested file - {}", e.toString());
        }

        return contentBuilder.toString();
    }
}
