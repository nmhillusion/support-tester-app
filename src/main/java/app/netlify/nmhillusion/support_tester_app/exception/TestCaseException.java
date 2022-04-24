package app.netlify.nmhillusion.support_tester_app.exception;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class TestCaseException extends Exception {
    public TestCaseException() {
    }

    public TestCaseException(String message) {
        super(message);
    }

    public TestCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestCaseException(Throwable cause) {
        super(cause);
    }
}
