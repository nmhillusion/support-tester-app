package app.netlify.nmhillusion.support_tester_app;

import app.netlify.nmhillusion.support_tester_app.builder.ConstructorBuilder;
import app.netlify.nmhillusion.support_tester_app.builder.MethodBuilder;
import app.netlify.nmhillusion.support_tester_app.model.Book;
import org.junit.jupiter.api.Test;

import static app.netlify.nmhillusion.support_tester_app.log.LogHelper.getLog;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {
    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> {
            final ConstructorBuilder constructorBuilder = new ConstructorBuilder("app.netlify.nmhillusion.support_tester_app.model.Book");
            final Object instance = constructorBuilder.apply();
            getLog(this).info("instance: " + instance);

            assertNotNull(instance);
            assertInstanceOf(Book.class, instance);

            final MethodBuilder setBookNameMethod = new MethodBuilder(instance, "setBookName");
            assertNotNull(setBookNameMethod);
            setBookNameMethod.apply("Abstract Universe");

            final MethodBuilder setPriceMethod = new MethodBuilder(instance, "setPrice");
            assertNotNull(setPriceMethod);
            setPriceMethod.apply(90_000);

            final MethodBuilder askReadBookMethod = new MethodBuilder(instance, "askReadBook");
            assertNotNull(askReadBookMethod);

            final Object askQuestion = askReadBookMethod.apply("Bob", 3);
            getLog(this).info("askQuestion: " + askQuestion);
        });
    }

    @Test
    void testMain() {
        Application.main(new String[]{"D:/projects/test-case/sample-test-case.txt", "Y"});
    }
}