package pl.kuba565.exception;

public class DirectoryNotFoundException extends RuntimeException {
    public DirectoryNotFoundException(String directory) {
        super(String.format("%s directory not found", directory));
    }
}
