package pl.kuba565.repository;

import lombok.Data;
import pl.kuba565.model.XmlFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class XmlFileRepository {
    private List<XmlFile> xmlFiles;

    public XmlFileRepository() {
        this.xmlFiles = new ArrayList<>();
    }

    public Boolean contains(String name) {
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        xmlFiles.forEach(file -> {
            if (file.getName().equals(name)) {
                result.set(true);
            }
        });
        return result.get();
    }

    public void add(XmlFile xmlFile) {
        xmlFiles.add(xmlFile);
    }
}
