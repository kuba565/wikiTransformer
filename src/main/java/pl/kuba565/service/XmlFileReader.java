package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

class XmlFileReader {
    private final String inputSource;
    private StartElementHandler startElementHandler;
    private EndElementHandler endElementHandler;
    private CharactersHandler charactersHandler;

    XmlFileReader(@Value("${inputSource}") String inputSource, StartElementHandler startElementHandler,
                  EndElementHandler endElementHandler, CharactersHandler charactersHandler) {
        this.inputSource = inputSource;
        this.startElementHandler = startElementHandler;
        this.charactersHandler = charactersHandler;
        this.endElementHandler = endElementHandler;
    }

    String read() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(inputSource));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT: {
                        stringBuilder.append(startElementHandler.handle(event.asStartElement()));
                        break;
                    }
                    case XMLStreamConstants.CHARACTERS: {
                        stringBuilder.append(charactersHandler.handle(event.asCharacters()));
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        stringBuilder.append(endElementHandler.handle(event.asEndElement()));
                        break;
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
