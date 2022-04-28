package app.netlify.nmhillusion.support_tester_app.builder;

import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

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

    private Constructor<?> lookForConstructor(Class<?> realClass, Object... args) throws NoSuchMethodException {
        Constructor<?> constructor = null;
        try {
            constructor = realClass.getConstructor(buildClassPrimitiveTypesFromArgs(args));
        } catch (NoSuchMethodException ex) {
            try {
                constructor = realClass.getConstructor(buildClassTypesFromArgs(args));
            } catch (Exception ex2) {
                final List<Constructor<?>> constructorList = List.of(realClass.getConstructors());
                if (1 == constructorList.size()) {
                    if (compareParameterTypesAndArgs(constructorList.get(0).getParameterTypes(), args)) {
                        constructor = constructorList.get(0);
                    }
                } else {
                    constructor = lookForMethodByRelativeType(constructorList, args);
                }
            }
        }
        if (null == constructor) {
            throw new NoSuchMethodException(realClass + " with args: " + Arrays.toString(args));
        }
        return constructor;
    }

    private Constructor<?> lookForMethodByRelativeType(List<Constructor<?>> constructorList, Object[] args) {
        Constructor<?> foundConstructor = null;
        for (Constructor<?> constructor_ : constructorList) {
            if (constructor_.getParameterCount() != args.length) {
                continue;
            }

            final Class<?>[] parameterTypes = constructor_.getParameterTypes();
            if (compareParameterTypesAndArgs(parameterTypes, args)) {
                foundConstructor = constructor_;
                break;
            }
        }
        return foundConstructor;
    }

    public Object apply(Object... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<?> realClass = Class.forName(className);
        final Constructor<?> constructor = lookForConstructor(realClass, args);
        constructor.setAccessible(true);
        return constructor.newInstance(generateArgumentsForInvoking(constructor.getParameterTypes(), args));
    }
}
