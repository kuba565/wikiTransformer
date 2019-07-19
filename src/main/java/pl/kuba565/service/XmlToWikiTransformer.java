package pl.kuba565.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kuba565.Util.WikiStringFormatter;
import pl.kuba565.handler.XmlFileToWikiHandler;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class XmlToWikiTransformer {
    private final String inputSource;
    private final XmlFileToWikiHandler xmlFileToWikiHandler;
    private final WikiFileSaver wikiFileSaver;
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileToWikiHandler.class);

    public XmlToWikiTransformer(String inputSource, XmlFileToWikiHandler xmlFileToWikiHandler, WikiFileSaver wikiFileSaver) {
        this.inputSource = inputSource;
        this.xmlFileToWikiHandler = xmlFileToWikiHandler;
        this.wikiFileSaver = wikiFileSaver;
    }

    @PostConstruct
    public void convertXmlToWiki() {
        // TODO: Thread tests!
        try {
            runWatcher(FileSystems.getDefault().newWatchService());
        } catch (IOException e) {
            LOGGER.error("file problem {}", e.toString());
        }
    }

    private void runWatcher(WatchService watchService) throws IOException {
        Path path = Paths.get(inputSource);
        WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            key.pollEvents()
                    .forEach(x -> saveAsWikiFile(inputSource + "/" + x.context().toString()));
            key.reset();
        }
    }

    private void saveAsWikiFile(String path) {
        String fileName = FilenameUtils.removeExtension(new File(path).getName());
        String content = WikiStringFormatter.format(xmlFileToWikiHandler.handle(path));
        wikiFileSaver.saveWikiFile(fileName, content);
    }
}
