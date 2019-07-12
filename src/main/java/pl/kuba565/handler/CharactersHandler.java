package pl.kuba565.handler;

import javax.xml.stream.events.Characters;

public class CharactersHandler {
    public String handle(Characters characters) {
        return characters.toString();
    }
}
