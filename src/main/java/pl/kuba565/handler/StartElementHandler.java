package pl.kuba565.handler;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

public class StartElementHandler {
    public String handle(StartElement startElement) {
        StringBuilder stringBuilder = new StringBuilder();
        String elementName = startElement.getName().getLocalPart();

        switch (elementName) {
            case "section": {
                stringBuilder.append("=");
                Iterator<Attribute> attributes = startElement.getAttributes();
                if (attributes.hasNext()) {
                    Attribute attribute = attributes.next();
                    String heading = attribute.getValue();
                    stringBuilder.append(heading)
                            .append("=")
                            .append(System.lineSeparator());
                }
                break;
            }
            case "bold": {
                stringBuilder.append("'''");
                break;
            }
            case "italic": {
                stringBuilder.append("''");
            }

        }

        return stringBuilder.toString();
    }
}
