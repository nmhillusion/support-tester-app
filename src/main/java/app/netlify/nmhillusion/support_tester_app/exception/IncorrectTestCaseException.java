package app.netlify.nmhillusion.support_tester_app.exception;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class IncorrectTestCaseException extends TestCaseException {
    public IncorrectTestCaseException() {
    }

    public IncorrectTestCaseException(String message) {
        super(message);
    }

    public IncorrectTestCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectTestCaseException(Throwable cause) {
        super(cause);
    }
}
