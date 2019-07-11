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
    public void shouldReadFile() {
        //given
        String inputSource = "example1.xml";
        StartElementHandler startElementHandler = new StartElementHandler();
        CharactersHandler charactersHandler = new CharactersHandler();
        EndElementHandler endElementHandler = new EndElementHandler();
        XmlFileReader xmlFileReader = new XmlFileReader(inputSource, startElementHandler, endElementHandler, charactersHandler);

        final String expected = "The text can start outside a section...." +
                System.lineSeparator() +
                "=Build 1234=" +
                System.lineSeparator() +
                "==Api component==" +
                System.lineSeparator() +
                "'''Date: ''01.04.2015'''''" +
                System.lineSeparator() +
                "===Main===" +
                System.lineSeparator() +
                "====astraia.jar====" +
                System.lineSeparator() +
                "''Built in '''512ms'''''" +
                System.lineSeparator() +
                "===Test===" +
                System.lineSeparator() +
                "Test performed on the different databases" +
                System.lineSeparator() +
                "====Sybase====" +
                System.lineSeparator() +
                "=====Preparing DB=====" +
                System.lineSeparator() +
                "Done in ''1556ms''" +
                System.lineSeparator() +
                "=====JUnits=====" +
                System.lineSeparator() +
                "======com.astraia.api.data======" +
                System.lineSeparator() +
                "'''Passed:''' All passed!" +
                System.lineSeparator() +
                "'''Failed:''' None=";

        //when
        String result = xmlFileReader.read();

        //then
        Assertions.assertEquals(expected, result);
    }


}