package com.registration.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ValidationUtilsTest {

  @Test
  void test_validateEmail_shouldReturnTrue_whenProvideValidEmail() {
    String validEmail = "John@gmail.com";
    assertThat(ValidationUtils.validateEmail(validEmail)).isTrue();
  }

  @Test
  void test_validateEmail_shouldReturnFalse_whenProvideInValidEmail() {
    String validEmail = "John!gmail.com";
    assertThat(ValidationUtils.validateEmail(validEmail)).isFalse();
  }
}