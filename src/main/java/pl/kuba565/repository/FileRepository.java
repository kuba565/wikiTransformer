package pl.kuba565.repository;

import org.xml.sax.SAXException;
import pl.kuba565.model.File;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface FileRepository {
    List<File> readFiles() throws IOException, SAXException, ParserConfigurationException;
}
