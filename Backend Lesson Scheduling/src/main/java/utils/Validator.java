package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 * @date 13/05/2021
 */
public class Validator {
    private static final String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String regexName = "^\\pL+[\\pL\\pZ\\pP]*$";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_NAME_REGEX = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validateName(String name) {
        Matcher matcher = VALID_NAME_REGEX.matcher(name);
        return matcher.find();
    }
}