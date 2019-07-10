package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ServiceConfig {

    @Bean
    public XmlFileReader XmlFileReader(@Value("${inputSource}") String dataSource) {
        return new XmlFileReader(dataSource);
    }
}
