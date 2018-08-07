package translator.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class IsoUtilTest {

    @Test
    public void isValidISOLanguage_existingLanguage_valid() {
        boolean isValid = IsoUtil.isValidISOLanguage("nl");
        assertTrue(isValid);
    }

    @Test
    public void isValidISOLanguage_nonExistingLanguage_inValid() {
        boolean isValid = IsoUtil.isValidISOLanguage("xx");
        assertFalse(isValid);
    }
}