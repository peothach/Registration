package com.registration.converter;

import com.registration.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
class UserTypeConverterTest {

  @InjectMocks
  private UserTypeConverter userTypeConverter;

  @Test
  void test_ConvertToDatabaseColumn() {
    assertThat(UserType.SILVER.getValue()).isEqualTo(userTypeConverter.convertToDatabaseColumn(UserType.SILVER));
    assertThat(UserType.GOLD.getValue()).isEqualTo(userTypeConverter.convertToDatabaseColumn(UserType.GOLD));
    assertThat(UserType.PLATINUM.getValue()).isEqualTo(userTypeConverter.convertToDatabaseColumn(UserType.PLATINUM));
  }

  @Test
  void test_ConvertToEntityAttribute() {
    assertThat(UserType.SILVER).usingRecursiveComparison().isEqualTo(userTypeConverter.convertToEntityAttribute(UserType.SILVER.getValue()));
    assertThat(UserType.GOLD).usingRecursiveComparison().isEqualTo(userTypeConverter.convertToEntityAttribute(UserType.GOLD.getValue()));
    assertThat(UserType.PLATINUM).usingRecursiveComparison().isEqualTo(userTypeConverter.convertToEntityAttribute(UserType.PLATINUM.getValue()));
  }
}