package app.netlify.nmhillusion.support_tester_app.builder;

import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class ConstructorBuilder extends Constructable {
    private final String className;

    public ConstructorBuilder(String className) throws BuilderException {
        if (StringValidator.isBlank(className)) {
            throw new BuilderException("className is blank");
        }
        this.className = className;
    }

    public Object apply(Object... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<?> realClass = Class.forName(className);
        Constructor<?> constructor = null;
        try {
            constructor = realClass.getConstructor(buildClassPrimitiveTypesFromArgs(args));
        } catch (NoSuchMethodException ex) {
            constructor = realClass.getConstructor(buildClassTypesFromArgs(args));
        }

        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }
}
