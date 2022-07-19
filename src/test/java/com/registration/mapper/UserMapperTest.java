package com.registration.mapper;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles(profiles = "test")
class UserMapperTest {
  private static final Long ID = 1L;
  private static final String EMAIL = "John@gmail.com";
  private static final double SALARY_DOUBLE = 15000;
  private static final BigDecimal SALARY_BIG_DECIMAL = BigDecimal.valueOf(SALARY_DOUBLE);
  private static final String PASSWORD_REQUEST = "password";
  private static final String PASSWORD_ENCRYPT = "$2a$10$HB14nGdCdgUAHpo.p8tL8O.L4ewWjL.A1FrRi3XDwrVxzJ3t4PuTK";
  private static final LocalDate CREATE_AT = LocalDate.now();
  private static final UserType USER_TYPE = UserType.SILVER;
  @InjectMocks
  private UserMapper userMapper;
  @Mock
  private PasswordEncoder passwordEncoder;
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
    userEntity.setPassword(PASSWORD_ENCRYPT);
    userEntity.setSalary(SALARY_DOUBLE);
    userEntity.setUserType(USER_TYPE);
    // setup userEntityFromRequestDTO
    userEntityFromRequestDTO = new UserEntity();
    userEntityFromRequestDTO.setCreateAt(CREATE_AT);
    userEntityFromRequestDTO.setEmail(EMAIL);
    userEntityFromRequestDTO.setPassword(PASSWORD_ENCRYPT);
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
    userRequestDTO.setPassword(PASSWORD_REQUEST);
  }


  @Test
  void test_MapDtoToEntity_ShouldReturnUserEntity_IfPassValidUserRequestDTO() {
    when(passwordEncoder.encode(any(String.class))).thenReturn(PASSWORD_ENCRYPT);
    UserEntity actual = userMapper.mapDtoToEntity(userRequestDTO);
    assertThat(actual).usingRecursiveComparison().isEqualTo(userEntityFromRequestDTO);
  }

  @Test
  void test_MapEntityToDTO_ShouldReturnUserResponseDTO_IfPassValidUserEntity() {
    UserResponseDto actual = userMapper.mapEntityToDto(userEntity);
    assertThat(actual).usingRecursiveComparison().isEqualTo(userResponseDTO);
  }
}