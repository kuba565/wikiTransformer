package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.kuba565.exception.DirectoryNotFoundException;
import pl.kuba565.exception.NullDirectoryException;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class ServiceConfig {
    @Bean
    public XmlLooker XmlLooker(@Value("${inputSource}") String inputSource, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) throws IOException {

        if (inputSource == null || inputSource.equals("")) {
            throw new NullDirectoryException();
        }

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
    public WikiFileSaver WikiFileSaver(@Value("${outputSource}") String outputSource) {
        if (outputSource == null || outputSource.equals("")) {
            throw new NullDirectoryException();
        }

        if (!Files.exists(Paths.get(outputSource))) {
            throw new DirectoryNotFoundException(outputSource);
        }
        return new WikiFileSaver(outputSource);
    }

}
