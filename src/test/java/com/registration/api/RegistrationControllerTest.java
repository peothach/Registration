package com.registration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.BaseResponseDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.enums.UserType;
import com.registration.exception.EmailAlreadyExistsException;
import com.registration.exception.IllegalEmailException;
import com.registration.exception.IllegalSalaryException;
import com.registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RegistrationController.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles(profiles = "test")
class RegistrationControllerTest {

  private static final String END_POINT = "/api/v1/registration";
  private static final Long ID = 1L;
  private static final String EMAIL = "John@gmail.com";
  private static final double SALARY_DOUBLE = 15000;
  private static final BigDecimal SALARY_BIG_DECIMAL = BigDecimal.valueOf(SALARY_DOUBLE);
  private static final UserType USER_TYPE = UserType.SILVER;
  private static final String PASSWORD = "password";
  private static final String EMAIL_IS_EXIST = "Email already exists";
  private static final String ILLEGAL_SALARY = "Minimum salary for new member is 15,000";
  private static final String ILLEGAL_EMAIL = "Email is not valid";
  private static final String SUCCESS_MESSAGE = "Successful";
  private static final LocalDate CREATE_AT = LocalDate.now();
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private UserService userService;
  private UserResponseDto userResponseDTO;
  private UserRequestDto userRequestDTO;

  @BeforeEach
  void init() {
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
  void test_registerUser_shouldReturnStatusCreatedAndUserResponseDTO_whenProvideValidUserRegistration() throws Exception {
    // mock
    when(userService.save(any(UserRequestDto.class))).thenReturn(userResponseDTO);
    MockHttpServletResponse response = mockMvc.perform(post(END_POINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequestDTO)))
            .andReturn().getResponse();
    // setup responseExpected
    BaseResponseDto<UserResponseDto> responseExpected = new BaseResponseDto<>();
    responseExpected.setCode(HttpStatus.CREATED.value());
    responseExpected.setMessage(SUCCESS_MESSAGE);
    responseExpected.setData(userResponseDTO);
    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    System.out.println(objectMapper.writeValueAsString(responseExpected));
    assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(responseExpected));
  }

  @Test
  void test_registerUser_shouldReturnStatusCreatedAndUserResponseDTO_whenProvideEmailUserExists() throws Exception {
    // mock
    when(userService.save(any(UserRequestDto.class))).thenThrow(new EmailAlreadyExistsException(EMAIL_IS_EXIST));
    MockHttpServletResponse response = mockMvc.perform(post(END_POINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequestDTO)))
            .andReturn().getResponse();
    // setup responseExpected
    BaseResponseDto<UserResponseDto> responseExpected = new BaseResponseDto<>();
    responseExpected.setCode(HttpStatus.BAD_REQUEST.value());
    responseExpected.setMessage(EMAIL_IS_EXIST);
    responseExpected.setData(null);
    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(responseExpected));
  }

  @Test
  void test_registerUser_shouldReturnStatusCreatedAndUserResponseDTO_whenProvideIllegalSalary() throws Exception {
    // mock
    when(userService.save(any(UserRequestDto.class))).thenThrow(new IllegalSalaryException(ILLEGAL_SALARY));
    MockHttpServletResponse response = mockMvc.perform(post(END_POINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequestDTO)))
            .andReturn().getResponse();
    // setup responseExpected
    BaseResponseDto<UserResponseDto> responseExpected = new BaseResponseDto<>();
    responseExpected.setCode(HttpStatus.BAD_REQUEST.value());
    responseExpected.setMessage(ILLEGAL_SALARY);
    responseExpected.setData(null);
    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(responseExpected));
  }

  @Test
  void test_registerUser_shouldReturnStatusCreatedAndUserResponseDTO_whenProvideIllegalEmail() throws Exception {
    // mock
    when(userService.save(any(UserRequestDto.class))).thenThrow(new IllegalEmailException(ILLEGAL_EMAIL));
    MockHttpServletResponse response = mockMvc.perform(post(END_POINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequestDTO)))
            .andReturn().getResponse();
    // setup responseExpected
    BaseResponseDto<UserResponseDto> responseExpected = new BaseResponseDto<>();
    responseExpected.setCode(HttpStatus.BAD_REQUEST.value());
    responseExpected.setMessage(ILLEGAL_EMAIL);
    responseExpected.setData(null);
    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(responseExpected));
  }

}