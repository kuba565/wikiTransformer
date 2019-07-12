package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import pl.kuba565.model.WikiFile;

import java.io.IOException;
import java.io.PrintWriter;

public class WikiFileSaver {
    private final String outputSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlLooker.class);

    WikiFileSaver(@Value("${outputSource}") String outputSource) {
        this.outputSource = outputSource;
    }

    public void saveWikiFile(WikiFile wikiFile) throws IOException {
        try (PrintWriter out = new PrintWriter(outputSource + "/filename.txt")) {
            out.println(wikiFile.getValue());
        }
    }
}