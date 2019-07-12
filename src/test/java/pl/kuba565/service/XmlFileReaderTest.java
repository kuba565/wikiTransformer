package pl.kuba565.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kuba565.handler.CharactersHandler;
import pl.kuba565.handler.EndElementHandler;
import pl.kuba565.handler.StartElementHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlFileReaderTest {

    @Test
    public void shouldReadFile1() {
        //given
        String inputSource = "example1.xml";
        StartElementHandler startElementHandler = new StartElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();
        EndElementHandler endElementHandler = new EndElementHandler();
        XmlFileReader xmlFileReader = new XmlFileReader(inputSource, startElementHandler, endElementHandler, charactersHandler);

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

        //when
        String result = xmlFileReader.read();

        //then
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void shouldReadFile2() {
        //given
        String inputSource = "example2.xml";
        StartElementHandler startElementHandler = new StartElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();
        EndElementHandler endElementHandler = new EndElementHandler();
        XmlFileReader xmlFileReader = new XmlFileReader(inputSource, startElementHandler, endElementHandler, charactersHandler);

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
                "'''Passed:''' 5 passed!\n" +
                "'''Failed:''' 1 failed!\n" +
                "=======failed tests=======\n" +
                "'''TestInterview'''\n";

        //when
        String result = xmlFileReader.read();

        //then
        Assertions.assertEquals(expected, result);
    }


}