package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import pl.kuba565.model.WikiFile;
import pl.kuba565.model.XmlFile;
import pl.kuba565.repository.XmlFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

public class XmlLooker extends TimerTask {
    private String inputSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlLooker.class);
    private XmlFileRepository xmlFileRepository;
    private WikiFileSaver wikiFileSaver;
    private XmlFileReader xmlFileReader;

    public XmlLooker(@Value("${inputSource}") String inputSource, XmlFileRepository xmlFileRepository, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        this.xmlFileReader = xmlFileReader;
        this.xmlFileRepository = xmlFileRepository;
        this.wikiFileSaver = wikiFileSaver;
        this.inputSource = inputSource;
    }

    private void checkFileForNewXml() {
        System.out.println("inputSource: " + inputSource);
        File file = new File(inputSource);

        if (file.isDirectory()) {
            if (Objects.requireNonNull(file.list()).length > 0) {
                LOGGER.info("Directory is not empty!");
                String[] fileList = file.list();
                List.of(fileList).forEach(fileName -> {
                    if (!xmlFileRepository.contains(fileName)) {
                        WikiFile wikiFile = new WikiFile();
                        wikiFile.setValue(xmlFileReader.read(inputSource + fileName));
                        wikiFile.setName(fileName);
                        try {
                            wikiFileSaver.saveWikiFile(wikiFile);
                            xmlFileRepository.add(new XmlFile(fileName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                LOGGER.info("Directory is empty!");
            }
        } else {
            LOGGER.info("This is not a directory");
        }

    }

    @Override
    public void run() {
        checkFileForNewXml();
    }
}
