package app.netlify.nmhillusion.support_tester_app.model;

import app.netlify.nmhillusion.support_tester_app.log.LogHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class Operator {

    public Operator() {
        LogHelper.getLog(this).info("start with no args");
    }

    public Operator(int x) {
        LogHelper.getLog(this).info("start with 1 argv: " + x);
    }

    public Operator(int[] arr) {
        LogHelper.getLog(this).info("start with args: " + Arrays.toString(arr));
    }

    public int add(int first, int second) {
        return first + second;
    }

    public int subtract(int first, int second) {
        return first - second;
    }

    public int multiply(int first, int second) {
        return first * second;
    }

    public float divide(int first, int second) {
        return (float) first / second;
    }

    public int sum(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    @Test
    void testMin() {
        assertEquals(7, min(Arrays.asList(8, 9, 12, 7, 10).iterator()));
    }

    public int min(Iterator<Integer> numbers) {
        int min = -1;
        int currentVal;
        boolean init = false;
        do
        {
            currentVal = numbers.next();
            if (!init || min > currentVal) {
                min = currentVal;
                init = true;
            }
        } while (numbers.hasNext());
        return min;
    }
}
