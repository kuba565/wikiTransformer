package pl.kuba565.service;

import pl.kuba565.model.WikiFile;
import pl.kuba565.model.XmlFile;

import java.util.function.Function;

public class XmlToWikiTransformer implements Function<XmlFile, WikiFile> {
    @Override
    public WikiFile apply(XmlFile xmlFile) {
        return new WikiFile(xmlFile.getReport());
    }
}
