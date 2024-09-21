package com.niceactimize.workshop.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }

    Pattern pattern = Pattern.compile("^\\+\\d{1,3}\\d{10}$");

    return pattern.matcher(value).matches();
  }
}
