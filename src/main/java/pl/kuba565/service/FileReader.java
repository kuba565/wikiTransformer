package pl.kuba565.service;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface FileReader {
    Document read() throws ParserConfigurationException, IOException, SAXException;
    Boolean fileAvailable();
}
