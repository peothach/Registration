package com.registration.service.impl;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import com.registration.exception.EmailAlreadyExistsException;
import com.registration.exception.IllegalEmailException;
import com.registration.exception.IllegalSalaryException;
import com.registration.mapper.UserMapper;
import com.registration.repository.UserRepository;
import com.registration.utils.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles(profiles = "test")
class UserServiceImplTest {

  private static final Long ID = 1L;
  private static final String EMAIL = "John@gmail.com";
  private static final String ILLEGAL_EMAIL = "John!gmail.com";
  private static final double SALARY_DOUBLE = 15000;
  private static final BigDecimal SALARY_BIG_DECIMAL = BigDecimal.valueOf(SALARY_DOUBLE);
  private static final String PASSWORD = "password";
  private static final LocalDate CREATE_AT = LocalDate.of(2022, Month.JULY, 18);
  private static final UserType USER_TYPE = UserType.SILVER;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private ValidationUtils validationUtils;
  @InjectMocks
  private UserServiceImpl userService;
  private UserEntity userEntity;
  private UserEntity userEntityFromRequestDTO;
  private UserResponseDto userResponseDTO;
  private UserRequestDto userRequestDTO;

  @BeforeEach
  void init() {
    // setup userEntity
    userEntity = new UserEntity();
    userEntity.setId(ID);
    userEntity.setCreateAt(CREATE_AT);
    userEntity.setEmail(EMAIL);
    userEntity.setPassword(PASSWORD);
    userEntity.setSalary(SALARY_DOUBLE);
    userEntity.setUserType(USER_TYPE);
    // setup userEntityFromRequestDTO
    userEntityFromRequestDTO = new UserEntity();
    userEntityFromRequestDTO.setCreateAt(CREATE_AT);
    userEntityFromRequestDTO.setEmail(EMAIL);
    userEntityFromRequestDTO.setPassword(PASSWORD);
    userEntityFromRequestDTO.setSalary(SALARY_DOUBLE);
    // setup userResponseDTO
    userResponseDTO = new UserResponseDto();
    userResponseDTO.setId(ID);
    userResponseDTO.setEmail(EMAIL);
    userResponseDTO.setSalary(SALARY_BIG_DECIMAL);
    userResponseDTO.setUserType(USER_TYPE.getValue());
    userResponseDTO.setCreateAt(CREATE_AT);
    // setup userRequestDTO
    userRequestDTO = new UserRequestDto();
    userRequestDTO.setEmail(EMAIL);
    userRequestDTO.setSalary(SALARY_DOUBLE);
    userRequestDTO.setPassword(PASSWORD);
  }

  @Test
  void test_saveUser_shouldReturnUserEntity_whenProvideValidUserRequestDTO() {
    // mock
    try (MockedStatic<ValidationUtils> validationUtils = Mockito.mockStatic(ValidationUtils.class)) {
      validationUtils.when(() -> ValidationUtils.validateEmail(any(String.class))).thenReturn(true);
      when(userMapper.mapDtoToEntity(userRequestDTO)).thenReturn(userEntityFromRequestDTO);
      when(userMapper.mapEntityToDto(userEntity)).thenReturn(userResponseDTO);
      when(userRepository.findUserEntityByEmail(any(String.class))).thenReturn(Optional.empty());
      when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

      UserResponseDto actual = userService.save(userRequestDTO);
      assertThat(userResponseDTO)
              .usingRecursiveComparison()
              .isEqualTo(actual);
    } catch (Exception ex) {
      ex.getStackTrace();
    }
  }

  @Test
  void test_saveUser_shouldReturnUserEntity_whenProvideEmailExisted() {
    // mock
    try (MockedStatic<ValidationUtils> validationUtils = Mockito.mockStatic(ValidationUtils.class)) {
      validationUtils.when(() -> ValidationUtils.validateEmail(any(String.class))).thenReturn(true);
      when(userRepository.findUserEntityByEmail(any(String.class))).thenReturn(Optional.ofNullable(userEntity));
      assertThrows(EmailAlreadyExistsException.class, () -> userService.save(userRequestDTO));
    }
  }

  @Test
  void test_saveUser_shouldReturnUserEntity_whenProvideIllegalSalary() {
    // setup illegal salary
    userRequestDTO.setSalary(10000);
    // mock
    try (MockedStatic<ValidationUtils> validationUtils = Mockito.mockStatic(ValidationUtils.class)) {
      validationUtils.when(() -> ValidationUtils.validateEmail(any(String.class))).thenReturn(true);
      when(userMapper.mapDtoToEntity(any(UserRequestDto.class))).thenReturn(userEntityFromRequestDTO);
      when(userRepository.findUserEntityByEmail(any(String.class))).thenReturn(Optional.empty());
      assertThrows(IllegalSalaryException.class, () -> userService.save(userRequestDTO));
    }
  }

  @Test
  void test_saveUser_shouldReturnUserEntity_whenProvideIllegalEmail() throws Exception {
    // mock
    try (MockedStatic<ValidationUtils> validationUtils = Mockito.mockStatic(ValidationUtils.class)) {
      validationUtils.when(() -> ValidationUtils.validateEmail(any(String.class))).thenReturn(false);
      assertThrows(IllegalEmailException.class, () -> userService.save(userRequestDTO));
    }
  }
}