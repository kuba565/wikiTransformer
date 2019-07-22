package pl.kuba565.handler;

import pl.kuba565.Util.StringUtils;

import javax.xml.stream.events.EndElement;

import static pl.kuba565.Util.StringUtils.BOLD;
import static pl.kuba565.Util.StringUtils.ITALIC;

public class EndElementHandler {
    public String handle(EndElement endElement) {
        String elementName = endElement.getName().getLocalPart();

        switch (elementName) {
            case BOLD: {
                return StringUtils.BOLD_SYMBOL;
            }
            case ITALIC: {
                return StringUtils.ITALIC_SYMBOL;
            }
        }
        return StringUtils.EMPTY;
    }
}
