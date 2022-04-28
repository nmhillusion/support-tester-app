package app.netlify.nmhillusion.support_tester_app.builder;

import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

    private Method lookForMethod(String methodName, Object... args) throws NoSuchMethodException {
        Method method = null;
        final Class<?> instanceClass = instance.getClass();
        try {
            method = instanceClass.getMethod(this.methodName, buildClassPrimitiveTypesFromArgs(args));
        } catch (Exception ex) {
            try {
                method = instanceClass.getMethod(this.methodName, buildClassTypesFromArgs(args));
            } catch (Exception ex2) {
                final List<Method> methodList = Arrays.stream(instanceClass.getMethods()).filter(m -> m.getName().equals(methodName)).toList();
                if (1 == methodList.size()) {
                    if (compareParameterTypesAndArgs(methodList.get(0).getParameterTypes(), args)) {
                        method = methodList.get(0);
                    }
                } else {
                    method = lookForMethodByRelativeType(methodList, args);
                }
            }
        }
        if (null == method) {
            throw new NoSuchMethodException(methodName + " with args: " + Arrays.toString(args));
        }
        return method;
    }

    private Method lookForMethodByRelativeType(List<Method> methodList, Object[] args) {
        Method foundMethod = null;
        for (Method method : methodList) {
            if (method.getParameterCount() != args.length) {
                continue;
            }

            final Class<?>[] parameterTypes = method.getParameterTypes();
            if (compareParameterTypesAndArgs(parameterTypes, args)) {
                foundMethod = method;
                break;
            }
        }
        return foundMethod;
    }

    @Override
    public Object apply(Object... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method method = lookForMethod(this.methodName, args);
        method.setAccessible(true);
        return method.invoke(instance, generateArgumentsForInvoking(method.getParameterTypes(), args));
    }
}
