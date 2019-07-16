package pl.kuba565.service;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ContextConfiguration(classes = TestServiceConfig.class)
@SpringBootTest
public class XmlLookerTest {
    @BeforeEach
    public void init() throws IOException {
        FileUtils.cleanDirectory(new File("./src/test/resources/output/"));
        FileUtils.cleanDirectory(new File("./src/test/resources/input/"));
        String inputSource = "./src/test/resources/input/";
        String outputSource = "./src/test/resources/output/";
        StartElementHandler startElementHandler = new StartElementHandler();
        EndElementHandler endElementHandler = new EndElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();

        XmlFileReader xmlFileReader = new XmlFileReader(startElementHandler, endElementHandler, charactersHandler);

        WikiFileSaver wikiFileSaver = new WikiFileSaver(outputSource);

        XmlLooker xmlLooker = new XmlLooker(inputSource, xmlFileReader, wikiFileSaver);

    }

    @Test
    public void shouldFindAndTransformXmlFile() throws IOException, InterruptedException {
        //given
        System.out.println(xmlLooker);
        String inputSource = "./src/test/resources/input/";
        String outputSource = "./src/test/resources/output/";
//
        final String expected = "The text can start outside a section....\n" +
                "=Build 1234=\n" +
                "==Api component==\n" +
                "'''Date: ''01.04.2015'''''\n" +
                "===Main===\n" +
                "====astraia.jar====\n" +
                "''Built in '''512ms'''''\n" +
                "===Test===\n" +
                "Test performed on the different databases\n" +
                "====Sybase====\n" +
                "=====Preparing DB=====\n" +
                "Done in ''1556ms''\n" +
                "=====JUnits=====\n" +
                "======com.astraia.api.data======\n" +
                "'''Passed:''' All passed!\n" +
                "'''Failed:''' None\n";

        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<report>\n" +
                "\tThe text can start outside a section.... \n" +
                "\t\n" +
                "\t<section heading=\"Build 1234\">\n" +
                "\t\t<section heading=\"Api component\">\n" +
                "\t\t\t<bold>Date: <italic>01.04.2015</italic></bold>\n" +
                "\t\t\t<section heading=\"Main\">\n" +
                "\t\t\t\t<section heading=\"astraia.jar\">\n" +
                "\t\t\t\t\t<italic>Built in <bold>512ms</bold></italic>\n" +
                "\t\t\t\t</section>\n" +
                "\t\t\t</section>\n" +
                "\t\t\t<section heading=\"Test\">\n" +
                "\t\t\t\tTest performed on the different databases\n" +
                "\t\t\t\t<section heading=\"Sybase\">\n" +
                "\t\t\t\t\t<section heading=\"Preparing DB\">\n" +
                "\t\t\t\t\t\tDone in\n" +
                "\t\t\t\t\t\t<italic>1556ms</italic>\n" +
                "\t\t\t\t\t</section>\n" +
                "\t\t\t\t\t<section heading=\"JUnits\">\n" +
                "\t\t\t\t\t\t<section heading=\"com.astraia.api.data\">\n" +
                "\t\t\t\t\t\t\t<bold>Passed:</bold> All passed!\n" +
                "\t\t\t\t\t\t\t<bold>Failed:</bold> None\n" +
                "\t\t\t\t\t\t</section>\n" +
                "\t\t\t\t\t</section>\n" +
                "\t\t\t\t</section>\n" +
                "\t\t\t</section>\n" +
                "\t\t</section>\n" +
                "\t</section>\n" +
                "</report>\n";

        //when

        String newFilePath = inputSource + "/example1.xml";
        try (PrintWriter writer = new PrintWriter(new File(newFilePath))) {
            writer.write(content);
        }

        Path path = Paths.get(outputSource + "example1.wiki");
        String result = Files.readAllLines(path).toString();

        //then
        Assertions.assertEquals(expected, result);
    }
}