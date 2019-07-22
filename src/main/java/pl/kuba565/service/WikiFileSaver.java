package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WikiFileSaver {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiFileSaver.class);
    private final String outputSource;

    public WikiFileSaver(String outputSource) {
        this.outputSource = outputSource;
    }

    public void saveWikiFile(String name, String content) {
        String path = String.format("%s/%s.wiki", outputSource, name);
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            writer.write(content);
        } catch (FileNotFoundException e) {
            LOGGER.error("{} not found", path);
        }
    }
}