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

public class StartElementHandlerTest {
    @Test
    public void shouldReturnCorrectSectionHeadingStringFromXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<section heading=\"dog\">test-string</section>";
        final String expected = "=dog=";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = createFileAndBuildStringUsingStartElementHandler(input, inputFile, 1);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    @Test
    public void shouldReturnCorrectSecondarySectionHeadingStringFromXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<section heading=\"dog\">test-string</section>";
        final String expected = "==dog==";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = createFileAndBuildStringUsingStartElementHandler(input, inputFile, 2);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    @Test
    public void shouldReturnCorrectBeginBoldStringFromXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<bold>test</bold>";
        final String expected = "'''";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = createFileAndBuildStringUsingStartElementHandler(input, inputFile, 0);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    @Test
    public void shouldReturnCorrectBeginItalicStringFromXmlEvent() throws XMLStreamException, IOException {
        // given
        final String input = "<italic>test</italic>";
        final String expected = "''";
        Path inputFile = Files.createTempDirectory("input");

        //when
        String outcome = createFileAndBuildStringUsingStartElementHandler(input, inputFile, 0);

        //then
        Assertions.assertEquals(expected, outcome);
        FileSystemUtils.deleteRecursively(inputFile);
    }

    private String createFileAndBuildStringUsingStartElementHandler(String input, Path inputFile, Integer level) throws XMLStreamException, FileNotFoundException {
        String inputSource = inputFile.toAbsolutePath().toString();
        String newFilePath = inputSource + "\\example.xml";
        try (PrintWriter writer = new PrintWriter(new File(newFilePath))) {
            writer.write(input);
        }
        StartElementHandler startElementHandler = new StartElementHandler();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(newFilePath));
        StringBuilder stringBuilder = new StringBuilder();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                stringBuilder.append(
                        startElementHandler.handle(event.asStartElement(), level
                        ));
            }
        }
        return stringBuilder.toString();
    }
}