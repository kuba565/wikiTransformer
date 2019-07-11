package pl.kuba565.handler;

import javax.xml.stream.events.EndElement;

public class EndElementHandler {
    public String handle(EndElement endElement) {
        StringBuilder stringBuilder = new StringBuilder();
        String elementName = endElement.getName().getLocalPart();

        switch (elementName) {
            case "section": {
                stringBuilder.append("=").append(System.lineSeparator());
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
