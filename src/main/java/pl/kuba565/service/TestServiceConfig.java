package pl.kuba565.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kuba565.exception.DirectoryNotFoundException;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class TestServiceConfig {

    @Bean
    public XmlLooker XmlLooker(XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        String inputSource = "./src/test/resources/input/";

        if (!Files.exists(Paths.get(inputSource))) {
            throw new DirectoryNotFoundException(inputSource);
        }

        return new XmlLooker(inputSource, xmlFileReader, wikiFileSaver);
    }

    @Bean
    public XmlFileReader XmlFileReader(StartElementHandler startElementHandler, EndElementHandler endElementHandler,
                                       CharactersHandler charactersHandler) {
        return new XmlFileReader(startElementHandler, endElementHandler, charactersHandler);
    }

    @Bean
    public CharactersHandler CharactersHandler() {
        return new CharactersHandler();
    }

    @Bean
    public EndElementHandler EndElementHandler() {
        return new EndElementHandler();
    }

    @Bean
    public StartElementHandler StartElementHandler() {
        return new StartElementHandler();
    }

    @Bean
    public WikiFileSaver WikiFileSaver() {
        String outputSource = "./src/test/resources/output/";

        if (!Files.exists(Paths.get(outputSource))) {
            throw new DirectoryNotFoundException(outputSource);
        }
        return new WikiFileSaver(outputSource);
    }

}
