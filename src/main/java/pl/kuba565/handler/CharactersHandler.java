package pl.kuba565.handler;

import javax.xml.stream.events.Characters;

public class CharactersHandler {
    public String handle(Characters characters) {
        String text = characters.toString();
        text = text.stripLeading();
        text = text.replaceAll("\t", "");
        text = text.replaceAll("\n", "");
        return text;
    }
}
