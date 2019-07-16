package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WikiFileSaver {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiFileSaver.class);

    @Value("${outputSource}")
    private final String outputSource;

    WikiFileSaver(@Value("${outputSource}") String outputSource) {
        this.outputSource = outputSource;
    }

    void saveWikiFile(String name, String content) {
        String path = outputSource + "/" + name + ".wiki";
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            writer.write(content);
        } catch (FileNotFoundException e) {
            LOGGER.error("{} not found", path);
        }
    }
}