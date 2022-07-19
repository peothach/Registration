package com.registration.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
          Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  private ValidationUtils() {
  }

  public static boolean validateEmail(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }
}
