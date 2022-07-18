package com.registration.mapper;

import com.registration.dto.request.UserRequestDTO;
import com.registration.dto.response.UserResponseDTO;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
class UserMapperTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "John@gmail.com";
    private static final double SALARY_DOUBLE = 15000;
    private static final BigDecimal SALARY_BIG_DECIMAL = BigDecimal.valueOf(SALARY_DOUBLE);
    private static final String PASSWORD = "password";
    private static final LocalDate CREATE_AT = LocalDate.of(2022, Month.JULY, 18);
    private static final UserType USER_TYPE = UserType.SILVER;
    @InjectMocks
    private UserMapper userMapper;
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
    void test_MapDtoToEntity_ShouldReturnUserEntity_IfPassValidUserRequestDTO() {
        UserEntity actual = userMapper.mapDtoToEntity(userRequestDTO);
        assertThat(actual).usingRecursiveComparison().isEqualTo(userEntityFromRequestDTO);
    }

    @Test
    void test_MapEntityToDTO_ShouldReturnUserResponseDTO_IfPassValidUserEntity() {
        UserResponseDTO actual = userMapper.mapEntityToDTO(userEntity);
        assertThat(actual).usingRecursiveComparison().isEqualTo(userResponseDTO);
    }
}