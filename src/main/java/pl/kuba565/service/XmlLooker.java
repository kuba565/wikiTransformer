package pl.kuba565.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlLooker {
    @Value("${inputSource}")
    private final String inputSource;
    private final XmlFileReader xmlFileReader;
    private final WikiFileSaver wikiFileSaver;

    public XmlLooker(@Value("${inputSource}") String inputSource, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) throws IOException {
        this.inputSource = inputSource;
        this.xmlFileReader = xmlFileReader;
        this.wikiFileSaver = wikiFileSaver;
        this.checkFileForNewXml();
    }

    private void checkFileForNewXml() throws IOException { // TODO: PATHS TO INPUT/ OUTPUT
        readFiles();
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

    private void readFiles() {
        try (Stream<Path> walk = Files.walk(Paths.get(inputSource))) {

            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".xml")).collect(Collectors.toList());

            result.forEach(this::saveAsWikiFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
