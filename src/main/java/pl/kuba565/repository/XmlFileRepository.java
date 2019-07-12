package pl.kuba565.repository;

import lombok.Data;
import pl.kuba565.model.XmlFile;

import java.util.List;

@Data
public class XmlFileRepository {
    private List<XmlFile> xmlFiles;
}
