package pl.kuba565;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WikiTransformerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WikiTransformerApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
