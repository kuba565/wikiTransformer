package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kuba565.Util.TextTrimmer;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileReader.class);
    private StartElementHandler startElementHandler;
    private EndElementHandler endElementHandler;
    private CharactersHandler charactersHandler;

    XmlFileReader(StartElementHandler startElementHandler,
                  EndElementHandler endElementHandler, CharactersHandler charactersHandler) {
        this.startElementHandler = startElementHandler;
        this.charactersHandler = charactersHandler;
        this.endElementHandler = endElementHandler;
    }

    String read(String inputSource) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Integer level = 1;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(inputSource));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                    case XMLStreamConstants.END_ELEMENT: {
                        level = handleEvent(stringBuilder, level, event);
                        break;
                    }
                    case XMLStreamConstants.CHARACTERS: {
                        stringBuilder.append(charactersHandler.handle(event.asCharacters()));
                        break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.error("input file syntax problem {}", e.toString());
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found {}", e.toString());
        }
        return TextTrimmer.trim(stringBuilder);
    }

    private Integer handleEvent(StringBuilder stringBuilder, Integer level, XMLEvent event) {
        if (event.isStartElement()) {
            level = handleStartEvent(stringBuilder, level, event);
        } else {
            level = handleEndEvent(stringBuilder, level, event);
        }
        return level;
    }

    private Integer handleEndEvent(StringBuilder stringBuilder, Integer level, XMLEvent event) {
        boolean section = event.asEndElement().getName().getLocalPart().equalsIgnoreCase("section");
        if (section) {
            stringBuilder.append(endElementHandler.handle(event.asEndElement()));
            level--;
        } else {
            stringBuilder.append(endElementHandler.handle(event.asEndElement()));
        }
        return level;
    }

    private Integer handleStartEvent(StringBuilder stringBuilder, Integer level, XMLEvent event) {
        boolean section = event.asStartElement().getName().getLocalPart().equalsIgnoreCase("section");
        if (section) {
            stringBuilder.append(startElementHandler.handle(event.asStartElement(), level));
            level++;
        } else {
            stringBuilder.append(startElementHandler.handle(event.asStartElement(), level));
        }
        return level;
    }
}
