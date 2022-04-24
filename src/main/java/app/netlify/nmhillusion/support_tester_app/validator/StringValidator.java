package app.netlify.nmhillusion.support_tester_app.validator;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class StringValidator {
    public static boolean isBlank(String data) {
        return null == data || data.trim().isEmpty();
    }
}
