package com.registration.service.impl;

import com.registration.dto.request.UserRequestDTO;
import com.registration.dto.response.UserResponseDTO;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import com.registration.exception.EmailAlreadyExistsException;
import com.registration.exception.IllegalSalaryException;
import com.registration.mapper.UserMapper;
import com.registration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles(profiles = "test")
class UserServiceImplTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "John@gmail.com";
    private static final double SALARY_DOUBLE = 15000;
    private static final BigDecimal SALARY_BIG_DECIMAL = BigDecimal.valueOf(SALARY_DOUBLE);
    private static final String PASSWORD = "password";
    private static final LocalDate CREATE_AT = LocalDate.of(2022, Month.JULY, 18);
    private static final UserType USER_TYPE = UserType.SILVER;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userService;
    private UserEntity userEntity;
    private UserEntity userEntityFromRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UserRequestDTO userRequestDTO;

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
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(ID);
        userResponseDTO.setEmail(EMAIL);
        userResponseDTO.setSalary(SALARY_BIG_DECIMAL);
        userResponseDTO.setUserType(USER_TYPE.getValue());
        // setup userRequestDTO
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(EMAIL);
        userRequestDTO.setSalary(SALARY_DOUBLE);
        userRequestDTO.setPassword(PASSWORD);
    }

    @Test
    void test_saveUser_shouldReturnUserEntity_whenProvideValidUserRequestDTO() {
        // mock
        when(userMapper.mapDtoToEntity(userRequestDTO)).thenReturn(userEntityFromRequestDTO);
        when(userMapper.mapEntityToDTO(userEntity)).thenReturn(userResponseDTO);
        when(userRepository.findUserEntityByEmail(EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(userEntityFromRequestDTO)).thenReturn(userEntity);

        UserResponseDTO actual = userService.save(userRequestDTO);
        assertThat(userResponseDTO)
                .usingRecursiveComparison()
                .isEqualTo(actual);

    }

    @Test
    void test_saveUser_shouldReturnUserEntity_whenProvideEmailExisted() {
        // mock
        when(userRepository.findUserEntityByEmail(EMAIL)).thenReturn(Optional.ofNullable(userEntity));
        assertThrows(EmailAlreadyExistsException.class, () -> userService.save(userRequestDTO));
    }

    @Test
    void test_saveUser_shouldReturnUserEntity_whenProvideIllegalSalary() {
        // set Illegal Salary
        userRequestDTO.setSalary(10000);
        // mock
        when(userMapper.mapDtoToEntity(userRequestDTO)).thenReturn(userEntityFromRequestDTO);
        when(userRepository.findUserEntityByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(IllegalSalaryException.class, () -> userService.save(userRequestDTO));
    }
}