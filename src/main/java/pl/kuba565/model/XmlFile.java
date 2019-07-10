package pl.kuba565.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class XmlFile implements File {
    private Report report;
}
