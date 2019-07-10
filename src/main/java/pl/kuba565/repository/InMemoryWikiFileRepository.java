package pl.kuba565.repository;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import pl.kuba565.model.File;
import pl.kuba565.model.WikiFile;
import pl.kuba565.service.FileReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class InMemoryWikiFileRepository implements FileRepository {
    private List<File> wikiFiles;
    private FileReader wikiFileReader;

    public InMemoryWikiFileRepository(FileReader wikiFileReader) throws IOException, SAXException, ParserConfigurationException {
        this.wikiFiles = readFiles();
    }

    public List<File> readFiles() throws IOException, SAXException, ParserConfigurationException {
        Document document = wikiFileReader.read();
        File file = new WikiFile(document.toString());
        return List.of(file);
    }
}
