package app.netlify.nmhillusion.support_tester_app.model;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class Operator {
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
}
