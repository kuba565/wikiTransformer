package pl.kuba565.handler;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.util.FileSystemUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharactersHandlerTest {

    @Test
    public void shouldReturnCorrectStringFromXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<context>test-string</context>";
        final String expected = "test-string";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = buildStringUsingCharactersHandler(input, inputFile);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    @Test
    public void shouldReturnCorrectStringFromNestedXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<context><item1>test-outcome</item1></context>";
        final String expected = "test-outcome";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = buildStringUsingCharactersHandler(input, inputFile);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    @Test
    public void shouldReturnCorrectStringFromOuterAndInnerXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<context>test-<item1>test-string</item1>-test</context>";
        final String expected = "test-test-string-test";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = buildStringUsingCharactersHandler(input, inputFile);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    private String buildStringUsingCharactersHandler(String input, Path inputFile) throws XMLStreamException, FileNotFoundException {
        String inputSource = inputFile.toAbsolutePath().toString();
        String newFilePath = inputSource + "\\example.xml";
        try (PrintWriter writer = new PrintWriter(new File(newFilePath))) {
            writer.write(input);
        }
        CharactersHandler charactersHandler = new CharactersHandler();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(newFilePath));
        StringBuilder stringBuilder = new StringBuilder();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
                stringBuilder.append(
                        charactersHandler.handle(event.asCharacters()
                        ));
            }
        }
        return stringBuilder.toString();
    }
}