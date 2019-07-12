package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;
import pl.kuba565.repository.WikiFileRepository;
import pl.kuba565.repository.XmlFileRepository;

import java.util.Timer;

@Configuration
@ComponentScan
public class ServiceConfig {
    @Value("${inputSource}")
    public String inputSource;

    @Value("${outputSource}")
    public String outputSource;

    @Bean
    public XmlLooker XmlLooker(XmlFileRepository xmlFileRepository, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        return new XmlLooker(inputSource, xmlFileRepository, xmlFileReader, wikiFileSaver);
    }

    @Bean
    public XmlFileReader XmlFileReader(StartElementHandler startElementHandler, EndElementHandler endElementHandler,
                                       CharactersHandler charactersHandler) {
        return new XmlFileReader(startElementHandler, endElementHandler, charactersHandler);
    }

    @Bean
    public XmlFileRepository XmlFileRepository() {
        return new XmlFileRepository();
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
        return new WikiFileSaver(outputSource);
    }

    @Bean
    public WikiFileRepository WikiFileRepository() {
        return new WikiFileRepository();
    }

    @Bean
    public Timer Timer(XmlFileRepository xmlFileRepository, XmlFileReader xmlFileReader, WikiFileSaver wikiFileSaver) {
        Timer timer = new Timer();
        timer.schedule(new XmlLooker(inputSource, xmlFileRepository, xmlFileReader, wikiFileSaver), 0, 5000);
        return timer;
    }

}
