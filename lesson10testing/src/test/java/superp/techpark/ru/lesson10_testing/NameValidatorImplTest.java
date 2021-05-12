package superp.techpark.ru.lesson10_testing;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameValidatorImplTest {

    @Test
    public void testNegative() {
        NameValidatorImpl validator = new NameValidatorImpl();
        assertFalse(validator.isValid("Oz"));
    }

    @Test
    public void testPositive() {
        NameValidatorImpl validator = new NameValidatorImpl();
        assertTrue(validator.isValid("Nickname"));
    }

    @Test
    public void testNullable() {
        NameValidatorImpl validator = new NameValidatorImpl();
        assertFalse(validator.isValid(null));
    }
}