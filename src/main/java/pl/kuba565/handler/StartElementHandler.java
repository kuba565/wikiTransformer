package pl.kuba565.handler;

import pl.kuba565.Util.StringUtils;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.util.Iterator;

import static pl.kuba565.Util.StringUtils.BOLD;
import static pl.kuba565.Util.StringUtils.ITALIC;

public class StartElementHandler {

    public String handle(StartElement startElement, Integer level) {
        String elementName = startElement.getName().getLocalPart();

        switch (elementName) {
            case StringUtils.SECTION: {
                if (startElement.getAttributes().hasNext()) {
                    return generateSection(level, startElement.getAttributes());
                }
                break;
            }
            case BOLD: {
                return StringUtils.BOLD_SYMBOL;
            }
            case ITALIC: {
                return StringUtils.ITALIC_SYMBOL;
            }
        }
        return StringUtils.EMPTY;
    }

    private String generateSection(Integer level, Iterator<Attribute> attributes) {
        String sectionHeaderSign = "=".repeat(level);
        Attribute attribute = attributes.next();
        String heading = attribute.getValue();
        return String.format("%s%s%s", sectionHeaderSign, heading, sectionHeaderSign);
    }
}
