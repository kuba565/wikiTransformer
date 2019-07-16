package pl.kuba565.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class XmlLooker {
    @Value("${inputSource}")
    private final String inputSource;
    private final XmlFileReader xmlFileReader;
    private final WikiFileSaver wikiFileSaver;

    public XmlLooker(@Value("${inputSource}") String inputSource, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        this.inputSource = inputSource;
        this.xmlFileReader = xmlFileReader;
        this.wikiFileSaver = wikiFileSaver;
    }

    @PostConstruct
    void convertXmlToWiki() throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(inputSource);
        WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            watchKey.pollEvents()
                    .forEach(x -> saveAsWikiFile(inputSource + x.context().toString()));
            watchKey.reset();
        }
    }

    private void saveAsWikiFile(String path) {
        String fileName = FilenameUtils.removeExtension(new File(path).getName());
        String content = xmlFileReader.read(path);
        wikiFileSaver.saveWikiFile(fileName, content);
    }
}
