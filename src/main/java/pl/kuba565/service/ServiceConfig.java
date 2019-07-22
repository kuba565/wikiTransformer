package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;
import pl.kuba565.handler.XmlFileToWikiHandler;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ServiceConfig {
    @Bean
    public XmlToWikiTransformer XmlToWikiTransformer(@Value("${inputSource}") String inputSource, XmlFileToWikiHandler xmlFileToWikiHandler, WikiFileSaver wikiFileSaver) throws FileNotFoundException {
        if (inputSource == null || inputSource.isEmpty()) {
            throw new NullPointerException("null input source");
        }

        if (!Files.exists(Paths.get(inputSource))) {
            throw new FileNotFoundException(inputSource);
        }

        return new XmlToWikiTransformer(inputSource, xmlFileToWikiHandler, wikiFileSaver);
    }

    @Bean
    public XmlFileToWikiHandler XmlFileReader(StartElementHandler startElementHandler, EndElementHandler endElementHandler,
                                              CharactersHandler charactersHandler) {
        return new XmlFileToWikiHandler(startElementHandler, endElementHandler, charactersHandler);
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
    public WikiFileSaver WikiFileSaver(@Value("${outputSource}") String outputSource) throws FileNotFoundException {
        if (outputSource == null || outputSource.isEmpty()) {
            throw new NullPointerException("null output source");
        }

        if (!Files.exists(Paths.get(outputSource))) {
            throw new FileNotFoundException(outputSource);
        }
        return new WikiFileSaver(outputSource);
    }

}
