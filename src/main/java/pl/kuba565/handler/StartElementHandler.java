package pl.kuba565.handler;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

public class StartElementHandler {

    public String handle(StartElement startElement, Integer level) {
        StringBuilder stringBuilder = new StringBuilder();
        String elementName = startElement.getName().getLocalPart();

        switch (elementName) {
            case "report": {

                break;
            }
            case "section": {
                Iterator<Attribute> attributes = startElement.getAttributes();
                if (attributes.hasNext()) {
                    String sectionHeaderSign = "=".repeat(level);
                    Attribute attribute = attributes.next();
                    String heading = attribute.getValue();
                    stringBuilder
                            .append(sectionHeaderSign)
                            .append(heading)
                            .append(sectionHeaderSign);
                }
                break;
            }
            case "bold": {
                stringBuilder.append("'''");
                break;
            }
            case "italic": {
                stringBuilder.append("''");
                break;
            }
        }
        return stringBuilder.toString();
    }
}
