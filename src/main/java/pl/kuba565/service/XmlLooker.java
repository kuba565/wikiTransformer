package pl.kuba565.service;

import org.springframework.beans.factory.annotation.Value;

public class XmlLooker {
    private final String inputSource;

    XmlLooker(@Value("${inputSource}") String inputSource) {
        this.inputSource = inputSource;
    }

    public void checkFileForNewXml() {

    }
}
