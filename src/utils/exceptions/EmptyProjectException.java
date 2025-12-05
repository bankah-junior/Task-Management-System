package utils.exceptions;

public class EmptyProjectException extends RuntimeException {

    public EmptyProjectException(String message) {
        super(message);
    }

    public EmptyProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
