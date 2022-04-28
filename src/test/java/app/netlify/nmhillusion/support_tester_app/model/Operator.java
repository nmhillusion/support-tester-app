package app.netlify.nmhillusion.support_tester_app.model;

import app.netlify.nmhillusion.support_tester_app.log.LogHelper;

import java.util.Arrays;

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
}
