package app.netlify.nmhillusion.support_tester_app;

import app.netlify.nmhillusion.support_tester_app.exception.BuilderException;
import app.netlify.nmhillusion.support_tester_app.exception.TestCaseException;
import app.netlify.nmhillusion.support_tester_app.test.TestCaseLoader;
import app.netlify.nmhillusion.support_tester_app.test.TestCaseModel;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static app.netlify.nmhillusion.support_tester_app.log.LogHelper.getLog;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class Application {
    private final TestCaseLoader testCaseLoader = new TestCaseLoader();

    public static void main(String[] args) {
        getLog(Application.class).info(Arrays.toString(args));

        try {
            String testCaseFilePath = "";
            String stopWhenFailTestCase = "";

            if (1 <= args.length) {
                testCaseFilePath = args[0].strip();
            }
            if (2 <= args.length) {
                stopWhenFailTestCase = args[1].strip();
            }

            if (!StringValidator.isBlank(testCaseFilePath)) {
                final Application application = new Application();
                application.run(testCaseFilePath, stopWhenFailTestCase);
            }
        } catch (Throwable ex) {
            getLog(Application.class).error(ex.getMessage(), ex);
        }
    }

    private boolean isStopWhenFailTestCase(String stopWhenFailTestCase) {
        return Stream.of("Y", "1", "true", "T")
                .anyMatch(state -> StringValidator.isBlank(stopWhenFailTestCase) || state.equalsIgnoreCase(stopWhenFailTestCase));
    }

    private List<TestCaseModel> buildTestCaseList(boolean realStopWhenFailTestCase) throws TestCaseException {
        final List<String> callableCaseList = testCaseLoader.getCallableCaseList();
        final List<TestCaseModel> testCaseModelList = new ArrayList<>();

        if (callableCaseList.size() != testCaseLoader.getExpectedOutputList().size() ||
                callableCaseList.size() != testCaseLoader.getInputCaseList().size()
        ) {
            throw new TestCaseException("Length of components of test case are different. callableList size: " + callableCaseList.size() +
                    " ExpectedOutputList size: " + testCaseLoader.getExpectedOutputList().size() +
                    " InputCaseList size: " + testCaseLoader.getInputCaseList().size()
            );
        }

        for (int callableIdx = 0; callableIdx < callableCaseList.size(); callableIdx++) {
            final TestCaseModel testCaseModel = new TestCaseModel()
                    .setIsConstructor(0 == callableIdx)
                    .setCallable(callableCaseList.get(callableIdx))
                    .setInputParams(testCaseLoader.getInputCaseList().get(callableIdx))
                    .setExpectedOutput(testCaseLoader.getExpectedOutputList().get(callableIdx))
                    .setStopWhenFailTestCase(realStopWhenFailTestCase);

            testCaseModelList.add(testCaseModel);
        }
        return testCaseModelList;
    }

    public void run(String testCaseFilePath, String stopWhenFailTestCase) throws TestCaseException, IOException, BuilderException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testCaseLoader.parse(testCaseFilePath);
        final boolean realStopWhenFailTestCase = isStopWhenFailTestCase(stopWhenFailTestCase);

//        getLog(this).debug("CallableCaseList -> " + testCaseLoader.getCallableCaseList());
//        getLog(this).debug("InputCaseList -> " + testCaseLoader.getInputCaseList());
//        getLog(this).debug("ExpectedOutputList -> " + testCaseLoader.getExpectedOutputList());

        /// Mark: START TESTING
        final long testingStartTime = System.currentTimeMillis();

        final List<TestCaseModel> testCaseModelList = buildTestCaseList(realStopWhenFailTestCase);
        final TestCaseModel testCaseOfInstance = testCaseModelList.get(0);
        testCaseOfInstance.executeTestCase();

        int successfulCount = testCaseOfInstance.isSuccess() ? 1 : 0;
        int failedCount = 0;
        final int testCaseSize = testCaseModelList.size();
        for (int testCaseIndex = 1; testCaseIndex < testCaseSize; ++testCaseIndex) {
            final TestCaseModel testCase = testCaseModelList.get(testCaseIndex);
            final boolean resultOfTestCase = testCase
                    .setInstance(testCaseOfInstance.getInstance())
                    .executeTestCase()
                    .isSuccess();

            if (!resultOfTestCase) {
                failedCount += 1;
                getLog(this).error("FAIL in test case: " + testCase);
            } else {
                successfulCount += 1;
            }

            getLog(this).info("[test case status]: [PASS:$successfulCount/$testCaseSize] [FAIL:$failedCount/$testCaseSize]"
                    .replace("$successfulCount", String.valueOf(successfulCount))
                    .replace("$failedCount", String.valueOf(failedCount))
                    .replace("$testCaseSize", String.valueOf(testCaseSize))
            );
        }

        /// Mark: END TESTING

        final boolean resultAllTestCases = testCaseModelList.parallelStream().allMatch(TestCaseModel::isSuccess);
        getLog(this).info("[   Result Test Case     ]");
        getLog(this).info("[ PASS      :  %4d      ]".formatted(successfulCount));
        getLog(this).info("[ FAIL      :  %4d      ]".formatted(failedCount));
        getLog(this).info("[ TOTAL     :  %4d      ]".formatted(testCaseSize));
        getLog(this).info("[ SPEND_TIME:  %4dms    ]".formatted(System.currentTimeMillis() - testingStartTime));

        if (!resultAllTestCases) {
            getLog(this).info("List of fail test case: ");
            testCaseModelList.stream().filter(testcase -> !testcase.isSuccess()).forEach(testcase ->
                    getLog(this).info("\t" + testcase)
            );
        }
    }
}
