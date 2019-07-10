package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlFileReader implements FileReader {
    private final String inputSource;

    public XmlFileReader(@Value("${inputSource}") String inputSource) {
        this.inputSource = inputSource;
    }

    @Override
    public Document read() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputSource);
    }

    @Override
    public Boolean fileAvailable() {
        return Files.isDirectory(Path.of(inputSource));
    }
}
