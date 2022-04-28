package app.netlify.nmhillusion.support_tester_app.runner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThirdMaxTest {
    @Test
    void testThirdMax() {
        ThirdMax app = new ThirdMax();
        assertEquals(1, app.thirdMax(new int[]{3, 2, 1}));
        assertEquals(2, app.thirdMax(new int[]{2, 1}));
        assertEquals(1, app.thirdMax(new int[]{2, 2, 3, 1}));
        assertEquals(3, app.thirdMax(new int[]{2, 2, 3, 1, 8, 4, 1, 1, 1}));
        assertEquals(2, app.thirdMax(new int[]{2, 2, 3, 1, 2, 7}));
        assertEquals(2, app.thirdMax(new int[]{2, 2, 3, 1, 3, 4, 4, 3}));
    }
}