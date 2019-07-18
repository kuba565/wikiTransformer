package pl.kuba565.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class XmlLooker {
    private final String inputSource;
    private final XmlFileReader xmlFileReader;
    private final WikiFileSaver wikiFileSaver;
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileReader.class);

    public XmlLooker(String inputSource, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        this.inputSource = inputSource;
        this.xmlFileReader = xmlFileReader;
        this.wikiFileSaver = wikiFileSaver;
    }

    @PostConstruct
    void convertXmlToWiki() {
        new Thread(() -> {
            try {
                runWatcher(FileSystems.getDefault().newWatchService());
            } catch (IOException e) {
                LOGGER.error("file problem {}", e.toString());
            }
        }).start();
    }

    private void runWatcher(WatchService watchService) throws IOException {
        Path path = Paths.get(inputSource);
        WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            key.pollEvents()
                    .forEach(x -> saveAsWikiFile(inputSource + x.context().toString()));
            key.reset();
        }
    }

    private void saveAsWikiFile(String path) {
        String fileName = FilenameUtils.removeExtension(new File(path).getName());
        String content = xmlFileReader.read(path);
        wikiFileSaver.saveWikiFile(fileName, content);
    }
}
