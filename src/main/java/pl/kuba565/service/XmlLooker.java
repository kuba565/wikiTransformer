package pl.kuba565.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Objects;

public class XmlLooker {
    private final String inputSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlLooker.class);

    XmlLooker(@Value("${inputSource}") String inputSource) {
        this.inputSource = inputSource;
    }

    public void checkFileForNewXml() {
        File file = new File(inputSource);

        if (file.isDirectory()) {
            if (Objects.requireNonNull(file.list()).length > 0) {
                LOGGER.info("Directory is not empty!");
            } else {
                LOGGER.info("Directory is empty!");
            }
        } else {
            LOGGER.info("This is not a directory");
        }
    }
}
