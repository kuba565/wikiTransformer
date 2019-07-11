package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

@Configuration
@ComponentScan
public class ServiceConfig {

    @Bean
    public XmlFileReader XmlFileReader(@Value("${inputSource}") String dataSource,
                                       StartElementHandler startElementHandler, EndElementHandler endElementHandler,
                                       CharactersHandler charactersHandler) {
        return new XmlFileReader(dataSource, startElementHandler, endElementHandler, charactersHandler);
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
}
