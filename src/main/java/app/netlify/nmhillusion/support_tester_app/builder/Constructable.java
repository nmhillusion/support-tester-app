package app.netlify.nmhillusion.support_tester_app.builder;

import org.json.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    protected boolean isArrayType(Class<?> type) {
        if (type.isAssignableFrom(int[].class)) {
            return true;
        }
        if (type.isAssignableFrom(long[].class)) {
            return true;
        }
        if (type.isAssignableFrom(float[].class)) {
            return true;
        }
        if (type.isAssignableFrom(double[].class)) {
            return true;
        }
        if (type.isAssignableFrom(Iterator.class)) {
            return true;
        }
        if (type.isAssignableFrom(Collection.class)) {
            return true;
        }
        return type.isAssignableFrom(JSONArray.class);
    }

    protected boolean compareParameterTypesAndArgs(Class<?>[] parameterTypes, Object[] args) {
        boolean result = true;

        for (int typeIdx = 0; typeIdx < parameterTypes.length; typeIdx++) {
            final Class<?> parameterType = parameterTypes[typeIdx];
            final Class<?> argType = args[typeIdx].getClass();

            if (!parameterType.equals(argType) && !(isArrayType(parameterType) && isArrayType(argType))) {
                result = false;
                break;
            }
        }

        return result;
    }

    protected Object convertArgvByParameterType(Class<?> parameterType, Object argv) {
        Object objValue = argv;
        final Class<?> argvClass = argv.getClass();
        if (parameterType.equals(argvClass) || parameterType.equals(getPrimitiveClassType(argv))) {
            return objValue;
        } else {
            if (argv instanceof final JSONArray jsonArray) {
                List<Object> tempResult = new ArrayList<>();
                if (parameterType.equals(int[].class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.getInt(jsonIndex));
                    }
                    objValue = tempResult.stream().mapToInt(it -> (int) it).toArray();
                } else if (parameterType.equals(long[].class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.getLong(jsonIndex));
                    }
                    objValue = tempResult.stream().mapToLong(it -> (long) it).toArray();
                } else if (parameterType.equals(float[].class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.getFloat(jsonIndex));
                    }
                    objValue = tempResult.stream().map(o -> (float) o).toArray();
                } else if (parameterType.equals(double[].class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.getDouble(jsonIndex));
                    }
                    objValue = tempResult.stream().mapToDouble(it -> (double) it).toArray();
                } else if (parameterType.equals(String[].class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.getString(jsonIndex));
                    }
                    objValue = tempResult.stream().map(String::valueOf).toArray();
                } else if (parameterType.equals(Iterator.class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.get(jsonIndex));
                    }
                    objValue = tempResult.iterator();
                } else if (parameterType.equals(Collection.class)) {
                    for (int jsonIndex = 0; jsonIndex < jsonArray.length(); ++jsonIndex) {
                        tempResult.add(jsonArray.get(jsonIndex));
                    }
                    objValue = tempResult;
                }
            }
        }
        return objValue;
    }

    protected Object[] generateArgumentsForInvoking(Class<?>[] parameterTypes, Object[] args) {
        final List<Object> parameterValues = new ArrayList<>();
        for (int typeIdx = 0; typeIdx < parameterTypes.length; ++typeIdx) {
            final Class<?> parameterType = parameterTypes[typeIdx];
            final Object argv = args[typeIdx];
            parameterValues.add(convertArgvByParameterType(parameterType, argv));
        }
        return parameterValues.toArray();
    }

}
