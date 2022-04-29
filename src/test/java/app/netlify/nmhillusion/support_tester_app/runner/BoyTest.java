package app.netlify.nmhillusion.support_tester_app.runner;

import app.netlify.nmhillusion.support_tester_app.log.LogHelper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BoyTest {

    @Test
    void speak() {
        assertDoesNotThrow(() -> {
            final Method[] boyMethods = Boy.class.getMethods();
            for (Method method : boyMethods) {
                LogHelper.getLog(this).info("[method]: " + method);
            }
        });
    }
}