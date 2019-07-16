package pl.kuba565.exception;

public class NullDirectoryException extends RuntimeException {
    public NullDirectoryException() {
        super("Directory is null");
    }
}