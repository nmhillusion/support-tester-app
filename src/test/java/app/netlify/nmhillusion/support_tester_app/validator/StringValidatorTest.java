package app.netlify.nmhillusion.support_tester_app.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringValidatorTest {

    @Test
    void isBlank() {
        assertTrue(StringValidator.isBlank(null));
        assertTrue(StringValidator.isBlank(""));
        assertTrue(StringValidator.isBlank(" "));
        assertTrue(StringValidator.isBlank("   "));

        assertFalse(StringValidator.isBlank(" a  "));
        assertFalse(StringValidator.isBlank("  5"));
        assertFalse(StringValidator.isBlank("  67 "));
        assertFalse(StringValidator.isBlank("67"));
    }
}