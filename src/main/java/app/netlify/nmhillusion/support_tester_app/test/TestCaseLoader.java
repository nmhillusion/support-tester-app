package app.netlify.nmhillusion.support_tester_app.test;

import app.netlify.nmhillusion.support_tester_app.exception.TestCaseException;
import app.netlify.nmhillusion.support_tester_app.validator.StringValidator;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class TestCaseLoader {
    private final List<String> callableCaseList = new ArrayList<>();
    private final List<List<Object>> inputCaseList = new ArrayList<>();
    private final List<Object> expectedOutputList = new ArrayList<>();

    private List<String> loadThreeFirstLineContent(String testCaseFile) throws IOException, TestCaseException {
        final List<String> lines = Files.readAllLines(Paths.get(testCaseFile)).stream()
                .filter(line -> !StringValidator.isBlank(line))
                .toList();

        if (3 > lines.size()) {
            throw new TestCaseException("Not enough three line of test case file");
        }

        return lines;
    }

    public void parse(String testCaseFile) throws TestCaseException, IOException {
        final List<String> linesOfTestCase = loadThreeFirstLineContent(testCaseFile);
        final String firstLine = linesOfTestCase.get(0);
        final String secondLine = linesOfTestCase.get(1);
        final String thirdLine = linesOfTestCase.get(2);

        final JSONArray firstJsonData = new JSONArray(firstLine);
        for (int idx = 0; idx < firstJsonData.length(); idx++) {
            final String item = String.valueOf(firstJsonData.getString(idx)).strip();
            callableCaseList.add(item);
        }

        final JSONArray secondJsonData = new JSONArray(secondLine);
        for (int idx = 0; idx < secondJsonData.length(); idx++) {
            final List<Object> itemArray = new ArrayList<>();
            final JSONArray itemJsonData = secondJsonData.getJSONArray(idx);
            for (int idx2 = 0; idx2 < itemJsonData.length(); idx2++) {
                itemArray.add(itemJsonData.get(idx2));
            }

            inputCaseList.add(itemArray);
        }

        final JSONArray thirdJsonData = new JSONArray(thirdLine);
        for (int idx = 0; idx < thirdJsonData.length(); idx++) {
            expectedOutputList.add(thirdJsonData.get(idx));
        }
    }

    public List<String> getCallableCaseList() {
        return callableCaseList;
    }

    public List<List<Object>> getInputCaseList() {
        return inputCaseList;
    }

    public List<Object> getExpectedOutputList() {
        return expectedOutputList;
    }
}
