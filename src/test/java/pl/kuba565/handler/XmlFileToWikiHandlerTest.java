package pl.kuba565.handler;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

@RunWith(SpringRunner.class)
public class XmlFileToWikiHandlerTest {

    @Test
    public void shouldReadFile1() throws IOException {
        //given
        final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
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

        final String expected = "\n" +
                "\tThe text can start outside a section.... \n" +
                "\t\n" +
                "\t=Build 1234=\n" +
                "\t\t==Api component==\n" +
                "\t\t\t'''Date: ''01.04.2015'''''\n" +
                "\t\t\t===Main===\n" +
                "\t\t\t\t====astraia.jar====\n" +
                "\t\t\t\t\t''Built in '''512ms'''''\n" +
                "\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t===Test===\n" +
                "\t\t\t\tTest performed on the different databases\n" +
                "\t\t\t\t====Sybase====\n" +
                "\t\t\t\t\t=====Preparing DB=====\n" +
                "\t\t\t\t\t\tDone in\n" +
                "\t\t\t\t\t\t''1556ms''\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t=====JUnits=====\n" +
                "\t\t\t\t\t\t======com.astraia.api.data======\n" +
                "\t\t\t\t\t\t\t'''Passed:''' All passed!\n" +
                "\t\t\t\t\t\t\t'''Failed:''' None\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\n" +
                "\t\n";

        //when
        String result = createInputExampleFileAndGetXmlFileToWikiHandlerOutput(input);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void shouldReadFile2() throws IOException {
        //given
        final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
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
                "\t\t\t\t\t\t\t<bold>Passed:</bold> 5 passed!\n" +
                "\t\t\t\t\t\t\t<bold>Failed:</bold> 1 failed!\n" +
                "\t\t\t\t\t\t\t<section heading=\"failed tests\">\n" +
                "\t\t\t\t\t\t\t\t<bold>TestInterview</bold>\n" +
                "\t\t\t\t\t\t\t</section>\n" +
                "\t\t\t\t\t\t</section>\n" +
                "\t\t\t\t\t</section>\n" +
                "\t\t\t\t</section>\n" +
                "\t\t\t</section>\n" +
                "\t\t</section>\n" +
                "\t</section>\n" +
                "</report>\n";

        final String expected = "\n" +
                "\tThe text can start outside a section.... \n" +
                "\t\n" +
                "\t=Build 1234=\n" +
                "\t\t==Api component==\n" +
                "\t\t\t'''Date: ''01.04.2015'''''\n" +
                "\t\t\t===Main===\n" +
                "\t\t\t\t====astraia.jar====\n" +
                "\t\t\t\t\t''Built in '''512ms'''''\n" +
                "\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t===Test===\n" +
                "\t\t\t\tTest performed on the different databases\n" +
                "\t\t\t\t====Sybase====\n" +
                "\t\t\t\t\t=====Preparing DB=====\n" +
                "\t\t\t\t\t\tDone in\n" +
                "\t\t\t\t\t\t''1556ms''\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t=====JUnits=====\n" +
                "\t\t\t\t\t\t======com.astraia.api.data======\n" +
                "\t\t\t\t\t\t\t'''Passed:''' 5 passed!\n" +
                "\t\t\t\t\t\t\t'''Failed:''' 1 failed!\n" +
                "\t\t\t\t\t\t\t=======failed tests=======\n" +
                "\t\t\t\t\t\t\t\t'''TestInterview'''\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\n" +
                "\t\n";

        //when
        String result = createInputExampleFileAndGetXmlFileToWikiHandlerOutput(input);

        //then
        Assertions.assertEquals(expected, result);
    }

    private XmlFileToWikiHandler getXmlFileToWikiHandler() {
        EndElementHandler endElementHandler = new EndElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();
        StartElementHandler startElementHandler = new StartElementHandler();
        return new XmlFileToWikiHandler(startElementHandler, endElementHandler, charactersHandler);
    }

    private String createInputExampleFileAndGetXmlFileToWikiHandlerOutput(String input) throws IOException {
        Path inputFile = Files.createTempDirectory("input");
        String newFilePath = inputFile.toAbsolutePath().toString() + "\\example.xml";
        XmlFileToWikiHandler xmlFileToWikiHandler = getXmlFileToWikiHandler();

        try (PrintWriter writer = new PrintWriter(new File(newFilePath))) {
            writer.write(input);
        }

        //when
        String result = xmlFileToWikiHandler.handle(newFilePath);
        FileSystemUtils.deleteRecursively(inputFile);
        return result;
    }

}