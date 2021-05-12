package superp.techpark.ru.lesson10_testing;

public class NameValidatorImpl implements NameValidator {
    @Override
    public boolean isValid(String newName) {
        return newName != null && newName.length() > 2;
    }
}
