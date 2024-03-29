package pl.kuba565.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.util.FileSystemUtils;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;
import pl.kuba565.handler.XmlFileToWikiHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static pl.kuba565.util.TestUtil.readLineByLine;

public class XmlToWikiTransformerTest {
    private final static String CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
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

    private final static String EXPECTED = "The text can start outside a section....\n" +
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

    @Test
    public void shouldFindAndTransformXmlFile() throws IOException, InterruptedException {
        //given
        Path inputFile = Files.createTempDirectory("input");
        Path outputFile = Files.createTempDirectory("output");
        String inputSource = inputFile.toAbsolutePath().toString() + "/";
        String outputSource = outputFile.toAbsolutePath().toString() + "/";

        String newFilePath = inputSource + "/example1.xml";

        XmlFileToWikiHandler xmlFileToWikiHandler = getXmlFileToWikiHandler();
        WikiFileSaver wikiFileSaver = new WikiFileSaver(outputSource);

        XmlToWikiTransformer xmlToWikiTransformer = new XmlToWikiTransformer(inputSource, xmlFileToWikiHandler, wikiFileSaver);

        new Thread(xmlToWikiTransformer::convertXmlToWiki).start();


        //when
        Thread.sleep(1000);
        createNewFileWithContent(newFilePath, CONTENT);
        Thread.sleep(1000);

        //then
        String result = readLineByLine(outputSource + "/example1.wiki");

        Assertions.assertEquals(EXPECTED, result);
        FileSystemUtils.deleteRecursively(inputFile);
        FileSystemUtils.deleteRecursively(outputFile);
    }


    @Test
    public void shouldFindAndTransformXmlFileAfterFindingOneFileWithWrongXmlSyntax() throws IOException, InterruptedException {
        //given
        Path inputFile = Files.createTempDirectory("input");
        Path outputFile = Files.createTempDirectory("output");
        String outputSource = outputFile.toAbsolutePath().toString() + "/";
        String inputSource = inputFile.toAbsolutePath().toString() + "/";

        String newBadFilePath = inputSource + "/bad-example1.xml";
        String newFilePath = inputSource + "/example1.xml";

        XmlFileToWikiHandler xmlFileToWikiHandler = getXmlFileToWikiHandler();
        WikiFileSaver wikiFileSaver = new WikiFileSaver(outputSource);

        XmlToWikiTransformer xmlToWikiTransformer = new XmlToWikiTransformer(inputSource, xmlFileToWikiHandler, wikiFileSaver);

        new Thread(xmlToWikiTransformer::convertXmlToWiki).start();

        final String badContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<report>\n" +
                "\tThe =aaaaaaahgfhgf" +
                "\t</section>\n" +
                "</report>\n";

        //when
        Thread.sleep(1000);
        createNewFileWithContent(newBadFilePath, badContent);
        Thread.sleep(1000);
        createNewFileWithContent(newFilePath, CONTENT);
        Thread.sleep(1000);

        //then
        String result = readLineByLine(outputSource + "/example1.wiki");

        Assertions.assertEquals(EXPECTED, result);
        FileSystemUtils.deleteRecursively(inputFile);
        FileSystemUtils.deleteRecursively(outputFile);
    }

    private XmlFileToWikiHandler getXmlFileToWikiHandler() {
        EndElementHandler endElementHandler = new EndElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();
        StartElementHandler startElementHandler = new StartElementHandler();
        return new XmlFileToWikiHandler(startElementHandler, endElementHandler, charactersHandler);
    }

    private void createNewFileWithContent(String newFilePath, String content) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(newFilePath))) {
            writer.write(content);
        }
    }
}

