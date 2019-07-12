package pl.kuba565.repository;

import lombok.Data;
import pl.kuba565.model.WikiFile;

import java.util.List;

@Data
public class WikiFileRepository {
    private List<WikiFile> xmlFiles;
}
