/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.spi.store;

/**
 * Exception thrown when a document conflict occurs in content storage.
 */
public class ConflictException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
}
