package app.netlify.nmhillusion.support_tester_app.test;

import app.netlify.nmhillusion.support_tester_app.builder.ConstructorBuilder;
import app.netlify.nmhillusion.support_tester_app.builder.MethodBuilder;
import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.exception.IncorrectTestCaseException;
import app.netlify.nmhillusion.support_tester_app.log.LogHelper;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class TestCaseModel {
    private boolean isConstructor;
    private String callable;
    private List<Object> inputParams;
    private Object expectedOutput;
    private Object actualOutput;
    private Object instance;
    private boolean stopWhenFailTestCase = false;
    private AtomicBoolean resultRef;

    public boolean getIsConstructor() {
        return isConstructor;
    }

    public TestCaseModel setIsConstructor(boolean isConstructor) {
        this.isConstructor = isConstructor;
        return this;
    }

    public Object getActualOutput() {
        return actualOutput;
    }

    public TestCaseModel setActualOutput(Object actualOutput) {
        this.actualOutput = actualOutput;
        return this;
    }

    public String getCallable() {
        return callable;
    }

    public TestCaseModel setCallable(String callable) {
        this.callable = callable;
        return this;
    }

    public List<Object> getInputParams() {
        return inputParams;
    }

    public TestCaseModel setInputParams(List<Object> inputParams) {
        this.inputParams = inputParams;
        return this;
    }

    public Object getExpectedOutput() {
        return expectedOutput;
    }

    public TestCaseModel setExpectedOutput(Object expectedOutput) {
        this.expectedOutput = expectedOutput;
        return this;
    }

    public Object getInstance() {
        return instance;
    }

    public TestCaseModel setInstance(Object instance) {
        this.instance = instance;
        return this;
    }

    public boolean getStopWhenFailTestCase() {
        return stopWhenFailTestCase;
    }

    public TestCaseModel setStopWhenFailTestCase(boolean stopWhenFailTestCase) {
        this.stopWhenFailTestCase = stopWhenFailTestCase;
        return this;
    }

    public TestCaseModel executeTestCase() throws BuilderException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IncorrectTestCaseException {
        if (isConstructor) {
            final ConstructorBuilder constructorBuilder = new ConstructorBuilder(callable);
            this.instance = constructorBuilder.apply(inputParams.toArray());
        } else {
            final MethodBuilder methodBuilder = new MethodBuilder(this.instance, callable);
            this.actualOutput = methodBuilder.apply(inputParams.toArray());
        }

        if (stopWhenFailTestCase) {
            throwIfUnsuccessful();
        }

        LogHelper.getLog(this).info(this + " --> Result: " + isSuccess());
        return this;
    }

    private void throwIfUnsuccessful() throws IncorrectTestCaseException {
        if (!isSuccess()) {
            throw new IncorrectTestCaseException("actual: " + actualOutput + "[" + actualOutput.getClass() + "], expected: " + expectedOutput + "[" + expectedOutput.getClass() + "] of testCase: " + this);
        }
    }

    public boolean isSuccess() {
        if (null != this.resultRef) {
            return this.resultRef.get();
        } else {
            boolean result = false;
            if (null == this.actualOutput && null == this.expectedOutput) {
                result = true;
            } else {
                if (null != expectedOutput) {
                    if (isNumber(expectedOutput)
                    ) {
                        final double expectedDoubleValue = new BigDecimal(String.valueOf(expectedOutput)).doubleValue();
                        final double actualDoubleValue = new BigDecimal(String.valueOf(actualOutput)).doubleValue();

                        return Math.round(expectedDoubleValue) == Math.round(actualDoubleValue);
                    } else {
                        result = expectedOutput.equals(this.actualOutput);
                    }
                }
            }
            resultRef = new AtomicBoolean();
            resultRef.set(result);
            return result;
        }
    }

    private boolean isNumber(Object expectedOutput) {
        final Class<?> expectedOutputClass = expectedOutput.getClass();
        return Integer.class.isAssignableFrom(expectedOutputClass) ||
                Long.class.isAssignableFrom(expectedOutputClass) ||
                Float.class.isAssignableFrom(expectedOutputClass) ||
                Double.class.isAssignableFrom(expectedOutputClass) ||
                BigDecimal.class.isAssignableFrom(expectedOutputClass);
    }

    @Override
    public String toString() {
        return "TestCaseModel{" +
                "callable='" + callable + '\'' +
                ", inputParams=" + inputParams +
                ", expectedOutput=" + expectedOutput +
                ", actualOutput=" + actualOutput +
                '}';
    }
}
