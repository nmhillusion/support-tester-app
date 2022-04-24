package app.netlify.nmhillusion.support_tester_app.exception;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class BuilderException extends Exception {
    public BuilderException() {
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
