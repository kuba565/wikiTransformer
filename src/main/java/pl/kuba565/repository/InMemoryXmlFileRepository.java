package pl.kuba565.repository;

import org.xml.sax.SAXException;
import pl.kuba565.model.File;
import pl.kuba565.model.WikiFile;
import pl.kuba565.model.XmlFile;
import pl.kuba565.service.FileReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class InMemoryXmlFileRepository implements FileRepository {
    private List<File> xmlFiles;
    private FileReader xmlFileReader;

    public InMemoryXmlFileRepository(FileReader xmlFileReader) throws IOException, SAXException, ParserConfigurationException {
        this.xmlFiles = readFiles();
    }

    @Override
    public List<File> readFiles() throws IOException, SAXException, ParserConfigurationException {
        File file = new XmlFile(document.toString());
        return List.of(file);
    }
}
