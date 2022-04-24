package app.netlify.nmhillusion.support_tester_app.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */
public abstract class Constructable {
    public abstract Object apply(Object... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    public Class<?>[] buildClassTypesFromArgs(Object[] args) {
        final List<Class<?>> targetClasses = new ArrayList<>();

        if (null != args) {
            for (Object argv : args) {
                targetClasses.add(argv.getClass());
            }
        }

        return targetClasses.toArray(new Class[0]);
    }

    public Class<?> getPrimitiveClassType(Object argv) {
        if (argv instanceof java.lang.Character) {
            return char.class;
        }
        if (argv instanceof java.lang.Short) {
            return short.class;
        }
        if (argv instanceof java.lang.Byte) {
            return byte.class;
        }
        if (argv instanceof java.lang.Integer) {
            return int.class;
        }
        if (argv instanceof java.lang.Long) {
            return long.class;
        }
        if (argv instanceof java.lang.Float) {
            return float.class;
        }
        if (argv instanceof java.lang.Double) {
            return double.class;
        }
        if (argv instanceof java.lang.Boolean) {
            return boolean.class;
        } else {
            return argv.getClass();
        }
    }

    public Class<?>[] buildClassPrimitiveTypesFromArgs(Object[] args) {
        final List<Class<?>> targetClasses = new ArrayList<>();

        if (null != args) {
            for (Object argv : args) {
                targetClasses.add(getPrimitiveClassType(argv));
            }
        }

        return targetClasses.toArray(new Class[0]);
    }
}
