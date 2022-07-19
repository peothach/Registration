package com.registration.repository;

import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
class UserRepositoryTest {
  private static final String EMAIL_VALID = "John@gmail.com";
  private static final String EMAIL_INVALID = "Invalid@gmail.com";
  private static UserEntity userEntity;
  @Autowired
  private UserRepository userRepository;

  @BeforeAll
  static void setupTest() {
    userEntity = new UserEntity();
    userEntity.setEmail(EMAIL_VALID);
    userEntity.setSalary(15000);
    userEntity.setPassword("password");
    userEntity.setUserType(UserType.SILVER);
    userEntity.setCreateAt(LocalDate.of(2022, Month.JULY, 18));
  }

  @BeforeEach
  void init() {
    userRepository.deleteAll();
    userRepository.save(userEntity);
  }

  @Test
  void test_GetUserByEmail_ShouldReturnUser_IfEmailIsValid() {
    Optional<UserEntity> user = userRepository.findUserEntityByEmail(EMAIL_VALID);
    assertThat(user).isNotEmpty();
  }

  @Test
  void test_GetUserByEmail_ShouldReturnEmpty_IfEmailIsInvalid() {
    Optional<UserEntity> user = userRepository.findUserEntityByEmail(EMAIL_INVALID);
    assertThat(user).isEmpty();
  }

}