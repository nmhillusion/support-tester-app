package app.netlify.nmhillusion.support_tester_app;

import app.netlify.nmhillusion.support_tester_app.log.LogHelper;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class Application {
    public static void main(String[] args) {
        LogHelper.getLog(Application.class).info("Hello Logger");
    }
}
