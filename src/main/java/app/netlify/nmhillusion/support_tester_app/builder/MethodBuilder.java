package app.netlify.nmhillusion.support_tester_app.builder;

import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class MethodBuilder extends Constructable {
    private final Object instance;
    private final String methodName;

    public MethodBuilder(Object instance, String methodName) throws BuilderException {
        if (null == instance) {
            throw new BuilderException("instance is null");
        }
        if (StringValidator.isBlank(methodName)) {
            throw new BuilderException("methodName is blank");
        }

        this.instance = instance;
        this.methodName = methodName;
    }

    @Override
    public Object apply(Object... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method method = null;

        try {
            method = instance.getClass().getMethod(methodName, buildClassPrimitiveTypesFromArgs(args));
        } catch (Exception ex) {
            method = instance.getClass().getMethod(methodName, buildClassTypesFromArgs(args));
        }

        method.setAccessible(true);
        return method.invoke(instance, args);
    }
}
